package com.android.example.travalue.ui.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentMytravelListBinding
import java.util.*
import kotlin.collections.ArrayList

class MyTravelListFragment : BaseFragment<FragmentMytravelListBinding>(R.layout.fragment_mytravel_list) {

    private var allPlace :ArrayList<String> = arrayListOf("미국")
    private var placeIcon :ArrayList<String> = arrayListOf("🇺🇸")
    private var addPlace :ArrayList<String> =arrayListOf("미국")

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("나의 여행지 리스트")
    }

    override fun initDataBinding() {
        super.initDataBinding()


        if(addPlace.size > 0){
            binding.btnMyTravel.text=""
        }else{
            binding.btnMyTravel.text="아직 추가된 여행지 리스트가 없어요"
        }

        binding.placeList.adapter = MyTravelListAdapter(addPlace,true) // 어댑터 생성
        val gridLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,3)
        binding.placeList.layoutManager = gridLayoutManager

    }


    override fun initAfterBinding() {
        super.initAfterBinding()


        // editText 입력 감지
        binding.etTravel.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                val place = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("not implemented")
                // To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val place = binding.etTravel.text.toString()

                // 저장된 여행지를 입력했을때
                if(allPlace.contains(place)){
                    binding.btnAddTravel.isEnabled=true
                    binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                    binding.ivIcon.visibility = View.GONE
                    binding.tvAddIcon.visibility=View.GONE
                    binding.tvPlaceIcon.text = placeIcon[0]
                    binding.tvPlaceIcon.visibility=View.VISIBLE
                }else{
                    binding.btnAddTravel.isEnabled=false
                    binding.btnAddTravel.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
                    binding.ivIcon.visibility = View.VISIBLE
                    binding.tvAddIcon.visibility=View.VISIBLE
                    binding.tvPlaceIcon.visibility=View.GONE
                }

            }
        })

        // 여행지 추가 버튼 클릭시
        binding.btnAddTravel.setOnClickListener {
            val place = binding.etTravel.text.toString()

        }
    }

}