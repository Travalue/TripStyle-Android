package com.android.example.travalue.ui.traveller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R
import com.android.example.travalue.network.res.ItemData
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.NaverMap

class TravellerLocationRecyclerViewAdapter(val context: Context?):
    RecyclerView.Adapter<TravellerLocationRecyclerViewAdapter.RecyclerViewViewHolder>() {

    private var list = arrayListOf<ItemData>() // TOOD : 추 후에 item type 변경
    private lateinit var listener : onSelectedLocationListener

    fun setData(data: ArrayList<ItemData>){
        this.list = data
    }

    fun setListener(listener: onSelectedLocationListener){
        this.listener = listener
    }

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
            var item = list[pos]
            item.title = list[pos].title.replace(Regex("<\\/?(b|B)>"), "")
            trailerLocationName.text = item.title
            trailerLocationAddButton.setOnClickListener {
                var tm128 = Tm128(list[pos].mapx.toDouble(),list[pos].mapy.toDouble())

                item.mapx = tm128.toLatLng().longitude.toString()
                item.mapy = tm128.toLatLng().latitude.toString()

                listener.selectLocation(item)
            }
        }
    }
}

interface onSelectedLocationListener {
    fun selectLocation(item:ItemData)
}