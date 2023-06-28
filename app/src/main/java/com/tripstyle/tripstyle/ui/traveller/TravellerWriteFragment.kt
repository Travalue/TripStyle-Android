package com.tripstyle.tripstyle.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTravellerSubEditorBinding
import com.tripstyle.tripstyle.databinding.FragmentTravellerWriteBinding
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.MainActivity
import kotlinx.coroutines.launch

class TravellerWriteFragment : BaseFragment<FragmentTravellerWriteBinding>(R.layout.fragment_traveller_write) {

    private var list = ArrayList<String>() // post image 넘어오는 array

    private lateinit var binding2: FragmentTravellerSubEditorBinding

//    private val adapter = ViewPagerAdapter(list,context)

    companion object{
        const val PHOTO_MAX_LENGTH = 10
        const val REQ_GALLERY = 1

        var flag = 0
        // 본문에 사진 추가할 때
        // 0 -> outer view (최상단 본문 사진)
        // 1 -> inner view (그 밑 본문 사진)
    }

    private val imageResultMultiple = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->
                if (it.clipData != null) {   // 사진 여러장 선택
                    val count = it.clipData!!.itemCount
                    if (count > PHOTO_MAX_LENGTH) {
                        // 아래 toast를 다른 표시 방법으로 변경할 것
                        Toast.makeText(context, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@registerForActivityResult
                    }

                    for (i in 0 until count) {
                        val imageUri = getRealPathFromURI(it.clipData!!.getItemAt(i).uri)
                        list.add(imageUri)
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    list.add(imageUri)
                }

                refreshViewPager() // viewpager 새로고침
                hideButton() // 갤러리에서 사진을 한번이라도 불러오고 나면 viewpager 위치에 있는 imageview(버튼 이미지)와 textview 숨기기

            }
        }
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
        (activity as MainActivity).setToolbarTitle("글 작성하기")

        val adapter = ViewPagerAdapter(list,context)
        binding.bodyImageViewPager.adapter = adapter
        binding.bodyImageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun initDataBinding() {
        super.initDataBinding()

        binding.tvAddSchedule.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
        binding.ivCalendar.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }

        binding.buttonBackgroundImage.setOnClickListener {
            // 갤러리에서 배경사진 1장 선택하여 배경사진칸에 넣음
            selectGallery(false)
        }

        binding.buttonBodyImageUnselected.setOnClickListener {
            // 갤러리에서 사진 여러장 선택하여 viewPager에 넣음
            flag = 0
            selectGallery(true)
        }

        binding.buttonBodyAdd.setOnClickListener {
            list.clear()
//            val addView = LayoutInflater.from(context).inflate(R.layout.fragment_traveller_sub_editor,null,false)
//            val newViewPager = addView.findViewById<ViewPager2>(R.id.bodyImageViewPager)

            //binding으로 다시, 얘가 위에 addView 대체
            binding2 = FragmentTravellerSubEditorBinding.inflate(LayoutInflater.from(context))


            val newAdapter = ViewPagerAdapter(list,context)
            binding2.bodyImageViewPager.adapter = newAdapter
            binding2.bodyImageViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            binding2.buttonBodyImageUnselected.setOnClickListener {
                flag = 1
                selectGallery(true)
            }

//            binding.container.addView(addView)
            binding.container.addView(binding2.root)
        }

        binding.ivBackground.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_categoryOptionFragment)
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    fun getRealPathFromURI(uri: Uri): String{

        val buildName = Build.MANUFACTURER
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

    fun refreshViewPager(){
        if(flag == 0)
            binding.bodyImageViewPager.adapter?.notifyDataSetChanged()
        else
            binding2.bodyImageViewPager.adapter?.notifyDataSetChanged()
    }

    fun hideButton(){
        if(flag == 0) {
            binding.ivPhotoUnselected.visibility = View.INVISIBLE
            binding.tvPhotoUnselected1.visibility = View.INVISIBLE
            binding.tvPhotoUnselected2.visibility = View.INVISIBLE
        }
        else{
            binding2.ivPhotoUnselected.visibility = View.INVISIBLE
            binding2.tvPhotoUnselected1.visibility = View.INVISIBLE
            binding2.tvPhotoUnselected2.visibility = View.INVISIBLE
        }
    }

    fun refreshBackgroundImage(imageUri: String){
        context?.let {
            Glide.with(it).load(imageUri)
                .centerCrop()
                .into(binding.ivBackground)
        }
    }


    fun selectGallery(isMultiple: Boolean){
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
            if(isMultiple == true){ // 사진 여러장 선택
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
                imageResultMultiple.launch(intent)
            }
            else{ // 사진 1장만 선택
                var intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
                imageResultSingle.launch(intent)
            }
        }
    }

}