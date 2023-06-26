package com.tripstyle.tripstyle.ui.info.info

import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentRegisterOkBinding
import com.tripstyle.tripstyle.MainActivity

class RegisterOkFragment : BaseFragment<FragmentRegisterOkBinding>(R.layout.fragment_register_ok) {

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("none")
    }

    override fun initDataBinding() {
        super.initDataBinding()


    }


    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnLogin2.setOnClickListener {
            navController.navigate(R.id.action_registerOkFragment_to_trailerFragment)
        }
    }
}