package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.data.model.dto.MyTripDeleteResponse
import com.tripstyle.tripstyle.data.model.dto.MyTripModel
import com.tripstyle.tripstyle.data.model.dto.UpdateUserProfileResponseModel
import com.tripstyle.tripstyle.databinding.PlaceItemViewBinding
import com.tripstyle.tripstyle.di.AppClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTravelListAdapter(var travelList: ArrayList<MyTripModel>, val delete: Boolean) :
    RecyclerView.Adapter<MyTravelListAdapter.ViewHolder>() {


    inner class ViewHolder(itemViewBinding: PlaceItemViewBinding)
        : RecyclerView.ViewHolder(itemViewBinding.root){
        var placeName = itemViewBinding.tvPlace
        var deleteBtn = itemViewBinding.btnPlaceDelete
        var icon = itemViewBinding.tvIcon
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            PlaceItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = travelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeName.text = travelList[position].travelTitle
        holder.icon.text = travelList[position].emoji

        if(delete){
            holder.deleteBtn.visibility=View.VISIBLE
        }

        // 추가된 나의 여행지 삭제버튼 클릭시
        holder.deleteBtn.setOnClickListener {
            Log.d("[delete mytrip id]",travelList[position].id.toString())
//            val resultData: Call<MyTripDeleteResponse> = AppClient.userService.deleteMyTrip()
//            resultData.enqueue(object : Callback<MyTripDeleteResponse> {
//                override fun onResponse(
//                    call: Call<MyTripDeleteResponse>,
//                    response: Response<MyTripDeleteResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        Log.d("[updateProfile]", response.message())
//
//                    } else {
//                        Log.d("[updateProfile]", "실패코드_${response.code()}")
//                    }
//                }
//                override fun onFailure(call: Call<MyTripDeleteResponse>, t: Throwable) {
//                    t.printStackTrace()
//                    Log.d("[updateProfile]","통신 실패")
//                }
//            })
        }
    }
}