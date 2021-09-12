package com.mamram.checktezostokenprice

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.mamram.checktezostokenprice.data.entity.Contract
import com.mamram.checktezostokenprice.data.entity.toModel
import com.mamram.checktezostokenprice.data.entity.toModels
import com.mamram.checktezostokenprice.data.model.ContractModel
import com.mamram.checktezostokenprice.database.AppDatabase
import com.mamram.checktezostokenprice.database.ContractDao
import com.mamram.checktezostokenprice.network.TzKtService
import com.mamram.checktezostokenprice.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val contractDao: ContractDao = AppDatabase.getInstance(application).contractDao()

    private val _contracts = MutableLiveData(mutableListOf<ContractModel>())
    val contracts: LiveData<MutableList<ContractModel>> get() = _contracts

    private val client = OkHttpClient.Builder().build()

    private val retrofit =
        Retrofit.Builder().client(client).baseUrl(TZKT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(TzKtService::class.java)

    private val _snackBarErrorMessage = MutableLiveData<Event<String>>()
    val snackBarErrorMessage: LiveData<Event<String>> get() = _snackBarErrorMessage

    private val _loadingVisibility = MutableLiveData(View.GONE)
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _progressValue = MutableLiveData(0)
    val progressValue: LiveData<Int> get() = _progressValue

    val progressVisibility: LiveData<Int> = contracts.map {
        if (it.isEmpty()) {
            return@map View.GONE
        } else {
            return@map View.VISIBLE
        }
    }

    private var lastTimeFetchApi = 0L

    fun fetchLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            val contracts = contractDao.getAll().toModels()
            Log.d(TAG, "fetchLocal: $contracts")
            _contracts.postValue(contracts.toMutableList())
            fetchAPI()
        }
    }

    fun fetchContractInfo(address: String, name: String): Boolean {
        if (_loadingVisibility.value == View.GONE) {
            _loadingVisibility.value = View.VISIBLE
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val contractInfo = service.getContractInfo(address)
                    if (contractInfo.type == null || contractInfo.type != TYPE_CONTRACT) {
                        showAddressError(address)
                    } else {
                        fetchContractExchangeRate(address, name, -1)
                    }
                } catch (e: Exception) {
                    showAddressError(address)
                }
                _loadingVisibility.postValue(View.GONE)
            }
            return true
        } else {
            return false
        }
    }

    private fun showAddressError(address: String) {
        _snackBarErrorMessage.postValue(
            Event(
                this@MainViewModel.getApplication<Application>()
                    .getString(R.string.error_address) + " $address"
            )
        )
    }

    private fun fetchContractExchangeRate(address: String, alias: String, localId: Int = -1) {
        Log.d(TAG, "fetchContractExchangeRate: $localId")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contractStorage = service.getContractStorage(address)
                if (contractStorage.storage?.tezPool == null || contractStorage.storage.tokenPool == null) {
                    showAddressError(address)
                } else {
                    var isReverse = false
                    var rate =
                        contractStorage.storage.tezPool.toDouble() / contractStorage.storage.tokenPool.toDouble()
                    if (rate < 0.00001) {
                        rate =
                            contractStorage.storage.tokenPool.toDouble() / contractStorage.storage.tezPool.toDouble()
                        isReverse = true
                    }
                    if (localId == -1) {
                        Contract(
                            contractAddress = address,
                            contractName = alias,
                            lastRate = rate,
                            isReverse = isReverse
                        ).let {
                            it.id = contractDao.insertContract(it).toInt()
                            insertToContractList(it)
                        }
                    } else {
                        Contract(
                            contractAddress = address,
                            contractName = alias,
                            lastRate = rate,
                            id = localId,
                            isReverse = isReverse
                        ).let {
                            contractDao.updateContract(it)
                            updateContractList(it)
                        }
                    }
                }
                Log.d(TAG, "fetchContractExchangeRate finish: $localId")
            } catch (e: Exception) {
                showAddressError(address)
            }
        }
    }

    private fun insertToContractList(contract: Contract) {
        _contracts.value?.let { list ->
            list.add(contract.toModel())
            _contracts.postValue(list)
        }
    }

    private fun updateContractList(contract: Contract) {
        _contracts.value?.let { list ->
            list.find { it.id == contract.id }?.lastRate = contract.lastRate
            _contracts.postValue(list)
        }
    }

    private suspend fun fetchAPI() {
        while (true) {
            val temp = System.currentTimeMillis() - lastTimeFetchApi
            if (temp > INTERVAL) {
                lastTimeFetchApi = System.currentTimeMillis()
                _contracts.value?.forEach {
                    fetchContractExchangeRate(it.contractAddress, it.contractName, it.id)
                }
                _progressValue.postValue(0)
            } else {
                _progressValue.postValue((temp * 100 / INTERVAL).toInt())
            }
            delay(1000L)
        }
    }

    fun deleteContact(position: Int) {
        try {
            _contracts.value?.get(position)?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    contractDao.delete(
                        Contract(
                            it.id,
                            it.contractName,
                            it.contractAddress,
                            it.lastRate
                        )
                    )
                    withContext(Dispatchers.Main) {
                        _contracts.value?.removeAt(position)
                        _contracts.value = _contracts.value
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "deleteContact: $e")
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            contractDao.nukeTable()
            _contracts.postValue(mutableListOf())
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val TZKT_BASE_URL = "https://api.tzkt.io/v1/"
        private const val TYPE_CONTRACT = "contract"
        private const val INTERVAL = 15 * 1000L
    }

}