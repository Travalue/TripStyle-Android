package com.android.example.travalue.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R

// TOOD : 추 후에 item type 변경
class TravellerLocationRecyclerViewAdapter(val context: Context?,val list : List<String>):
    RecyclerView.Adapter<TravellerLocationRecyclerViewAdapter.RecyclerViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_item_view,
            parent, false)

        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val trailerLocationName = itemView.findViewById<TextView>(R.id.tv_location_name)
        private val trailerLocationAddButton = itemView.findViewById<Button>(R.id.btn_add)

        fun setContents(pos: Int){
            trailerLocationName.text = list[pos]
            trailerLocationAddButton.setOnClickListener {
                print("$pos 클릭 확인")
            }
        }
    }
}