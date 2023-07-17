package com.tripstyle.tripstyle.presentation.ui.traveller

import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTravellerLocationBinding
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.data.source.remote.MapService
import com.tripstyle.tripstyle.data.model.dto.ItemData
import com.tripstyle.tripstyle.data.model.dto.Schedule
import com.tripstyle.tripstyle.data.model.dto.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerLocationFragment : BaseFragment<FragmentTravellerLocationBinding>(R.layout.fragment_traveller_location) {

    lateinit var adapter : TravellerLocationRecyclerViewAdapter
    lateinit var selectAdapter : TravellerLocationSelectedRecyclerViewAdapter

    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    private lateinit var menuTextView: TextView

    var selectedList = arrayListOf<ItemData>()

    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("일정/장소 첨부")
        viewModel.deleteScheduleItem()
        initMenu()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        //장소 리스트 adapter
        adapter = TravellerLocationRecyclerViewAdapter(context)
        adapter.setListener(object : onSelectedLocationListener {
            override fun selectLocation(itemData: ItemData) {
                addRecyclerviewData(itemData)
            }
        })
        binding.rvLocationList.adapter = adapter
        binding.rvLocationList.layoutManager = LinearLayoutManager(context)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.icSearch.setOnClickListener {
            val query = binding.editTextPlaceName.text.toString()
            getLocationList(query)
        }

    }

    fun addRecyclerviewData(itemData: ItemData){
        //recyclerview
        if(!binding.rvSelected.isVisible){
            binding.rvSelected.visibility = View.VISIBLE
            selectAdapter = TravellerLocationSelectedRecyclerViewAdapter(context,object :
                onRemovedLocationListener {
                override fun removeLocation(id: Int) {
                    selectedList.removeAt(id)
                    selectAdapter.notifyDataSetChanged()
                    if(selectedList.size == 0) binding.rvSelected.visibility = View.GONE
                }
            })
            binding.rvSelected.adapter = selectAdapter
            binding.rvSelected.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        selectedList.add(itemData)
        viewModel.addScheduleItem(Schedule(itemData.address,itemData.mapy.toDouble(),itemData.mapx.toDouble(),itemData.title))
        selectAdapter.setData(selectedList)
        selectAdapter.notifyDataSetChanged()

    }
    private fun getLocationList(query : String){
        val service = AppClient.locationRetrofit?.create(MapService::class.java)
        service?.getMapSerachResult(query)?.enqueue(object : Callback<SearchResult>{
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val items = response.body()?.items
                adapter.setData(items as ArrayList<ItemData>)
                adapter.notifyDataSetChanged()

                if (items.isEmpty()){
                    binding.tvNoResult1.visibility = View.VISIBLE
                    binding.tvNoResult2.visibility = View.VISIBLE
                }
                else{
                    binding.tvNoResult1.visibility = View.INVISIBLE
                    binding.tvNoResult2.visibility = View.INVISIBLE
                }

            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_traveller_add, menu)

                // TextView 스타일 변경
                val menuItem = menu.findItem(R.id.menu_traveller_btn_add)
                val actionView = LayoutInflater.from(context).inflate(R.layout.traveller_menu_style, null)
                menuTextView = actionView.findViewById<TextView>(R.id.tv_menu_text_style_false)

                menuTextView.text = menuItem.title
                menuItem.actionView = actionView

                menuTextView.setOnClickListener {
                    when (menuItem.itemId) {
                        R.id.menu_traveller_btn_add -> {
                            Log.e("","Schedule Add Button Clicked")
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}