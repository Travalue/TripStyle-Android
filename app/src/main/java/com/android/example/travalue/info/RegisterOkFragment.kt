package com.android.example.travalue.info

import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentRegisterOkBinding

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