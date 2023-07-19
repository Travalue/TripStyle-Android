package com.tripstyle.tripstyle.presentation.ui.traveller

import android.util.Log
import android.view.View
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.CategoryReadResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionBinding
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerCategoryOptionFragment : BaseFragment<FragmentTravellerCategoryOptionBinding>(R.layout.fragment_traveller_category_option) {

    override fun initStartView() {
        super.initStartView()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvCategoryAdd.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionFragment_to_categoryOptionSubjectFragment)
        }

        binding.radioGroupOpenStatus.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.button_public -> changeOpenStatusTextView(true)
                R.id.button_private -> changeOpenStatusTextView(false)
            }
        }
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        requestCategoryList()
    }

    private fun changeOpenStatusTextView(isPublic: Boolean){
        if(isPublic) {
            binding.tvOpenStatusPrivate.visibility = View.INVISIBLE
            binding.tvOpenStatusPublic.visibility = View.VISIBLE
        }
        else{
            binding.tvOpenStatusPublic.visibility = View.INVISIBLE
            binding.tvOpenStatusPrivate.visibility = View.VISIBLE
        }
    }

    private fun requestCategoryList() {
        val service = AppClient.retrofit?.create(TravelService::class.java)

        // TODO: userId 부분 변경 필요
        service?.getCategoryList(1)?.enqueue(object : Callback<CategoryReadResponse> {
            override fun onResponse(
                call: Call<CategoryReadResponse>,
                response: Response<CategoryReadResponse>
            ) {
                if (response.isSuccessful) {
                    val categoryList = response.body()?.data
                    Log.e("CategoryReadResponse", "categoryList: ${categoryList.toString()}")
                } else {
                    Log.e("CategoryReadResponse", "Server error, CODE: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryReadResponse>, t: Throwable) {
                Log.e("CategoryReadResponse", "requestCategoryList API CALL FAILED")
            }
        })
    }

}