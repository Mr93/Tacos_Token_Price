package com.mamram.checktezostokenprice

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.mamram.checktezostokenprice.data.model.ContractModel

class ContractController : EpoxyController() {

    private val contracts = mutableListOf<ContractModel>()

    override fun buildModels() {
        contracts.forEachIndexed { index, it ->
            Log.d(TAG, "buildModels: ${index % 2 == 0}")
            ContractItemBindingModel_()
                .id(it.contractAddress + index + it.id)
                .itemIndex(index)
                .content(getContent(it))
                .addTo(this)
        }
    }

    private fun getContent(data: ContractModel): String {
        return if (data.isReverse) {
            "1 \$XTZ = ${data.lastRate} ${data.contractName}"
        } else {
            "1 ${data.contractName} = ${data.lastRate} \$XTZ"
        }
    }

    fun setData(data: MutableList<ContractModel>) {
        contracts.clear()
        contracts.addAll(data)
        requestModelBuild()
    }

    companion object {
        private const val TAG = "ContractController"
    }
}