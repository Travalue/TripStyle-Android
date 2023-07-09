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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.data.model.dto.TravellerWriteResult

class TravellerWriteBodyRecyclerViewAdapter(private val viewModel: TravellerWriteViewModel, val context: Context?, val fragment: Fragment):
    RecyclerView.Adapter<TravellerWriteBodyRecyclerViewAdapter.RecyclerViewViewHolder>() {

    private var list = ArrayList<String>() // post image 넘어오는
    private var currentPos: Int = -1

    // 갤러리 열때 필요한거
    companion object{
        const val PHOTO_MAX_LENGTH = 10
        const val REQ_GALLERY = 1
    }

    private val imageResultMultiple = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            result?.data?.let { it ->

                list.clear()

                if(currentPos == -1)
                    return@let

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

                    updateViewModelAndViewPager()

                } else {    // 사진 1장 선택
                    val imageUri = getRealPathFromURI(it.data!!)
                    list.add(imageUri)

                    updateViewModelAndViewPager()
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
        private val viewPager: ViewPager2 = itemView.findViewById(R.id.bodyImageViewPager)
        val editText: EditText = itemView.findViewById(R.id.editTextBody)
        private val buttonBodyImageUnselected: ImageButton = itemView.findViewById(R.id.buttonBodyImageUnselected)
        private val ivPhotoUnselected: ImageView = itemView.findViewById(R.id.iv_photo_unselected)
        private val tvPhotoUnselected1: TextView = itemView.findViewById(R.id.tv_photo_unselected1)
        private val tvPhotoUnselected2: TextView = itemView.findViewById(R.id.tv_photo_unselected2)

        var textWatcher: TextWatcher? = null

        fun setContents(pos: Int){
            with(viewModel.bodyItem[pos]){
                // viewpager adapter
                val adapter = ViewPagerAdapter(images as ArrayList<String>,context)
                viewPager.adapter = adapter
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

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

            buttonBodyImageUnselected.setOnClickListener {
                // 갤러리 열기
                selectFromGallery(pos)

                // 현재 밑 방법으로는 갤러리만 열고 사진을 추가하지 않더라도 사진추가 문구는 사라짐. 변경이 필요하면 변경할 것.
                ivPhotoUnselected.visibility = View.INVISIBLE
                tvPhotoUnselected1.visibility = View.INVISIBLE
                tvPhotoUnselected2.visibility = View.INVISIBLE
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
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            imageResultMultiple.launch(intent)
        }
    }

    private fun updateViewModelAndViewPager() {
        viewModel.updateBodyItem(currentPos, TravellerWriteResult(list,""))
        notifyItemChanged(currentPos)
    }




}