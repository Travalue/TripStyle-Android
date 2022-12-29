package com.android.example.travalue.ui.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R

class TrailerViewPagerAdapter(var trailerCardList: ArrayList<Int>) :
    RecyclerView.Adapter<TrailerViewPagerAdapter.PagerViewHolder>() {


    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.trailer_item_view, parent, false)) {
        val trailerCard = itemView.findViewById<ImageView>(R.id.iv_trailer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = trailerCardList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.trailerCard.setImageResource(trailerCardList[position])
    }
}