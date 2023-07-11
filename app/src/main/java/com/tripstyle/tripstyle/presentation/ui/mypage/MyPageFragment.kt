package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentMyPageMainBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.LoginResponseModel
import com.tripstyle.tripstyle.data.model.dto.TravelDetailResponse
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.source.remote.UserService
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageFragment  : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {

    private var addPlace :ArrayList<String> =arrayListOf("미국")

    override fun initStartView() {
        super.initStartView()

        val resultData: Call<UserInfoModel> = AppClient.userService.getUserInfo(1,1,1)
        resultData.enqueue(object : Callback<UserInfoModel> {
            override fun onResponse(
                call: Call<UserInfoModel>,
                response: Response<UserInfoModel>
            ) {
                if (response.isSuccessful) {
                    val result: UserInfoModel = response.body()!!
                    Log.d("[getUserInfo]", "코드-${response.code()}")
                    Log.d("[getUserInfo]", "메시지-${response.message()}")
                    Log.d("[getUserInfo]", "서버응답 : $result")
                } else {
                    Log.d("[getUserInfo]", "실패코드_${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                t.printStackTrace()
                Log.d("[getUserInfo]","통신 실패")
            }
        })

        AppClient.userService.getUserInfo(1,1,1)
            .enqueue(object : Callback<UserInfoModel>{
            override fun onResponse(
                call: Call<UserInfoModel>,
                response: Response<UserInfoModel>
            ) {
                if (response.isSuccessful) {
                    val result: UserInfoModel = response.body()!!
                    Log.d("[getUserInfo]", "코드-${response.code()}")
                    Log.d("[getUserInfo]", "메시지-${response.message()}")
                    Log.d("[getUserInfo]", "서버응답 : $result")
                } else {
                    Log.d("[getUserInfo]", "실패코드_${response.code()}")
                }
            }

                override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("[getUserInfo]","통신 실패")
                }

            })
    }

    override fun initDataBinding() {
        super.initDataBinding()

        if(addPlace.size > 0){
            binding.tvNoDestination.text=""
        }else{
            binding.tvNoDestination.text="나의 여행지를 추가해보세요."
        }


        // 나의 여행지 리스트 어댑터
        binding.placeList.adapter = MyTravelListAdapter(addPlace,false) // 어댑터 생성
        val gridLayoutManager1: RecyclerView.LayoutManager = GridLayoutManager(context,4)
        binding.placeList.layoutManager = gridLayoutManager1

        // 공유중인 여행지 어댑터
        binding.categoryCardList.adapter = CategoryAdapter(getCategoryImg()) // 어댑터 생성
        val gridLayoutManager2: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.categoryCardList.layoutManager = gridLayoutManager2

    }


    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnEditProfile.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_editProfileFragment)
        }

        //공유중인 여행지 UI로 이동
        binding.btnDestinationDetail3.setOnClickListener {
            navController.navigate(R.id.action_myPageFragment_to_shareTravelFragment)
        }

        binding.btnToTravelList.setOnClickListener{
            navController.navigate(R.id.action_myPageFragment_to_myTravelListFragment)
        }

        binding.btnToLikeList.setOnClickListener{
            navController.navigate(R.id.action_myPageFragment_to_likeListFragment)
        }
    }

    private fun getCategoryImg(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.ex_img1,
            R.drawable.img_add_category)
    }

}