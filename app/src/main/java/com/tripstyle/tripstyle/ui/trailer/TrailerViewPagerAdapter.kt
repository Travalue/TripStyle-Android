package com.tripstyle.tripstyle.ui.trailer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.model.TrailerItem

class TrailerViewPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<TrailerViewPagerAdapter.PagerViewHolder>() {

    private var listener : onActionListener? = null
    private var trailerCardList : ArrayList<TrailerItem>? = arrayListOf()

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.trailer_item_view, parent, false)) {
        val trailerCard = itemView.findViewById<ImageView>(R.id.iv_trailer)
        val trailerTitle = itemView.findViewById<TextView>(R.id.tv_main_title)
        val trailerSubtitle = itemView.findViewById<TextView>(R.id.tv_subtitle)
        val trailerBadge = itemView.findViewById<ImageView>(R.id.iv_category)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

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
                it.findNavController().navigate(listener!!.onMoveDetailPage())
            }
        }
        //holder.trailerCard.clipToOutline = true
    }

    fun setListener(listener: onActionListener){
        this.listener = listener
    }

    fun setData(trailerCardList:ArrayList<TrailerItem>){
        this.trailerCardList = trailerCardList
    }
}