package com.tripstyle.tripstyle.ui.info.info

import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentRegisterOkBinding
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