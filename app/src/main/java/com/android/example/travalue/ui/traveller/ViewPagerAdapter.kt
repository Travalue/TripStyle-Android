package com.android.example.travalue.ui.traveller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R
import com.bumptech.glide.Glide

class ViewPagerAdapter(var list: ArrayList<String>,val context: Context?) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        // glide로 처리
//        holder.postImage.setImageResource(list[position])
        holder.setContents(position)
        Log.e("","onBindViewHolder arrived, current pos: ${position}")
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.traveller_write_viewpager_item, parent, false)) {
        val postImage = itemView.findViewById<ImageView>(R.id.iv_post)

        fun setContents(pos: Int){
            with(list[pos]){
                //세팅
                    Glide.with(itemView).load(this)
                        .centerCrop()
                        .into(postImage)
                    Log.e("","setContents arrived, current pos: ${pos}")
            }

        }
    }
}