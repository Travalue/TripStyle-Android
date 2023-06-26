package com.tripstyle.tripstyle.ui.mypage

import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.travalue.R
import com.android.example.travalue.base.BaseFragment
import com.android.example.travalue.databinding.FragmentShareTravelDetailBinding
import com.android.example.travalue.ui.trailer.TrailerViewPagerAdapter
import com.tripstyle.tripstyle.MainActivity


class ShareTravelDetailFragment : BaseFragment<FragmentShareTravelDetailBinding>(R.layout.fragment_share_travel_detail) {

    val args: ShareTravelDetailFragmentArgs by navArgs()

    override fun initStartView() {
        super.initStartView()
        (activity as MainActivity).hideToolbar(false)

        (activity as MainActivity).binding.tvToolbarName.text = args.type
        if(args.type != "전체 보기") initMenu()

    }

    override fun initDataBinding() {
        super.initDataBinding()
        val adapter = TrailerViewPagerAdapter(getCardList())
        binding.rvCategoryList.adapter = adapter // 어댑터 생성
        binding.rvCategoryList.layoutManager = LinearLayoutManager(context)
        binding.rvCategoryList.setHasFixedSize(true)
    }

    private fun initMenu(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_category_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_edit_button -> {
                        navController.navigate(R.id.action_shareTravelDetailFragment_to_categoryEditFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initAfterBinding() {
        super.initAfterBinding()
    }

    private fun getCardList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example,
            R.drawable.card_img_example)
    }
}