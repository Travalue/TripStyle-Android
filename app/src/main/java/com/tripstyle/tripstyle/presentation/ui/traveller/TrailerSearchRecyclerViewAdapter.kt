package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.TravellerSearchResult

class TrailerSearchRecyclerViewAdapter(val context: Context?):
    RecyclerView.Adapter<TrailerSearchRecyclerViewAdapter.RecyclerViewViewHolder>() {

    private var list : ArrayList<TravellerSearchResult>? = arrayListOf()
    private val screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trailer_search_item_view,
            parent, false)

        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        // 검색결과(썸네일)에 표시될 item 개수
        return list?.size ?: 0
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val trailerSearchResult: ImageView = itemView.findViewById(R.id.trailer_search_image)
        private val imageViewSize = screenWidth / 3

        fun setContents(pos: Int) {
            // 이미지뷰 정사각형으로
            trailerSearchResult.layoutParams.width = imageViewSize
            trailerSearchResult.layoutParams.height = imageViewSize

            // 세팅
            Glide.with(itemView)
                .load(list!![pos].thumbnail)
                .into(trailerSearchResult)


        }
    }

    fun setData(list:ArrayList<TravellerSearchResult>){
        this.list = list
    }
}