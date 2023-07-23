package com.tripstyle.tripstyle.dialog

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseDialogFragment
import com.tripstyle.tripstyle.databinding.DialogTravellerWriteBinding

class TravellerWriteDialog() : BaseDialogFragment<DialogTravellerWriteBinding>(R.layout.dialog_traveller_write) {

    private lateinit var listener: onDialogListener

    override fun initStartView() {
        super.initStartView()

        clickDialogEvent()
    }

    fun setActionListener(listener: onDialogListener) {
        this.listener = listener
    }

    private fun clickDialogEvent(){
        //취소
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            listener.onConfirmAction()
            dismiss()
        }
    }
}