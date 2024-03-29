package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.ItemData

class TravellerLocationSelectedAdapter(val context: Context?, val listener: onRemovedLocationListener):
    RecyclerView.Adapter<TravellerLocationSelectedAdapter.RecyclerViewViewHolder>() {

    private var list = arrayListOf<ItemData>()

    fun setData(list: ArrayList<ItemData>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_name_view,
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
        private val selectedName = itemView.findViewById<TextView>(R.id.tv_select_name)
        private val deleteButton = itemView.findViewById<ImageView>(R.id.btn_delete)

        fun setContents(pos: Int){
            selectedName.text = list[pos].title
            deleteButton.setOnClickListener {
                listener.removeLocation(pos)
            }
        }
    }
}

interface onRemovedLocationListener{
    fun removeLocation(id:Int)
}