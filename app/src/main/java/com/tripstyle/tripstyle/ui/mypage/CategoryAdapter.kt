package com.tripstyle.tripstyle.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.databinding.CategroyItemViewBinding

class CategoryAdapter(var categoryList: ArrayList<Int>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    inner class ViewHolder(itemViewBinding: CategroyItemViewBinding)
        :RecyclerView.ViewHolder(itemViewBinding.root){
        var CategoryCard = itemViewBinding.ivCategory
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            CategroyItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.CategoryCard.setImageResource(categoryList[position])
        holder.CategoryCard.clipToOutline=true
    }
}