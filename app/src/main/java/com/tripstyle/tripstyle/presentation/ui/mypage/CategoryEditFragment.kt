package com.tripstyle.tripstyle.presentation.ui.mypage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.tripstyle.tripstyle.dialog.CategoryDialog
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.BaseResponseModel
import com.tripstyle.tripstyle.data.model.dto.CategoryUpdateRequest
import com.tripstyle.tripstyle.data.source.remote.CategoryService
import com.tripstyle.tripstyle.databinding.FragmentCategoryEditBinding
import com.tripstyle.tripstyle.di.AppClient
import com.tripstyle.tripstyle.dialog.onDialogListener
import com.tripstyle.tripstyle.util.Constant
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CategoryEditFragment :  BaseFragment<FragmentCategoryEditBinding>(R.layout.fragment_category_edit) {

    private lateinit var subjectCategory : String
    private lateinit var locationCategory : String
    private lateinit var imageUri : Uri

    override fun initStartView() {
        super.initStartView()

        initMenu()
        initCategorySpinner()
        initLocationSpinner()

    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
        clickButtonEvent()
        selectCoverImage()
    }

    // 메뉴 초기화
    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_category_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_confrim_button -> {
                        checkInput()
                        requestUpdateCategory(1,binding.etCategory.text.toString(),subjectCategory,locationCategory)
                        findNavController().navigate(R.id.action_categoryEditFragment_to_shareTravelFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun checkInput() {
        if(binding.etCategory.text.isEmpty()){
            showAlert("카테고리 이름을 입력하세요.")
        }
    }

    private fun showAlert(msg:String){
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show() };
    }

    //spinner data setup
    private fun initCategorySpinner(){
        val data = resources.getStringArray(R.array.subject_category)
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,data)
        binding.spinnerSubject.adapter = adapter
        binding.spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                subjectCategory = data[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    //spinner data setup
    private fun initLocationSpinner(){
        val data = resources.getStringArray(R.array.location_category)
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,data)
        binding.spinnerLocation.adapter = adapter
        binding.spinnerLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                locationCategory = data[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    //커버이미지 선택
    private fun selectCoverImage(){
        binding.ivIcon.setOnClickListener {
            selectGallery()
        }
    }

    //network 연결
    private fun requestUpdateCategory(categoryId:Int,title:String,subject:String,region:String){
        val service = AppClient.retrofit?.create(CategoryService::class.java)
        val image = setImagePart(imageUri)
        val requestModel = CategoryUpdateRequest(title = title, subject =subject, region = region, thumbnail =image)
        service?.updateCategory(categoryId,requestModel)?.enqueue(object : Callback<BaseResponseModel> {
            override fun onResponse(
                call: Call<BaseResponseModel>,
                response: Response<BaseResponseModel>
            ) {
                if(response.body()?.code==201){
                    showAlert("수정이 완료되었습니다")
                }else{
                    Toast.makeText(requireContext(),"카테고리 수정에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
                    Log.d("response",response.body().toString())
                }

            }

            override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                Log.d("data","네트워크 연결 실패 : ${t}")
            }

        })
    }


    //카테고리 삭제 API 호출
    private fun requestDeleteCategory(categoryId:Int){
        val service = AppClient.retrofit?.create(CategoryService::class.java)
        service?.deleteCategory(categoryId)?.enqueue(object : Callback<BaseResponseModel> {
            override fun onResponse(
                call: Call<BaseResponseModel>,
                response: Response<BaseResponseModel>
            ) {
                if(response.body()?.code==201){
                    showAlert("삭제가 완료되었습니다")
                }else{
                    Toast.makeText(requireContext(),"카테고리 수정에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
                    Log.d("response",response.body().toString())
                }

            }

            override fun onFailure(call: Call<BaseResponseModel>, t: Throwable) {
                Log.d("data","네트워크 연결 실패 : ${t}")
            }

        })
    }

    //카테고리 삭제 버튼 이벤트
    private fun clickButtonEvent(){
        binding.btnCategoryDelete.setOnClickListener {
            val title = getString(R.string.category_dialog_delete)
            val content = getString(R.string.category_dialog_delete_content)
            val dialog = CategoryDialog(title,content)
            dialog.setActionListener(object : onDialogListener{
                override fun onConfirmAction() {
                    requestDeleteCategory(1)
                    findNavController().navigate(R.id.action_categoryEditFragment_to_shareTravelFragment)
                }
            })
            dialog.show(parentFragmentManager,"categoryDelete")
        }
    }

    private val imageResultSingle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->
                imageUri = it.data!!
                val image = getRealPathFromURI(it.data!!)
                refreshBackgroundImage(image)
            }
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


    private fun refreshBackgroundImage(imageUri: String){
        context?.let {
            Glide.with(it).load(imageUri)
                .centerCrop()
                .into(binding.ivIcon)
        }
    }


    private fun selectGallery(){
        val readPermission = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)

        if(readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ),
                Constant.REQ_GALLERY
            )
        }else{
            var intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            imageResultSingle.launch(intent)
        }
    }

    private fun setImagePart(fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path)
        val requestFile =  RequestBody.create("image/*".toMediaTypeOrNull(),file)
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }
}