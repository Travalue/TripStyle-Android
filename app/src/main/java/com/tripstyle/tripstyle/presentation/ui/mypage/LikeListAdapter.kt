package com.tripstyle.tripstyle.presentation.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.databinding.LikeItemViewBinding

class LikeListAdapter(var likeList: ArrayList<String>) :
    RecyclerView.Adapter<LikeListAdapter.ViewHolder>() {


    inner class ViewHolder(itemViewBinding: LikeItemViewBinding)
        : RecyclerView.ViewHolder(itemViewBinding.root){

            var placeName = itemViewBinding.tvPlaceName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LikeItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = likeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.placeName.text=likeList[position]
    }
}