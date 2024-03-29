package com.tripstyle.tripstyle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tripstyle.tripstyle.databinding.ActivityMainBinding
import com.tripstyle.tripstyle.data.model.dto.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityMainBinding

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        migrateToolbarNavigation()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun setToolbarTitle(tag: String){
        when (tag) {
            "none" -> {
                binding.toolbar.visibility = View.GONE
            }
            "프로필 편집" -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "프로필 편집"
            }
            "나의 여행지 리스트"-> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "나의 여행지 리스트"
            }
            "글 작성하기" -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "글 작성하기"
            }
            "일정/장소 첨부" -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "일정/장소 첨부"
                supportActionBar!!.setHomeAsUpIndicator(com.tripstyle.tripstyle.R.drawable.text_cancel) // 임시
            }
            else -> {
                binding.toolbar.visibility= View.VISIBLE
                binding.tvToolbarName.text = tag
            }
        }
    }

    private fun migrateToolbarNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(com.tripstyle.tripstyle.R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener{ controller ,destination,arguments ->
            // nav_graph xml 파일의 각 fragment의 label을 가져와서 보여줌
            binding.tvToolbarName.text = navController.currentDestination?.label

        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    //toolbar visible 설정하는 함수
    fun hideToolbar(state:Boolean){
        if(state){
            binding.toolbar.visibility = View.GONE
        }else{
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    fun setTitle(text:String){
        binding.tvToolbarName.text = text
    }

    fun hideBottomNav(state: Boolean){
        if(state){
            binding.bottomNav.visibility = View.GONE
        }else{
            binding.bottomNav.visibility = View.VISIBLE
        }
    }

    fun getUserViewModel(): UserViewModel {
        return userViewModel
    }
}