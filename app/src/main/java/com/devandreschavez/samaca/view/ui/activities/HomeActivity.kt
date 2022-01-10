package com.devandreschavez.samaca.view.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.devandreschavez.samaca.R
import com.devandreschavez.samaca.core.KeepStateNavigator
import com.devandreschavez.samaca.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        configNav()
        configPerNav()
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.exitOption ->{
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                R.id.reports ->{
                    findNavController(R.id.nav_host_fragment).navigate(R.id.userReportsFragment)
                    true
                }
                else -> false
            }
        }
    }
    fun configNav(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
    }
    @SuppressLint("RestrictedApi")
    fun configPerNav(){
        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        val navigator = KeepStateNavigator(this,navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.nav_graph)
//        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.setupWithNavController(navController)
    }
}