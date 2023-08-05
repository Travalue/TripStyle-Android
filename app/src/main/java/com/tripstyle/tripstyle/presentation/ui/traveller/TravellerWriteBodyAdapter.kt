package com.tripstyle.tripstyle.presentation.ui.traveller

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.TravellerWriteResult

class TravellerWriteBodyAdapter(private val viewModel: TravellerWriteViewModel, val context: Context?, val fragment: Fragment):
    RecyclerView.Adapter<TravellerWriteBodyAdapter.RecyclerViewViewHolder>() {

    private var currentPos: Int = -1

    // 갤러리 열때 필요한거
    companion object{
        const val REQ_GALLERY = 1
    }

    private val imageResultSingle = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->

                if(currentPos == -1)
                    return@let

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
                        updateViewModelAndRefresh(imageUri)
                    }
                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    updateViewModelAndRefresh(imageUri)
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.traveller_write_body_item_view, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.setContents(position)
    }

    override fun onViewRecycled(holder: RecyclerViewViewHolder) {
        holder.editText.removeTextChangedListener(holder.textWatcher)
        holder.textWatcher = null
        super.onViewRecycled(holder)
    }


    override fun getItemCount(): Int {
        // 본문 세트 수
        return viewModel.bodyItem.size
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val bodyImage: ImageView = itemView.findViewById(R.id.iv_body)
        val editText: EditText = itemView.findViewById(R.id.editTextBody)

        var textWatcher: TextWatcher? = null

        fun setContents(pos: Int){
            with(viewModel.bodyItem[pos]){

                // 이미지 세팅
                if (image.isNotBlank())
                    Glide.with(itemView).load(image).centerCrop().into(bodyImage)

                // 본문 텍스트 세팅
                editText.setText(text)

                editText.removeTextChangedListener(textWatcher)

                textWatcher = object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        // text 저장
                        viewModel.updateBodyTextItem(pos, s.toString())
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                }

                editText.addTextChangedListener(textWatcher)

            }

            bodyImage.setOnClickListener {
                if (viewModel.currentCheckedBodyImageIndex.value == pos) { // 이미지가 이미 선택된 상태인데 다시 선택하려고 클릭한 경우
                    viewModel.currentCheckedBodyImageIndex.value = -1
                    bodyImage.setBackgroundResource(0) // 파란 테두리 비활성화
                } else if (viewModel.currentCheckedBodyImageIndex.value == -1) {
                    // 이미지 들어있는지 확인하고, 안 들어있으면 바로 갤러리 열고 들어있으면 파란색 테두리 표시하고 하단 메뉴 띄우기
                    if (!viewModel.checkBodyImageExist(pos))
                        selectFromGallery(pos)
                    else {
                        bodyImage.setBackgroundResource(R.drawable.traveller_write_border) // 파란색 테두리 활성화
                        // 하단 메뉴 띄우기 -> Fragment에서 수행
                    }
                }
            }

        }
    }


    private fun getRealPathFromURI(uri: Uri): String{
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = fragment.activity?.contentResolver?.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun selectFromGallery(pos: Int){
        currentPos = pos

        val readPermission = ContextCompat.checkSelfPermission(fragment.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = ContextCompat.checkSelfPermission(fragment.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(readPermission == PackageManager.PERMISSION_DENIED || writePermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(fragment.requireActivity(), arrayOf(
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

    private fun updateViewModelAndRefresh(imageUri: String) {
        viewModel.updateBodyItem(currentPos, TravellerWriteResult(imageUri,""))
        notifyItemChanged(currentPos)
    }



}