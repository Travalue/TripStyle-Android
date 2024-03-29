package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.CategoryItem

class TravellerCategoryAdapter(private val viewModel: TravellerWriteViewModel, val context: Context?):
    RecyclerView.Adapter<TravellerCategoryAdapter.RecyclerViewViewHolder>() {

    private var list : ArrayList<CategoryItem>? = arrayListOf()
    private var checkedPosition = -1

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
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun setContents(pos: Int) {
            // 세팅
            if(!list.isNullOrEmpty()){
                if(pos<list!!.size){
                    categoryTitle.text = list!![pos].title
                    categoryWriteCount.text = list!![pos].travellerCount.toString()
                }
            }

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = pos == checkedPosition

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    checkedPosition = pos
                    viewModel.categoryCheckBox.value = pos
                    viewModel.currentCheckedCategoryId.value = list!![pos].id // 현재 체크된 카테고리의 카테고리 id 저장
                } else {
                    if (pos == checkedPosition) {
                        viewModel.categoryCheckBox.value = -1
                        checkedPosition = -1
                        viewModel.currentCheckedCategoryId.value = -1 // 현재 체크된 카테고리의 카테고리 id 초기화
                    }
                }
            }

        }

    }

    fun setData(list:ArrayList<CategoryItem>){
        this.list = list
    }

}