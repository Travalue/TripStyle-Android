package com.android.example.travalue.ui.traveller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.R
import com.bumptech.glide.Glide

class TravellerSearchRecyclerViewAdapter(private val viewModel: TravellerSearchViewModel, val context: Context?):
    RecyclerView.Adapter<TravellerSearchRecyclerViewAdapter.RecyclerViewViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(pos: Int, city: String, searchText: String)
    }

    private lateinit var onClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.traveller_search_item_view, parent, false)
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemViewBinding.inflate(inflater,parent,false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun getItemCount(): Int {
        // 검색결과에 표시될 item 개수
        return viewModel.domesticCities.size
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val cityImage: ImageView = itemView.findViewById(R.id.city_image)
        private val QueryCityName: TextView = itemView.findViewById(R.id.city_name)
        private val QuerySpecificCityName: TextView = itemView.findViewById(R.id.specific_city_name)

        init{
            itemView.setOnClickListener {
                val pos = adapterPosition

                if(pos != RecyclerView.NO_POSITION){
                    if(onClickListener != null){
                        cityImage.setOnClickListener {
                            onClickListener.onItemClick(pos,"","")
                            println("adapterposition: ${pos}")
                            println("${QueryCityName.text}")
                        }
                    }
                }
            }
        }

        fun setContents(pos: Int){
            with(viewModel.domesticCities[pos]){
                //세팅
                when(cities) {
                    "서울" -> Glide.with(itemView).load(R.drawable.ic_seoul2).into(cityImage)
                    "부산" -> Glide.with(itemView).load(R.drawable.ic_busan2).into(cityImage)
                    "제주" -> Glide.with(itemView).load(R.drawable.ic_jeju2).into(cityImage)
                    else -> Glide.with(itemView).load(R.drawable.profile_img).into(cityImage)
                }
                QueryCityName.text = cities
                QuerySpecificCityName.text = specificCities
            }

        }
    }
}