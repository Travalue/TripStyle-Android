package com.tripstyle.tripstyle.ui.trailer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTrailerDetailBinding
import com.tripstyle.tripstyle.util.ScheduleAdapter
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PolylineOverlay
import com.tripstyle.tripstyle.MainActivity

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
        initData()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    private fun initData(){
        val list = arrayListOf<TrailerDetail>()
        list.add(TrailerDetail("dfksljdfksjdkf",R.drawable.card_img_example))
        list.add(TrailerDetail("dfksljdfksjdkf",R.drawable.card_img_example))
        list.add(TrailerDetail("dfksljdfksjdkf",R.drawable.card_img_example))
        list.add(TrailerDetail("dfksljdfksjdkf",R.drawable.card_img_example))

        val adapter = TrailerDetailRecyclerViewAdapter(list)
        binding.rvScheduleDetail.adapter = adapter // 어댑터 생성

        with(binding.rvScheduleDetail){
            clipToPadding = false
            clipChildren = false
        }
    }
    private fun clickClip(){
        binding.btnClipLick.setOnClickListener {
            val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("url","https://${resources.getString(R.string.DEEP_LINK_DOMAIN)}/${args.postId}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,"URL이 복사되었습니다",Toast.LENGTH_SHORT).show()
            binding.btnClipLick.setBackgroundResource(R.drawable.ic_link_02)
        }
    }
    private fun initMapView(){
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment,it).commit()
            }
        binding.ivMapTransparent.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> true
            }
        }

        val onMapReadyCallback = OnMapReadyCallback {
            //ui zoom in/out 버튼 없애기
            it.uiSettings.isZoomControlEnabled = false
            it.uiSettings.isScaleBarEnabled = false

            val markers = mutableListOf<Marker>()
            val polyline = PolylineOverlay()

            //TODO : change marker hard code data
            val mapList = arrayListOf<LatLng>(
                LatLng(37.5670135, 126.9783740),
                LatLng(37.5666805, 126.9784147),
                LatLng(37.568307444233, 126.97675211537),
            )

            polyline.setPattern(10,5)
            polyline.coords = mapList

            for(i in 0 until mapList.size){
                val markerTextview = TextView(context)
                markerTextview.textSize = 9f
                markerTextview.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                markerTextview.setBackgroundResource(R.drawable.ic_baseline_circle_black_24)
                markerTextview.setTextColor(Color.WHITE)
                markerTextview.text = (i+1).toString()

                val marker = Marker()
                marker.position = mapList[i]
                marker.icon = OverlayImage.fromView(markerTextview)
                markers.add(marker)
            }

            polyline.map = it
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