package com.android.example.travalue.dialog

import android.widget.Toast
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseDialogFragment
import com.android.example.travalue.databinding.DialogCategoryEditBinding

class CategoryDeleteDialog : BaseDialogFragment<DialogCategoryEditBinding>(R.layout.dialog_category_edit) {
    override fun initStartView() {
        super.initStartView()
        clickDialogEvent()
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    // 다이얼로그 이벤트
    private fun clickDialogEvent(){
        //취소
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            Toast.makeText(requireContext(),"삭제 되었습니다",Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

}