package com.tripstyle.tripstyle.dialog

import android.widget.Toast
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseDialogFragment
import com.tripstyle.tripstyle.databinding.DialogExitConfirmBinding

class ProfilePopupDialog : BaseDialogFragment<DialogExitConfirmBinding>(R.layout.dialog_exit_confirm) {
    override fun initStartView() {
        super.initStartView()
        clickDialogEvent()

        dialog?.window?.attributes?.dimAmount = 0.8f // 다이얼로그가 나왔을때 뒷배경의 투명도를 조정
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
            Toast.makeText(requireContext(),"수정 되었습니다",Toast.LENGTH_SHORT).show()
//            dismiss()
        }
    }

}