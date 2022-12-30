package com.android.example.travalue.info

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentCategroyBinding

class CategoryFragment : BaseFragment<FragmentCategroyBinding>(R.layout.fragment_categroy) {
    val boldFont: Typeface? = context?.let { ResourcesCompat.getFont(it, R.font.suit_bold) }
    val boldExtraLight: Typeface? = context?.let { ResourcesCompat.getFont(it, R.font.suit_extra_light) }

    override fun initStartView() {
        super.initStartView()
    }

    override fun initDataBinding() {
        super.initDataBinding()


    }


    @SuppressLint("ResourceAsColor")
    override fun initAfterBinding() {
        super.initAfterBinding()

        // 카테고리 클릭시 이벤트
        binding.tvGoTrailer.setOnClickListener {
            navController.navigate(R.id.action_categoryFragment_to_trailerFragment)
        }

        binding.tvGoTraveller.setOnClickListener {
            navController.navigate(R.id.action_categoryFragment_to_travellerFragment)
        }
    }
}