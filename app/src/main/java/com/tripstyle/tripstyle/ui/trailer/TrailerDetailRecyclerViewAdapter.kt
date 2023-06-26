package com.tripstyle.tripstyle.ui.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R

class TrailerDetailRecyclerViewAdapter(var dataList: ArrayList<TrailerDetail>) :
    RecyclerView.Adapter<TrailerDetailRecyclerViewAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.trailer_detail_item_view, parent, false)) {
        val detailImage = itemView.findViewById<ImageView>(R.id.iv_detail_view)
        val detailText = itemView.findViewById<TextView>(R.id.tv_detail_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.detailImage.setImageResource(dataList[position].img)
        holder.detailText.setText(dataList[position].text)
    }
}

data class TrailerDetail(
    val text : String,
    val img : Int
)