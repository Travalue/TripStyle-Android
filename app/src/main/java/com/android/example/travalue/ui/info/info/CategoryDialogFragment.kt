package com.android.example.travalue.ui.info.info

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseDialogFragment
import com.android.example.travalue.databinding.FragmentCategroyBinding
import kotlin.math.log

class CategoryDialogFragment  : BaseDialogFragment<FragmentCategroyBinding>(R.layout.fragment_categroy) {
    private val boldFont: Typeface? = context?.let { ResourcesCompat.getFont(it, R.font.suit_bold) }
    private val boldExtraLight: Typeface? = context?.let { ResourcesCompat.getFont(it, R.font.suit_extra_light) }


    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()


    }

    @SuppressLint("ResourceAsColor")
    override fun initAfterBinding() {
        super.initAfterBinding()

        // 닫기 버튼
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        // 카테고리 클릭시 이벤트
        binding.tvGoTrailer.setOnClickListener {
            navController.navigate(R.id.action_categoryDialogFragment_to_trailerFragment)
        }

        binding.tvGoTraveller.setOnClickListener {
            navController.navigate(R.id.action_categoryDialogFragment_to_travellerFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        //전체화면으로 만드는 코드
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }



}