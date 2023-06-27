package com.tripstyle.tripstyle.ui.info.info

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseDialogFragment
import com.tripstyle.tripstyle.databinding.FragmentCategroyBinding
import com.tripstyle.tripstyle.MainActivity

class CategoryDialogFragment  : BaseDialogFragment<FragmentCategroyBinding>(R.layout.fragment_categroy) {
    val args: CategoryDialogFragmentArgs by navArgs()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")

        // 현재 페이지별 텍스트 변경
        when (args.argsString) {
            "\\ trailer" -> {
                binding.tvGoTrailer.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
                binding.tvGoTrailer.setTypeface(null, Typeface.BOLD )
            }
            "\\ traveller" -> {
                binding.tvGoTraveller.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
                binding.tvGoTraveller.setTypeface(null, Typeface.BOLD )
            }
            "\\ my page"->{
                binding.tvGoMypage.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
                binding.tvGoMypage.setTypeface(null, Typeface.BOLD )
            }
            else -> println("null")
        }

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

        binding.tvGoMypage.setOnClickListener {
            navController.navigate(R.id.action_categoryDialogFragment_to_myPageFragment)
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