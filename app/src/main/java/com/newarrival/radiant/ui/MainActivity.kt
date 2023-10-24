package com.newarrival.radiant.ui

import android.content.Intent
import com.newarrival.radiant.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun buildLayoutBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnLed.setOnClickListener {
            startActivity(Intent(this@MainActivity, LedActivity::class.java))
        }
        binding.btnFlashlight.setOnClickListener {
            startActivity(Intent(this@MainActivity, ScreenLightActivity::class.java))
        }
    }
}