package com.example.simplemorty.presentation.screens.main_activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.simplemorty.R
import com.example.simplemorty.databinding.ActivityMainBinding
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private lateinit var binding: ActivityMainBinding
    private var backButtonCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar) // Устанавливаем тулбар в качестве экшн-бара
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Включаем кнопку "назад"

//???????????
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this@MainActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
    }
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
