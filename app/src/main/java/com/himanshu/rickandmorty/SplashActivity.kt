package com.himanshu.rickandmorty

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.himanshu.rickandmorty.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            window.decorView.windowInsetsController!!.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val scaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_in)

        // Start scale in animation
        binding.iconApp.startAnimation(scaleIn)

        scaleIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}