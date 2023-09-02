package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.HotTravellerItem

class TravellerHotAdapter(val context: Context?):
    RecyclerView.Adapter<TravellerHotAdapter.RecyclerViewViewHolder>() {

    private var list : ArrayList<HotTravellerItem>? = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hot_traveller_item_view, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        // hot traveller 아이템 개수 (3개 고정)
        return 3
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val thumbnailImage: ImageView = itemView.findViewById(R.id.iv_hot_traveller)
        private val profileImage: ImageView = itemView.findViewById(R.id.iv_hot_traveller_profile)
        private val categoryImage: ImageView = itemView.findViewById(R.id.iv_hot_traveller_category)
        private val nicknameText: TextView = itemView.findViewById(R.id.tv_hot_traveller_nickname)
        private val descriptionText: TextView = itemView.findViewById(R.id.tv_hot_traveller_description)
        private val titleText: TextView = itemView.findViewById(R.id.tv_hot_traveller_title)
        private val subtitleText: TextView = itemView.findViewById(R.id.tv_hot_traveller_subtitle)

        fun setContents(pos: Int) {

            // 리스트에 항상 3개 이하로 들어있으면 상관없는데, 지금으로서는 어떻게 될지 모르겠어서 최대 앞 3개 아이템만 표시하도록 제한함
            // 나중에 hotTraveller List에 아이템이 10개 이런식으로 관리하면 그중에 3개만 랜덤으로 표시하는 식으로 변경하던지 해도됨

            if (!list.isNullOrEmpty()) {

                if(pos >= list!!.size)
                    return

            Glide.with(itemView)
                .load(list!![pos].thumbnail)
                .into(thumbnailImage)
            Glide.with(itemView).load(list!![pos].profileImage).into(profileImage)
            when (list!![pos].subject) {
                "식도락" -> Glide.with(itemView).load(R.drawable.ic_category_food).into(categoryImage)
                "액티비티" -> Glide.with(itemView).load(R.drawable.ic_category_activity).into(categoryImage)
                "휴양" -> Glide.with(itemView).load(R.drawable.ic_category_refresh).into(categoryImage)
                "패키지" -> Glide.with(itemView).load(R.drawable.ic_category_package).into(categoryImage)
            }
            nicknameText.text = list!![pos].nickname
            descriptionText.text = list!![pos].description
            titleText.text = list!![pos].title
            subtitleText.text = list!![pos].subTitle

            }
        }
    }

    fun setData(list:ArrayList<HotTravellerItem>){
        this.list = list
    }
}