package com.tripstyle.tripstyle.presentation.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.data.model.dto.MyTripModel
import com.tripstyle.tripstyle.databinding.PlaceItemViewBinding

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
    }
}