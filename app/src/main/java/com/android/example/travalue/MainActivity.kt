package com.android.example.travalue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.example.travalue.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setToolbar()

        migrateToolbarNavigation()

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
            }
            "visibleComplete" -> {
                binding.toolbar.visibility = View.VISIBLE
                binding.tvToolbarName.text = "프로필 편집"
                binding.tvComplete.visibility = View.VISIBLE
            }
            else -> {
                binding.toolbar.visibility= View.VISIBLE
                binding.tvToolbarName.text = tag
            }
        }
    }

    private fun migrateToolbarNavigation(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener{ _,_,_ ->
            // nav_graph xml 파일의 각 fragment의 label을 가져와서 보여줌
            binding.tvToolbarName.text = navController.currentDestination?.label
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

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

}