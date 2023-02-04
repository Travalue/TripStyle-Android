package com.android.example.travalue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.example.travalue.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

    }

    private fun setToolbar(){
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        // Toolbar에 표시되는 제목의 표시 유무를 설정. false로 해야 custom한 툴바의 이름이 화면에 보인다.
        ab.setDisplayShowTitleEnabled(false)
        // 뒤로가기 버튼
        ab.setDisplayHomeAsUpEnabled(true)
        //왼쪽 버튼 아이콘 변경
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_before)
    }

    fun setToolbarTitle(tag: String){
        when (tag) {
            "none" -> {
                binding.toolbar.visibility = View.GONE
                binding.tvComplete.visibility = View.GONE
            }
            "프로필 편집" -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "프로필 편집"
                binding.tvComplete.visibility = View.VISIBLE
            }
            "나의 여행지 리스트"-> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "나의 여행지 리스트"
                binding.tvComplete.visibility = View.VISIBLE
            }
            else -> {
                binding.toolbar.visibility= View.VISIBLE
                binding.tvToolbarName.text = tag
                binding.tvComplete.visibility = View.GONE
            }
        }
    }

}