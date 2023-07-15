package com.tripstyle.tripstyle.presentation.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.CategoryAddResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectBinding
import com.tripstyle.tripstyle.di.AppClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravellerCategoryOptionSubjectFragment : BaseFragment<FragmentTravellerCategoryOptionSubjectBinding>(R.layout.fragment_traveller_category_option_subject) {

    private val viewModel by activityViewModels<TravellerWriteViewModel>()

    var isImageUploaded = false
    var categoryCoverImageUri = ""

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
                        categoryCoverImageUri = imageUri
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    refreshCategoryCoverImage(imageUri) // 카테고리 커버 이미지 변경
                    categoryCoverImageUri = imageUri
                }

            }
        }
    }

    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("")
    }

    override fun initDataBinding() {
        super.initDataBinding()

        // 등록 버튼 활성화 관련
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                checkFields()
            }
        }

        binding.editTextCategoryName.addTextChangedListener(textWatcher)
        binding.editTextRegion.addTextChangedListener(textWatcher)


        binding.tvSubjectCategorySelected.setOnClickListener {
            navController.navigate(R.id.action_categoryOptionSubjectFragment_to_categoryOptionSubjectSelectFragment)
        }

        // 사용자가 선택한 카테고리 주제
        viewModel.categorySubjectLiveData.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank()) {
                binding.tvSubjectCategorySelected.text = it
                checkFields()
            }
        }

        binding.ivCategoryCover.setOnClickListener {
            selectGallery()
        }

        // (임시) 카테고리 추가 버튼
        // TODO: 툴바로 바꿀 것
        binding.btnAddCategory.setOnClickListener {
            Log.e("","add button clicked")
            requestAddCategory()
            navController.popBackStack()
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
        isImageUploaded = true
        checkFields(true)
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

    // 카테고리 추가하기 전 모든 필드값이 채워졌는지 확인하고 버튼 활성화
    fun checkFields(isImageUploadedNow: Boolean = false) {
        if (isImageUploadedNow) {
            binding.btnAddCategory.isEnabled =
                binding.editTextCategoryName.text.trim().isNotEmpty() &&
                        binding.editTextRegion.text.trim().isNotEmpty() &&
                        binding.tvSubjectCategorySelected.text.trim().isNotEmpty()
        } else {
            binding.btnAddCategory.isEnabled =
                binding.editTextCategoryName.text.trim().isNotEmpty() &&
                        binding.editTextRegion.text.trim().isNotEmpty() &&
                        binding.tvSubjectCategorySelected.text.trim().isNotEmpty() &&
                        isImageUploaded
        }
    }

    private fun requestAddCategory(){
        val file = File(categoryCoverImageUri)
        val thumbnail = MultipartBody.Part.createFormData("thumbnail", file.name, file.asRequestBody())
        val subject = binding.tvSubjectCategorySelected.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val title = binding.editTextCategoryName.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val region = binding.editTextRegion.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        //  TODO: userId 변경 필요
        val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())

        val service = AppClient.retrofit?.create(TravelService::class.java)

        service?.addCategory(thumbnail, subject, title, region, userId)?.enqueue(object : Callback<CategoryAddResponse> {
            override fun onResponse(call: Call<CategoryAddResponse>, response: Response<CategoryAddResponse>) {
                if (response.isSuccessful) {
                    Log.e("AddCategoryResponse:", response.body().toString())
                } else {
                    Log.e("AddCategoryResponse:", "응답 실패")
                }
            }

            override fun onFailure(call: Call<CategoryAddResponse>, t: Throwable) {
                Log.e("AddCategoryResponse:", "요청 실패", t)
            }
        })

    }

}