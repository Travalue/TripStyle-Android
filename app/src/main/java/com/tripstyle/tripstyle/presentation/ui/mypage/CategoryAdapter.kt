package com.tripstyle.tripstyle.presentation.ui.mypage

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.databinding.CategroyItemViewBinding

class CategoryAdapter(var categoryList: ArrayList<Int>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    inner class ViewHolder(itemViewBinding: CategroyItemViewBinding)
        :RecyclerView.ViewHolder(itemViewBinding.root){
        var categoryCard = itemViewBinding.ivCategoryImg
        var category = itemViewBinding.ivCategory
        var categoryInfo = itemViewBinding.tvCategoryInfo
        var categoryDetailButton = itemViewBinding.btnCategoryDetail
        var categorySize = itemViewBinding.tvCategorySize
        var textView = itemViewBinding.textView5
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
        holder.categoryCard.setImageResource(categoryList[position])
        if(position == (categoryList.size -1)){
            holder.category.visibility = View.INVISIBLE
            holder.categoryInfo.visibility = View.INVISIBLE
            holder.categorySize.visibility = View.INVISIBLE
            holder.categoryDetailButton.visibility = View.INVISIBLE
            holder.textView.visibility = View.INVISIBLE
        }else{
            holder.categoryCard.setColorFilter(Color.parseColor("#60000000"))
        }
    }
}