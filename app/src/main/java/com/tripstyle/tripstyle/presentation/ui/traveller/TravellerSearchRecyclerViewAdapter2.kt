package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.bumptech.glide.Glide

class TravellerSearchRecyclerViewAdapter2(private val viewModel: TravellerSearchViewModel, val context: Context?):
    RecyclerView.Adapter<TravellerSearchRecyclerViewAdapter2.RecyclerViewViewHolder>() {


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
        return viewModel.overseasCities.size
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
            with(viewModel.overseasCities[pos]){
                //세팅
                when(cities) {
                    "아시아" -> Glide.with(itemView).load(R.drawable.ic_asia3).into(cityImage)
                    "유럽" -> Glide.with(itemView).load(R.drawable.ic_europe3).into(cityImage)
                    "아메리카" -> Glide.with(itemView).load(R.drawable.ic_america3).into(cityImage)
                    "아프리카" -> Glide.with(itemView).load(R.drawable.ic_africa3).into(cityImage)
                    else -> Glide.with(itemView).load(R.drawable.default_profile_img).into(cityImage)
                }
                QueryCityName.text = cities
                QuerySpecificCityName.text = "해외 여행지"
            }

        }
    }
}