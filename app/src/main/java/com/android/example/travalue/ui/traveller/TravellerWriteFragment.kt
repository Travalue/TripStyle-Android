package com.android.example.travalue.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTravellerWriteBinding

class TravellerWriteFragment : BaseFragment<FragmentTravellerWriteBinding>(R.layout.fragment_traveller_write) {

    private var list = ArrayList<String>() // post image 넘어오는 array

    private val adapter = ViewPagerAdapter(list,context)

    companion object{
        const val PHOTO_MAX_LENGTH = 10
        const val REQ_GALLERY = 1
        const val PARAM_KEY_IMAGE = "image"
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->
                if (it.clipData != null) {   // 사진 여러장 선택
                    val count = it.clipData!!.itemCount
                    if (count > PHOTO_MAX_LENGTH) {
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

                refreshData() // viewpager 새로고침
                hideButton() // 갤러리에서 사진을 한번이라도 불러오고 나면 viewpager 위치에 있는 button과 textview 숨기기


            }
        }
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("글 작성하기")

//        val adapter = ViewPagerAdapter(list,context)

        binding.bodyImage.adapter = adapter
        binding.bodyImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun initDataBinding() {
        super.initDataBinding()

        // category 이동
//        binding.hambugerbar.setOnClickListener {
//            navController.navigate(R.id.action_travellerFragment_to_categoryDialogFragment)

//            val action = TravellerFragmentDirections.actionTravellerFragmentToCategoryDialogFragment(binding.tvTraveller.text.toString())
//            navController.navigate(action)
//        }

        binding.tvAddSchedule.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }
        binding.ivCalendar.setOnClickListener {
            navController.navigate(R.id.action_travellerWriteFragment_to_TravellerLocationFragment)
        }

        binding.buttonPhotoUnselected.setOnClickListener {
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
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
                imageResult.launch(intent)

            }
        }

        binding.tvPhotoUnselected1.setOnClickListener {
            refreshData()
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

    fun refreshData(){
        binding.bodyImage.adapter?.notifyDataSetChanged()
    }

    fun hideButton(){
        binding.ivPhotoUnselected.visibility = View.INVISIBLE
        binding.tvPhotoUnselected1.visibility = View.INVISIBLE
        binding.tvPhotoUnselected2.visibility = View.INVISIBLE
    }

//    private fun setAdapater(list:ArrayList<String>){
//        val adapter = context?.let { ViewPagerAdapter(list, it) }
//        binding.bodyImage.adapter = adapter
//        binding.bodyImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//    }
}