package com.mamram.checktezostokenprice

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.mamram.checktezostokenprice.databinding.ActivityMainBinding
import com.mamram.checktezostokenprice.utils.showSnackBar


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var controller: ContractController

    private val onMenuItemClickListener =
        Toolbar.OnMenuItemClickListener { item ->

            /**
             * This method will be invoked when a menu item is clicked if the item itself did
             * not already handle the event.
             *
             * @param item [MenuItem] that was clicked
             * @return `true` if the event was handled, `false` otherwise.
             */
            if (item?.itemId == R.id.delete) {
                confirmDeleteAll()
            } else if (item?.itemId == R.id.donate) {
                donateMe()
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initList()
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        binding.toolBar.setOnMenuItemClickListener(onMenuItemClickListener)
        mainViewModel.fetchLocal()
        observeEvent()
    }

    private fun initList() {
        controller = ContractController()
        binding.contractList.apply {
            this.setController(controller)
        }
        binding.btnAdd.setOnClickListener {
            if (binding.edtContractAddress.text.toString().isNotEmpty()) {
                if (mainViewModel.fetchContractInfo(binding.edtContractAddress.text.toString())) {
                    binding.edtContractAddress.setText("")
                }
            }
        }

        EpoxyTouchHelper.initSwiping(binding.contractList)
            .leftAndRight() // Which directions to allow
            .withTargets(ContractItemBindingModel_::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<EpoxyModel<*>?>() {
                override fun onSwipeCompleted(
                    model: EpoxyModel<*>?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    Log.d(TAG, "onSwipeCompleted: ${direction}")
                    mainViewModel.deleteContact(position)
                }
            })
    }

    private fun observeEvent() {
        mainViewModel.contracts.observe(this, {
            Log.d(TAG, "observeEvent: $it")
            controller.setData(it)
        })
        mainViewModel.snackBarErrorMessage.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                showSnackBar(it, Snackbar.LENGTH_LONG)
            }
        })
        mainViewModel.loadingVisibility.observe(this, {
            binding.btnAdd.isEnabled = it != View.VISIBLE
        })
    }

    private fun confirmDeleteAll() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle(R.string.delete_all)
        builder.setMessage(R.string.delete_all_message)
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            mainViewModel.deleteAll()
        }

        builder.setNegativeButton(R.string.no) { dialog, which ->
        }
        builder.show()
    }

    private fun donateMe() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle(R.string.donate_title)
        builder.setMessage(R.string.donate_message)
        builder.setPositiveButton(R.string.copy_address) { dialog, which ->
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData =
                ClipData.newPlainText("My tacos address", "tz1MM9YqZuifZMYxuvQDKMTr3iRafDPpo8Gu")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, R.string.address_copied, Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}