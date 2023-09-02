package com.tripstyle.tripstyle.presentation.ui.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentMytravelListBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.MyTripDeleteResponse
import com.tripstyle.tripstyle.data.model.dto.TripModel
import com.tripstyle.tripstyle.data.model.dto.UserViewModel
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyTravelListFragment : BaseFragment<FragmentMytravelListBinding>(R.layout.fragment_mytravel_list) {

    private lateinit var userViewModel : UserViewModel
    private lateinit var myTravelListAdapter : MyTravelListAdapter

    override fun initStartView() {
        super.initStartView()

        userViewModel = (context as MainActivity).getUserViewModel()

    }

    override fun initDataBinding() {
        super.initDataBinding()

        myTravelListAdapter = MyTravelListAdapter(userViewModel.myTripList.value!!, true) // 어댑터 생성
        binding.placeList.adapter = myTravelListAdapter

        val gridLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 3)
        binding.placeList.layoutManager = gridLayoutManager

        // LiveData Observer를 등록하여 변경 사항을 감지하고 Adapter 업데이트
        userViewModel.myTripList.observe(viewLifecycleOwner) { newList ->
            myTravelListAdapter.travelList = newList
            myTravelListAdapter.notifyDataSetChanged()
        }


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

                binding.btnAddTravel.isEnabled=true
                binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                if(place == ""){
                    binding.btnAddTravel.isEnabled=false
                    binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.gray_959595))
                }
            }
        })

        // 이모지 추가 버튼
        binding.ivIcon.setOnClickListener {

        }

        // 여행지 추가 버튼 클릭시
        binding.btnAddTravel.setOnClickListener {
            val place = binding.etTravel.text.toString()
            val tripModel = TripModel("💡",place)
            val resultData: Call<Void> = AppClient.userService.addMyTrip(tripModel)
            resultData.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        Log.d("[addMyTrip]", response.message())
                        userViewModel.getTripList()

                        binding.etTravel.setText("")
                    } else {
                        Log.d("[addMyTrip]", "실패코드_${response.code()}")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("[addMyTrip]","통신 실패")
                }
            })
        }
    }

}