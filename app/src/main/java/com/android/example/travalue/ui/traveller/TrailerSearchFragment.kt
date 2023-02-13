package com.android.example.travalue.ui.traveller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.example.travalue.MainActivity
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentTrailerSearchBinding

class TrailerSearchFragment : BaseFragment<FragmentTrailerSearchBinding>(R.layout.fragment_trailer_search) {
    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).setToolbarTitle("visible")


        //recyclerView adapter
        binding.trailerSearchResult.adapter = TrailerSearchRecyclerViewAdapter(context)
        binding.trailerSearchResult.layoutManager = GridLayoutManager(context,3)
    }

    override fun initDataBinding() {
        super.initDataBinding()
    }

    override fun initAfterBinding() {
        super.initAfterBinding()

    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        (activity as MainActivity).setToolbarTitle("visible")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }


}