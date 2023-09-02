package com.tripstyle.tripstyle.presentation.ui.trailer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.TrailerItem
import com.tripstyle.tripstyle.util.Constant

class TrailerViewPagerAdapter(private val context: Context,val type:Int, val like: Boolean) :
    RecyclerView.Adapter<TrailerViewPagerAdapter.PagerViewHolder>() {

    private var listener : onActionListener? = null
    private var trailerCardList : ArrayList<TrailerItem>? = arrayListOf()

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.trailer_item_view, parent, false)) {
        val trailerCard = itemView.findViewById<ImageView>(R.id.iv_trailer)
        val trailerTitle = itemView.findViewById<TextView>(R.id.tv_main_title)
        val trailerSubtitle = itemView.findViewById<TextView>(R.id.tv_subtitle)
        val trailerBadge = itemView.findViewById<ImageView>(R.id.iv_category)
        val likeHeart = itemView.findViewById<ImageView>(R.id.iv_heart)

    }
//
//    inner class RecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
//        (LayoutInflater.from(parent.context).inflate(R.layout.share_item_view, parent, false)) {
//        val trailerCard = itemView.findViewById<ImageView>(R.id.iv_trailer)
//        val trailerTitle = itemView.findViewById<TextView>(R.id.tv_main_title)
//        val trailerSubtitle = itemView.findViewById<TextView>(R.id.tv_subtitle)
//        val trailerBadge = itemView.findViewById<ImageView>(R.id.iv_category)
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PagerViewHolder {
        return when (type) {
            Constant.TYPE_PAGER -> {
                val pagerViewHolder = PagerViewHolder(parent)
                val layoutParams = pagerViewHolder.itemView.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                pagerViewHolder.itemView.layoutParams = layoutParams
                pagerViewHolder
            }
            Constant.TYPE_RECYCLER -> {
                val recycleViewHolder = PagerViewHolder(parent)
                val layoutParams = recycleViewHolder.itemView.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                recycleViewHolder.itemView.layoutParams = layoutParams
                recycleViewHolder
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = trailerCardList?.size!!

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = trailerCardList?.get(position)

        holder.trailerTitle.text = item?.title
        holder.trailerSubtitle.text = item?.subTitle
        when(item?.subject){
            "식도락" -> Glide.with(context).load(R.drawable.ic_category_food).into(holder.trailerBadge)
            "액티비티" -> Glide.with(context).load(R.drawable.ic_category_activity).into(holder.trailerBadge)
            "패키지" -> Glide.with(context).load(R.drawable.ic_category_package).into(holder.trailerBadge)
            "휴양" -> Glide.with(context).load(R.drawable.ic_category_refresh).into(holder.trailerBadge)
        }

        Glide.with(context).load(item?.thumbnail).into(holder.trailerCard)

        if(listener != null){
            holder.trailerCard.setOnClickListener {
                it.findNavController().navigate(listener!!.onMoveDetailPage(item?.trailerId!!))
            }
        }
        //holder.trailerCard.clipToOutline = true

        if (like){
            holder.likeHeart.isVisible = true
        }
    }

    fun setListener(listener: onActionListener){
        this.listener = listener
    }

    fun setData(trailerCardList:ArrayList<TrailerItem>){
        this.trailerCardList = trailerCardList
    }
}