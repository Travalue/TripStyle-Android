package com.tripstyle.tripstyle.presentation.ui.trailer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.Content

class TrailerDetailRecyclerViewAdapter(val context: Context, var dataList: List<Content>) :
    RecyclerView.Adapter<TrailerDetailRecyclerViewAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.trailer_detail_item_view, parent, false)) {
        val detailImage = itemView.findViewById<ImageView>(R.id.iv_detail_view)
        val detailText = itemView.findViewById<TextView>(R.id.tv_detail_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].imageURL).into(holder.detailImage)
        holder.detailText.setText(dataList[position].content)
    }
}

data class TrailerDetail(
    val text : String,
    val img : Int
)