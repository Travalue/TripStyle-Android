package com.tripstyle.tripstyle.presentation.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
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
import com.tripstyle.tripstyle.data.model.dto.TravellerWriteResult
import com.tripstyle.tripstyle.dialog.TravellerWriteDialog
import com.tripstyle.tripstyle.dialog.onDialogListener
import com.tripstyle.tripstyle.util.ScheduleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TravellerWriteFragment : BaseFragment<FragmentTravellerWriteBinding>(R.layout.fragment_traveller_write) {

    private var list = ArrayList<String>() // post image 넘어오는 array
    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    private lateinit var adapter: TravellerWriteBodyAdapter

    private lateinit var menuTextView: TextView

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
                        viewModel.updateMainBackgroundImage(imageUri)
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    viewModel.updateMainBackgroundImage(imageUri)
                }

            }
        }
    }

    private val bodyImageResultSingle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
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
                        editBodyImageWithIndex(viewModel.currentCheckedBodyImageIndex.value!!,imageUri)
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    editBodyImageWithIndex(viewModel.currentCheckedBodyImageIndex.value!!,imageUri)
                }

            }
        }
    }

    override fun initStartView() {
        super.initStartView()

        initAdapter()
        initMenu()
        initScheduleAdapter()
        initMapView()

    }

    override fun initDataBinding() {
        super.initDataBinding()

        initFirstSetting()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun initAdapter() {
        adapter = TravellerWriteBodyAdapter(viewModel,context,this)
        binding.bodyRecyclerView.adapter = adapter
        binding.bodyRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initFirstSetting() {
        // 본문 개수 최소 1개로 유지
        if(viewModel.bodyItem.isEmpty())
            viewModel.addBodyItem()

        // 등록 버튼 활성화 관련
        val textWatcherTitle: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                binding.tvPreviewTitle.text = s.toString()
                checkFields()
            }
        }

        val textWatcherSubTitle: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                binding.tvPreviewSubtitle.text = s.toString()
                checkFields()
            }
        }

        binding.editTextTitle.addTextChangedListener(textWatcherTitle)
        binding.editTextSubtitle.addTextChangedListener(textWatcherSubTitle)

        viewModel.bodyItemListData.observe(viewLifecycleOwner){
            binding.bodyRecyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.mainBackgroundImageLiveData.observe(viewLifecycleOwner){
            refreshBackgroundImage(it)
            checkFields(true)
        }

        viewModel.scheduleItemListData.observe(viewLifecycleOwner){
            initMapView()
            binding.rvSchedule.adapter?.notifyDataSetChanged()
        }

        // TODO: 선택된 카테고리가 있으면 상단 미리보기에 카테고리 주제를 표시하는 부분 추가해야함
        // 이렇게 하려면 선택된 카테고리의 주제를 받아올 수 있어야 함

        binding.tvAddSchedule.setOnClickListener {
            viewModel.updateAllBodyText()
            viewModel.updateTitleAndSubtitle(binding.editTextTitle.text.toString(),binding.editTextSubtitle.text.toString())

            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
        binding.ivCalendar.setOnClickListener {
            viewModel.updateAllBodyText()
            viewModel.updateTitleAndSubtitle(binding.editTextTitle.text.toString(),binding.editTextSubtitle.text.toString())

            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }

        binding.ivBackground.setOnClickListener {
            // 갤러리에서 배경사진 1장 선택하여 배경사진 칸에 넣음
            selectGallery(true)
        }

        binding.buttonBackgroundImage.setOnClickListener {
            // 갤러리에서 배경사진 1장 선택하여 배경사진 칸에 넣음
            selectGallery(true)
        }

        binding.buttonBodyAdd.setOnClickListener {
            viewModel.addBodyItem()
            checkFields()
        }

        viewModel.isBodyContentsExist.observe(viewLifecycleOwner){
            checkFields()
        }

        viewModel.currentCheckedBodyImageIndex.observe(viewLifecycleOwner){
            if(viewModel.currentCheckedBodyImageIndex.value != -1) {
                binding.bottomMenu.visibility = View.VISIBLE
                binding.bottomMenu.bringToFront()
            }
            else
                binding.bottomMenu.visibility = View.GONE
        }

        binding.layoutBottomEdit.setOnClickListener {
            selectGallery(false)
        }

        binding.layoutBottomDelete.setOnClickListener {
            deleteBodyImageWithIndex(viewModel.currentCheckedBodyImageIndex.value!!)
            checkFields()
        }
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


    // 배경 이미지 세팅
    private fun refreshBackgroundImage(imageUri: String){
        if(imageUri.isNotBlank()) {
            /*
            배경사진이 한번 들어오면 기본 사진으로 다시 돌아갈 일이 없으므로
            사진 추가 아이콘, 배경사진 추가 버튼, 제목/부제목 미리보기 등에 대한 처리는 여기서 같이 함
            */
            binding.layoutCoverAdd.visibility = View.INVISIBLE
            binding.buttonBackgroundImage.visibility = View.VISIBLE
            binding.tvPreviewTitle.visibility = View.VISIBLE
            binding.tvPreviewSubtitle.visibility = View.VISIBLE
            binding.ivBackground.isEnabled = false
            context?.let {
                Glide.with(it).load(imageUri)
                    .override(Resources.getSystem().displayMetrics.widthPixels,dpToPx(460))
                    .centerCrop()
                    .into(binding.ivBackground)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).roundToInt()
    }


    private fun selectGallery(isBackgroundImage: Boolean){
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
            if(isBackgroundImage) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                imageResultSingle.launch(intent)
            }
            else{
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                bodyImageResultSingle.launch(intent)
            }
        }
    }

    // 특정 위치의 본문 이미지 변경

    private fun editBodyImageWithIndex(index: Int, imageUri: String){
        viewModel.updateBodyItem(index, TravellerWriteResult(imageUri,""))
        adapter.notifyItemChanged(index)
        viewModel.currentCheckedBodyImageIndex.value = -1
    }

    // 특정 위치의 본문 이미지 삭제

    private fun deleteBodyImageWithIndex(index: Int){
        viewModel.updateBodyItem(index, TravellerWriteResult("",""))
        adapter.notifyItemChanged(index)
        viewModel.currentCheckedBodyImageIndex.value = -1
    }






    /*  지도 및 일정 관련  */



    private fun initScheduleAdapter(){
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



    /* 툴바 메뉴 관련 */

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_traveller_register, menu)

                // TextView 스타일 변경
                val menuItem = menu.findItem(R.id.menu_traveller_btn_register)
                val actionView = LayoutInflater.from(context).inflate(R.layout.traveller_menu_style, null)
                menuTextView = actionView.findViewById<TextView>(R.id.tv_menu_text_style_false)

                menuTextView.text = menuItem.title
                menuItem.actionView = actionView

                menuTextView.setOnClickListener {
                    when (menuItem.itemId) {
                        R.id.menu_traveller_btn_register -> {
                            viewModel.updateAllBodyText()
                            viewModel.updateTitleAndSubtitle(binding.editTextTitle.text.toString(),binding.editTextSubtitle.text.toString())

                            navController.navigate(R.id.action_travellerWriteFragment_to_categoryOptionFragment)
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        val dialog = TravellerWriteDialog("뒤로가기 시, 작성중인 글은 저장되지 않습니다.")
                        dialog.setActionListener(object : onDialogListener{
                            override fun onConfirmAction() {
                                // 데이터 비우기
                                viewModel.deleteAllItem()
                                navController.popBackStack()
                            }
                        })
                        dialog.show(parentFragmentManager,"")
                        true
                    }
                    else -> return false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setMenuTextViewEnabled(enabled: Boolean) {
        if(::menuTextView.isInitialized) {
            if (enabled) {
                // 메뉴(등록) 활성화
                menuTextView.isEnabled = true
                menuTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                // 비활성화
                menuTextView.isEnabled = false
                menuTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_959595))
            }
        }
    }

    // 필요한 필드값들이 채워졌는지 확인하고 메뉴 활성화/비활성화
    private fun checkFields(isBackgroundImageUploadedNow: Boolean = false) {
        lifecycleScope.launch(Dispatchers.Main) {
            if (isBackgroundImageUploadedNow) {
                if (
                    binding.editTextTitle.text.trim().isNotEmpty() &&
                    binding.editTextSubtitle.text.trim().isNotEmpty() &&
                    viewModel.isBodyContentsExist.value == true // 본문에 사진이든 글이든 컨텐츠 하나라도 있으면
                )
                    setMenuTextViewEnabled(true)
                else
                    setMenuTextViewEnabled(false)
            } else {
                if (
                    binding.editTextTitle.text.trim().isNotEmpty() &&
                    binding.editTextSubtitle.text.trim().isNotEmpty() &&
                    viewModel.isBodyContentsExist.value == true &&
                    viewModel.isMainBackgroundImageUploaded()
                )
                    setMenuTextViewEnabled(true)
                else
                    setMenuTextViewEnabled(false)
            }
        }
    }


}