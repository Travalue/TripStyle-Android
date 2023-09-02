package com.tripstyle.tripstyle.dialog

import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseDialogFragment
import com.tripstyle.tripstyle.databinding.DialogCategoryEditBinding

class CategoryDialog(val title:String,val content:String) : BaseDialogFragment<DialogCategoryEditBinding>(R.layout.dialog_category_edit) {

    private lateinit var listener: onDialogListener

    override fun initStartView() {
        super.initStartView()

        initView()
        clickDialogEvent()
    }

    // title, content 설정
    private fun initView(){
        binding.tvTitle.text = title
        binding.tvContent.text = content
    }

    // 리스너 설정
    fun setActionListener(listener: onDialogListener) {
        this.listener = listener
    }

    // 다이얼로그 이벤트
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