package com.tripstyle.tripstyle.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R

class TrailerSearchRecyclerViewAdapter(val context: Context?):
    RecyclerView.Adapter<TrailerSearchRecyclerViewAdapter.RecyclerViewViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trailer_search_item_view,
            parent, false)
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemViewBinding.inflate(inflater,parent,false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        // '#제주도여행' 검색결과(사진)에 표시될 item 개수
        return 20
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val trailerSearchResult: ImageView = itemView.findViewById(R.id.trailer_search_image)

        fun setContents(pos: Int){
            with(pos){
                //세팅
                trailerSearchResult.setImageResource(R.drawable.trailer_search_sample_image)
            }

            /*

            trailerSearchResult.setOnClickListener {
                이런식으로 clickListener가 여기 있어야 하지만 이게 이제 MVVM 쓰면 xml로 들어가는거
            }

            */
        }
    }
}