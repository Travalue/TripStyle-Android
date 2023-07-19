package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.CategoryItem

class TravellerCategoryRecyclerViewAdapter(val context: Context?):
    RecyclerView.Adapter<TravellerCategoryRecyclerViewAdapter.RecyclerViewViewHolder>() {

    private var list : ArrayList<CategoryItem>? = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.traveller_category_item_view, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        // category item 개수
        return list?.size ?: 0
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val categoryTitle: TextView = itemView.findViewById(R.id.tv_category_title)
        private val categoryWriteCount: TextView = itemView.findViewById(R.id.tv_category_write_count)

        fun setContents(pos: Int) {
            // 세팅
            if(!list.isNullOrEmpty()){
                if(pos<list!!.size){
                    categoryTitle.text = list!![pos].title
                    categoryWriteCount.text = list!![pos].travellerCount.toString()
                }
            }
        }

    }

    fun setData(list:ArrayList<CategoryItem>){
        this.list = list
    }

}