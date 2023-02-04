package com.android.example.travalue.ui.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R

class CategoryAdapter(var categoryList: ArrayList<Int>) :
    RecyclerView.Adapter<CategoryAdapter.PagerViewHolder>() {


    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.categroy_item_view, parent, false)) {
        val CategoryCard = itemView.findViewById<ImageView>(R.id.iv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.CategoryCard.setImageResource(categoryList[position])
        holder.CategoryCard.clipToOutline = true
    }
}