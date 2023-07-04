package com.tripstyle.tripstyle.util

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.model.Schedule

class ScheduleAdapter(private val dataSet: ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tv_number :TextView
        val tv_schedule_name : TextView
        val tv_schedule_address : TextView
        val line : View
        init {
            tv_number = view.findViewById(R.id.tv_number)
            tv_schedule_name = view.findViewById(R.id.tv_schedule_name)
            tv_schedule_address = view.findViewById(R.id.tv_schedule_address)
            line = view.findViewById(R.id.v_time_line)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == dataSet.size-1) holder.line.visibility = View.INVISIBLE
        holder.tv_number.text = (position+1).toString()
        holder.tv_schedule_name.text = dataSet[position].name
        holder.tv_schedule_address.text = dataSet[position].address
    }

    inner class ItemDecorator(val topSpace:Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if(position!=0) outRect.top = topSpace

        }
    }
}