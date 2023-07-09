package com.tripstyle.tripstyle.presentation.ui.traveller

import androidx.recyclerview.widget.GridLayoutManager
import com.tripstyle.tripstyle.R
import com.tripstyle.tripstyle.base.BaseFragment
import com.tripstyle.tripstyle.databinding.FragmentTrailerSearchBinding
import com.tripstyle.tripstyle.MainActivity

class TrailerSearchFragment : BaseFragment<FragmentTrailerSearchBinding>(R.layout.fragment_trailer_search) {
    override fun initStartView() {
        super.initStartView()
//        (activity as MainActivity).setToolbarTitle("visible")


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