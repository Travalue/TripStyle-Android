package com.android.example.travalue.ui.trailer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerDetailBinding
import com.android.example.travalue.ui.mypage.ShareTravelDetailFragmentArgs
import com.android.example.travalue.util.ScheduleAdapter
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class TrailerDetailFragment : BaseFragment<FragmentTrailerDetailBinding>(R.layout.fragment_trailer_detail){
    val args: TrailerDetailFragmentArgs by navArgs()

    override fun initStartView() {
        super.initStartView()
       // (activity as MainActivity).setToolbarTitle("trailer")
        (activity as MainActivity).hideToolbar(false)

        initMapView()
        animationScheduleView()
        clickFavorite()
        clickClip()
    }

    override fun initDataBinding() {
        super.initDataBinding()
        initScheduleView()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    private fun clickClip(){
        binding.btnClipLick.setOnClickListener {
            val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("url","https://${resources.getString(R.string.DEEP_LINK_DOMAIN)}/${args.postId}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,"URL이 복사되었습니다",Toast.LENGTH_SHORT).show()
        }
    }
    private fun initMapView(){
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment,it).commit()
            }


        val onMapReadyCallback = OnMapReadyCallback {
            val markers = mutableListOf<Marker>()

            //TODO : change marker hard code data
            val mapList = arrayListOf<LatLng>(
                LatLng(37.5670135, 126.9783740),
                LatLng(37.5666805, 126.9784147),
                LatLng(37.568307444233, 126.97675211537),
            )

            for(i in 0 until mapList.size){
                val markerTextview = TextView(context)
                markerTextview.textSize = 9f
                markerTextview.gravity = Gravity.CENTER_HORIZONTAL
                markerTextview.setBackgroundResource(R.drawable.ic_baseline_circle_black_24)
                markerTextview.setTextColor(Color.WHITE)
                markerTextview.text = (i+1).toString()

                val marker = Marker()
                marker.position = mapList[i]
                marker.icon = OverlayImage.fromView(markerTextview)
                markers.add(marker)
            }

            markers.forEach { marker ->
                marker.map = it
            }
        }

        mapFragment.getMapAsync(onMapReadyCallback)
    }
    private fun animationScheduleView(){
        binding.layoutScheduleTitle.setOnClickListener {
            if(binding.layoutScheduleDetail.visibility == View.VISIBLE){
                binding.layoutScheduleDetail.visibility = View.GONE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            }else{
                val viewHeight = binding.layoutScheduleDetail.height
                val scrollY = binding.scrollView.scrollY

                binding.layoutScheduleDetail.visibility = View.VISIBLE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(180f)
                }
                binding.scrollView.post {
                    binding.scrollView.smoothScrollTo(0,scrollY+viewHeight)
                }

            }
        }
    }

    private fun initScheduleView(){
        val adapter = ScheduleAdapter(getScheduleList())

        binding.rvSchedule.addItemDecoration(adapter.ItemDecorator(-80))
        binding.rvSchedule.adapter = ScheduleAdapter(getScheduleList())
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
    }

    // TODO : 추후에 데이터 연결 후 flag 로직 빼기
    private fun clickFavorite(){
        var flag = false
        binding.btnFavorite.setOnClickListener {
            if(!flag){
                binding.btnFavorite.setBackgroundResource(R.drawable.ic_heart_selected)
                flag = true
            }else{
                binding.btnFavorite.setBackgroundResource(R.drawable.ic_heart_fill)
            }

        }
    }

    //mockup data
    private fun getScheduleList(): ArrayList<String> {
        return arrayListOf(
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원",
            "순천 국가정원")
    }
}