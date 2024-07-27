package com.himanshu.rickandmorty

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.himanshu.rickandmorty.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val supportFragmentManager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container_layout, MainFragment(), "result fragment")
        ft.commitAllowingStateLoss()

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container_layout) as NavHostFragment).navController
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}