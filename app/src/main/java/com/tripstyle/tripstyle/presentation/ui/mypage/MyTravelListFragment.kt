package com.tripstyle.tripstyle.presentation.ui.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentMytravelListBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import java.util.*
import kotlin.collections.ArrayList

class MyTravelListFragment : BaseFragment<FragmentMytravelListBinding>(R.layout.fragment_mytravel_list) {

    private lateinit var userViewModel : UserViewModel

    override fun initStartView() {
        super.initStartView()

        userViewModel = (context as MainActivity).getUserViewModel()

    }

    override fun initDataBinding() {
        super.initDataBinding()


        binding.placeList.adapter = MyTravelListAdapter(userViewModel.myTripList.value!!,true) // 어댑터 생성
        val gridLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,3)
        binding.placeList.layoutManager = gridLayoutManager

    }


    override fun initAfterBinding() {
        super.initAfterBinding()

        if (userViewModel.myTripList.value!!.isEmpty())
            binding.tvNoList.visibility = View.VISIBLE

        // editText 입력 감지
        binding.etTravel.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                val place = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val place = binding.etTravel.text.toString()

//                // 저장된 여행지를 입력했을때
//                if(allPlace.contains(place)){
//                    binding.btnAddTravel.isEnabled=true
//                    binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.white))
//                    binding.ivIcon.visibility = View.INVISIBLE
//                    binding.tvAddIcon.visibility=View.INVISIBLE
//                    binding.tvPlaceIcon.text = placeIcon[0]
//                    binding.tvPlaceIcon.visibility=View.VISIBLE
//                }else{
//                    binding.btnAddTravel.isEnabled=false
//                    binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
//                    binding.ivIcon.visibility = View.VISIBLE
//                    binding.tvAddIcon.visibility=View.VISIBLE
//                    binding.tvPlaceIcon.visibility=View.INVISIBLE
//                }

            }
        })

        // 이모지 추가 버튼
        binding.ivIcon.setOnClickListener {

        }

        // 여행지 추가 버튼 클릭시
        binding.btnAddTravel.setOnClickListener {
            val place = binding.etTravel.text.toString()

        }
    }

}