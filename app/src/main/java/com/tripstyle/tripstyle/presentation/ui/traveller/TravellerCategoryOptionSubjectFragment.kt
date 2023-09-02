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
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.data.model.dto.CategoryAddResponse
import com.tripstyle.tripstyle.data.source.remote.TravelService
import com.tripstyle.tripstyle.databinding.FragmentTravellerCategoryOptionSubjectBinding
import com.tripstyle.tripstyle.di.AppClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    private lateinit var menuTextView: TextView

    companion object {
        const val REQ_GALLERY = 1
    }

    private val imageResultSingle =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
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
                            viewModel.updateCategoryCoverImage(imageUri)
                        }
                    } else {    // 사진 1장 선택
                        val imageUri = getRealPathFromURI(it.data!!)
                        viewModel.updateCategoryCoverImage(imageUri)
                    }

                }
            }
        }

    override fun initStartView() {
        super.initStartView()

        initMenu()
    }

    override fun initDataBinding() {
        super.initDataBinding()

        initFirstSetting()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

    private fun initFirstSetting() {
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
        viewModel.categorySubjectLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                binding.tvSubjectCategorySelected.text = it
                checkFields()
            }
        }

        // 카테고리 커버 이미지
        viewModel.categoryCoverImageLiveData.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank()){
                refreshCategoryCoverImage(it)
                checkFields(true)
            }
        }

        binding.ivCategoryCover.setOnClickListener {
            selectGallery()
        }
    }

    private fun selectGallery() {
        val readPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writePermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (readPermission == PackageManager.PERMISSION_DENIED || writePermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQ_GALLERY
            )
        } else {
            // 사진 1장만 선택
            var intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            imageResultSingle.launch(intent)
        }
    }

    private fun refreshCategoryCoverImage(imageUri: String) {
        context?.let {
            Glide.with(it).load(imageUri)
                .into(binding.ivCategoryCover)
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }


    /* 툴바 메뉴 관련 */

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

    // 카테고리 추가하기 전 모든 필드값이 채워졌는지 확인하고 버튼 활성화/비활성화
    fun checkFields(isImageUploadedNow: Boolean = false) {
        lifecycleScope.launch(Dispatchers.Main) {
            if (isImageUploadedNow) {
                if (
                    binding.editTextCategoryName.text.trim().isNotEmpty() &&
                    binding.editTextRegion.text.trim().isNotEmpty() &&
                    binding.tvSubjectCategorySelected.text.trim().isNotEmpty()
                )
                    setMenuTextViewEnabled(true)
                else
                    setMenuTextViewEnabled(false)
            } else {
                if (
                    binding.editTextCategoryName.text.trim().isNotEmpty() &&
                    binding.editTextRegion.text.trim().isNotEmpty() &&
                    binding.tvSubjectCategorySelected.text.trim().isNotEmpty() &&
                    viewModel.isCategoryCoverImageUploaded()
                )
                    setMenuTextViewEnabled(true)
                else
                    setMenuTextViewEnabled(false)
            }
        }
    }

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_traveller_add, menu)

                // TextView 스타일 변경
                val menuItem = menu.findItem(R.id.menu_traveller_btn_add)
                val actionView = LayoutInflater.from(context).inflate(R.layout.traveller_menu_style, null)
                menuTextView = actionView.findViewById<TextView>(R.id.tv_menu_text_style_false)

                menuTextView.text = menuItem.title
                menuItem.actionView = actionView

                menuTextView.setOnClickListener {
                    when (menuItem.itemId) {
                        R.id.menu_traveller_btn_add -> {
                            requestAddCategory{
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }




    /* API CALL */

    private fun requestAddCategory(onSuccess: () -> Unit) {

        val file : File
        if(viewModel.isCategoryCoverImageUploaded())
            file = File(viewModel.categoryCoverImageLiveData.value!!)
        else
            return

        val thumbnail =
            MultipartBody.Part.createFormData("thumbnail", file.name, file.asRequestBody())
        val subject = binding.tvSubjectCategorySelected.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val title = binding.editTextCategoryName.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        val region = binding.editTextRegion.text.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())
        //  TODO: userId 변경 필요
        val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())

        val service = AppClient.retrofit?.create(TravelService::class.java)

        service?.addCategory(thumbnail, subject, title, region, userId)
            ?.enqueue(object : Callback<CategoryAddResponse> {
                override fun onResponse(
                    call: Call<CategoryAddResponse>,
                    response: Response<CategoryAddResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("AddCategoryResponse:", response.body().toString())
                        onSuccess()
                    } else {
                        Log.e("AddCategoryResponse:", "응답 실패")
                        // TODO: 다이얼로그 띄우기?
                    }
                }

                override fun onFailure(call: Call<CategoryAddResponse>, t: Throwable) {
                    Log.e("AddCategoryResponse:", "요청 실패", t)
                    // TODO: 다이얼로그 띄우기?
                }
            })

    }

}