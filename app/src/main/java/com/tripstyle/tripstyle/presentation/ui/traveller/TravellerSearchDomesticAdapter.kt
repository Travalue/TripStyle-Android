package com.tripstyle.tripstyle.presentation.ui.traveller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripstyle.tripstyle.R
import com.bumptech.glide.Glide

class TravellerSearchDomesticAdapter(private val viewModel: TravellerSearchViewModel, val context: Context?):
    RecyclerView.Adapter<TravellerSearchDomesticAdapter.RecyclerViewViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(pos: Int, city: String, searchText: String)
    }

    private lateinit var onClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.traveller_search_item_view, parent, false)

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
        private val searchButton: ImageButton = itemView.findViewById(R.id.btn_search_result)

        init{
            itemView.setOnClickListener {
                val pos = adapterPosition

                if(pos != RecyclerView.NO_POSITION){
                    searchButton.setOnClickListener {
                        onClickListener.onItemClick(pos,QueryCityName.text.toString(),"")
                    }
                }
            }
        }

        fun setContents(pos: Int){
            with(viewModel.domesticCities[pos]){
                //세팅
                when(cities) {
                    "서울" -> Glide.with(itemView).load(R.drawable.ic_seoul3).into(cityImage)
                    "부산" -> Glide.with(itemView).load(R.drawable.ic_busan3).into(cityImage)
                    "제주" -> Glide.with(itemView).load(R.drawable.ic_jeju3).into(cityImage)
                    else -> Glide.with(itemView).load(R.drawable.default_profile_img).into(cityImage)
                }
                QueryCityName.text = cities
                QuerySpecificCityName.text = "국내 여행지"
            }

        }
    }
}