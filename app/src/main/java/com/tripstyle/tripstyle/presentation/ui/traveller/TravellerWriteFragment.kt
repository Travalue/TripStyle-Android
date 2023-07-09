package com.tripstyle.tripstyle.presentation.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTravellerWriteBinding
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PolylineOverlay
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.util.ScheduleAdapter

class TravellerWriteFragment : BaseFragment<FragmentTravellerWriteBinding>(R.layout.fragment_traveller_write) {

    private var list = ArrayList<String>() // post image 넘어오는 array
    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    companion object{
        const val REQ_GALLERY = 1
    }


    private val imageResultSingle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->
                if (it.clipData != null) {   // 사진 여러장 선택
                    val count = it.clipData!!.itemCount
                    if (count > 1) {
                        // 아래 toast를 다른 표시 방법으로 변경할 것
                        Toast.makeText(context, "사진은 1장만 선택 가능합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@registerForActivityResult
                    }

                    for (i in 0 until count) {
                        val imageUri = getRealPathFromURI(it.clipData!!.getItemAt(i).uri)
                        refreshBackgroundImage(imageUri) // 배경이미지 새로고침
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    refreshBackgroundImage(imageUri) // 배경이미지 새로고침
                }

            }
        }
    }

    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("글 작성하기")

        val adapter = TravellerWriteBodyRecyclerViewAdapter(viewModel,context,this)
        binding.bodyRecyclerView.adapter = adapter
        binding.bodyRecyclerView.layoutManager = LinearLayoutManager(context)

        initView()
        initMapView()

    }

    override fun initDataBinding() {
        super.initDataBinding()

        viewModel.bodyItemListData.observe(viewLifecycleOwner){
            binding.bodyRecyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.scheduleItemListData.observe(viewLifecycleOwner){
            viewModel.scheduleItem.forEach {
                Log.e("","viewModel Data: ${it}")
            }
            initMapView()
            binding.rvSchedule.adapter?.notifyDataSetChanged()
        }

        binding.tvAddSchedule.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
        binding.ivCalendar.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }

        binding.buttonBackgroundImage.setOnClickListener {
            // 갤러리에서 배경사진 1장 선택하여 배경사진 칸에 넣음
            selectGallery()
        }

        binding.buttonBodyAdd.setOnClickListener {
            viewModel.addBodyItem()
        }

        binding.ivBackground.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_categoryOptionFragment)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun getRealPathFromURI(uri: Uri): String{
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }


    private fun refreshBackgroundImage(imageUri: String){
        context?.let {
            Glide.with(it).load(imageUri)
                .centerCrop()
                .into(binding.ivBackground)
        }
    }


    private fun selectGallery(){
        list.clear()

        val readPermission = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(readPermission == PackageManager.PERMISSION_DENIED || writePermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
                REQ_GALLERY
            )
        }else{
            var intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            imageResultSingle.launch(intent)
        }
    }






    /*



    여기 아래부터 지도 및 일정 관련



     */



    private fun initView(){
        // 일정 adapter
        val adapter = ScheduleAdapter(viewModel.scheduleItem)

        binding.rvSchedule.addItemDecoration(adapter.ItemDecorator(-80))
        binding.rvSchedule.adapter = adapter
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
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
        viewModel.scheduleItem.map {
            makerList.add(LatLng(it.latitude,it.longitude))
        }
        return makerList
    }



}