package com.tripstyle.tripstyle.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.MainActivity
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectBinding

class TravellerCategoryOptionSubjectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectBinding>(R.layout.fragment_traveller_category_option_subject) {

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
                        refreshCategoryCoverImage(imageUri) // 카테고리 커버 이미지 변경
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    refreshCategoryCoverImage(imageUri) // 카테고리 커버 이미지 변경
                }

            }
        }
    }

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()
        binding.tvSubjectCategorySelected.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionSubjectFragment_to_categoryOptionSubjectSelectFragment)
        }

        // 사용자가 선택한 카테고리 주제
        viewModel.categorySubjectLiveData.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank())
                binding.tvSubjectCategorySelected.text = it
        }

        binding.ivCategoryCover.setOnClickListener {
            selectGallery()
        }

    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun selectGallery(){
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
             // 사진 1장만 선택
            var intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            imageResultSingle.launch(intent)

        }
    }

    private fun refreshCategoryCoverImage(imageUri: String){
        context?.let {
            Glide.with(it).load(imageUri)
                .centerCrop()
                .into(binding.ivCategoryCover)
        }
        binding.tvCategoryCoverImageAdd.visibility = View.INVISIBLE
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

}