package com.tripstyle.tripstyle.presentation.ui.mypage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import com.bumptech.glide.request.transition.Transition
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentMyPageMainBinding
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.data.model.dto.LoginResponseModel
import com.tripstyle.tripstyle.data.model.dto.TravelDetailResponse
import com.tripstyle.tripstyle.data.model.dto.UserInfoModel
import com.tripstyle.tripstyle.data.model.dto.UserPageResponse
import com.tripstyle.tripstyle.data.source.remote.UserService
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MyPageFragment  : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {

    private var addPlace :ArrayList<String> =arrayListOf("미국")

    override fun initStartView() {
        super.initStartView()

        val resultData: Call<UserPageResponse> = AppClient.userService.getUserInfo(1,1,1)
        resultData.enqueue(object : Callback<UserPageResponse> {
            override fun onResponse(
                call: Call<UserPageResponse>,
                response: Response<UserPageResponse>
            ) {
                if (response.isSuccessful) {
                    val result: UserPageResponse = response.body()!!
                    val user = result.data

                    context?.let {
                        Glide.with(this@MyPageFragment)
                            .asBitmap()
                            .load(user.profileImage)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    binding.ivProfile.setImageBitmap(resource) // 다운로드한 이미지를 ImageView에 설정합니다.
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    // 이미지 로딩이 취소되었을 때 수행할 작업을 여기에 작성합니다.
                                }
                            })
                    }

//                    binding.tvNickname.text = user.nickname
//                    if (user.description != null) {
//                        binding.tvIntro.text = user.description
//                    }

                } else {
                    Log.d("[getUserInfo]", "실패코드_${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserPageResponse>, t: Throwable) {
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