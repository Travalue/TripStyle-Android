package com.tripstyle.tripstyle.presentation.ui.trailer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.Data
import com.tripstyle.tripstyle.data.model.dto.Schedule
import com.tripstyle.tripstyle.data.model.dto.TravelDetailResponse
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.data.source.remote.TravelService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailerDetailFragment : BaseFragment<FragmentTrailerDetailBinding>(R.layout.fragment_trailer_detail){
    private lateinit var detailResponse : Data
    val args: TrailerDetailFragmentArgs by navArgs()
    val service = AppClient.retrofit?.create(TravelService::class.java)

    override fun initStartView() {
        super.initStartView()
       // (activity as MainActivity).setToolbarTitle("trailer")
        (activity as MainActivity).hideToolbar(false)

        // API 조회
        requestTravelPost(args.postId)
    }

    override fun initDataBinding() {
        super.initDataBinding()

        animationScheduleView()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

        initFavorite()
        clickClip()
    }

    // 네트워크 요청
    private fun requestTravelPost(id:Int){
        service?.getTravelPost(id)?.enqueue(object : Callback<TravelDetailResponse>{
            override fun onResponse(
                call: Call<TravelDetailResponse>,
                response: Response<TravelDetailResponse>
            ) {
                detailResponse = response.body()?.data!!
                initView()
                initMapView()
            }

            override fun onFailure(call: Call<TravelDetailResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    // 초기화
    private fun initView(){
        binding.tvMainTitle.text = detailResponse.title
        binding.tvSubTitle.text = detailResponse.subTitle
        // 이미지 dim 처리
        Glide.with(this).load(detailResponse?.thumbnail).into(binding.ivMainImage)
        binding.ivMainImage.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);

        binding.tvViews.text = "${detailResponse.statistics.viewCount} views"
        binding.tvFavoriteCnt.text = "${detailResponse.statistics.likeCount}"

        // 일정 adapter
        val adapter = ScheduleAdapter(detailResponse.schedules as ArrayList<Schedule>)

        binding.rvSchedule.addItemDecoration(adapter.ItemDecorator(-80))
        binding.rvSchedule.adapter = adapter
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)

        // 컨텐츠 adapter
        val detailAdpater = TrailerDetailRecyclerViewAdapter(requireContext(), detailResponse.contents)
        binding.rvScheduleDetail.adapter = detailAdpater // 어댑터 생성

        with(binding.rvScheduleDetail){
            clipToPadding = false
            clipChildren = false
        }

        when(detailResponse?.subject){
            "식도락" -> Glide.with(this).load(R.drawable.ic_category_food).into(binding.ivCategory)
            "액티비티" -> Glide.with(this).load(R.drawable.ic_category_activity).into(binding.ivCategory)
            "패키지" -> Glide.with(this).load(R.drawable.ic_category_package).into(binding.ivCategory)
            "휴양" -> Glide.with(this).load(R.drawable.ic_category_refresh).into(binding.ivCategory)
        }

        //writer 영역
        binding.tvWriter.text = detailResponse.writer.nickname
        binding.tvDescription.text = detailResponse.writer?.description?.toString()
        Glide.with(this).load(detailResponse.writer.profileImageURL).apply(RequestOptions().circleCrop()).into(binding.ivUserProfile)
        binding.tvWriteCnt.text = "30"

        //좋아요
        if(detailResponse.statistics.liked){
            binding.btnFavorite.setImageResource(R.drawable.ic_heart_fill)
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
            val mapList = getMapData()

            polyline.setPattern(10,5)
            if(mapList.size >2){
                polyline.coords = mapList
            }

            for(i in 0 until mapList.size){
                val markerTextview = TextView(context)
                markerTextview.textSize = 9f
                markerTextview.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                markerTextview.setBackgroundResource(R.drawable.primary_color_round)
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

    private fun getMapData() : ArrayList<LatLng>{
        var makerList = arrayListOf<LatLng>()
        detailResponse.schedules.map {
            makerList.add(LatLng(it.latitude,it.longitude))
        }
        return makerList
    }

    private fun animationScheduleView(){
        binding.layoutScheduleTitle.setOnClickListener {
            if(binding.layoutScheduleDetail.visibility == View.VISIBLE){
                binding.layoutScheduleDetail.visibility = View.GONE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }else{
                val viewHeight = binding.layoutScheduleDetail.height
                val scrollY = binding.scrollView.scrollY

                binding.layoutScheduleDetail.visibility = View.VISIBLE
                binding.scheduleButton.animate().apply {
                    duration = 300
                    rotation(0f)
                }
                binding.scrollView.post {
                   // binding.scrollView.smoothScrollTo(0,scrollY+viewHeight)
                }

            }
        }
    }

    private fun initFavorite(){
        var flag = false
        binding.btnFavorite.setOnClickListener {
            if(!flag){
                service?.getLikePost(args.postId)?.enqueue(object : Callback<BaseResponseModel>{
                    override fun onResponse(
                        call: Call<BaseResponseModel>,
                        response: Response<BaseResponseModel>
                    ) {
                        if(response.body()?.code == 201){
                            binding.btnFavorite.setBackgroundResource(R.drawable.ic_heart_selected)
                            flag = true
                        }else{
                            Toast.makeText(context,response.body()?.message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                        Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                    }

                })
            }else{
                service?.getUnlikePost(args.postId)?.enqueue(object : Callback<BaseResponseModel>{
                    override fun onResponse(
                        call: Call<BaseResponseModel>,
                        response: Response<BaseResponseModel>
                    ) {
                        if(response.body()?.code == 200){
                            binding.btnFavorite.setBackgroundResource(R.drawable.ic_heart_fill)
                            flag = true
                        }else{
                            Toast.makeText(context,response.body()?.message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                        Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                    }

                })
            }

        }
    }

}