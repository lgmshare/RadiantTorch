package com.newarrival.radiant.ui

import android.provider.Settings
import android.util.Log
import android.widget.SeekBar
import com.newarrival.radiant.databinding.ActivityScreenBinding

class ScreenLightActivity : BaseActivity<ActivityScreenBinding>() {

    private var isTorchOn = true //记录当前手电筒状态，true为开启，false为关闭

    private var origScreenBrightness: Float = 0.1f
    private var currScreenBrightness: Float = 0.1f

    override fun buildLayoutBinding(): ActivityScreenBinding {
        return ActivityScreenBinding.inflate(layoutInflater)
    }

    override fun initView() {
        origScreenBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat()
        Log.d("ScreenLightActivity",   "$origScreenBrightness")
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        setScreenBrightness(1.0f)
        binding.lightSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val brightness = (p1.toFloat() / 100).toFloat()
                setScreenBrightness(brightness)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.colorSeekbar.setOnColorChangeListener { progress, color ->
            binding.coverView.setBackgroundColor(color)
        }

        binding.btnToggle.setOnClickListener {
            if (isTorchOn) {
                isTorchOn = false
                binding.lightSeekbar.progress = (origScreenBrightness).toInt()
                setScreenBrightness(origScreenBrightness)
            } else {
                isTorchOn = true
                binding.lightSeekbar.progress = 100
                setScreenBrightness(1.0f)
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    /**
     *  @param brightness [0.1,1.0]
     */
    private fun setScreenBrightness(brightness: Float) {
        currScreenBrightness = brightness
        window.attributes = window.attributes.apply {
            screenBrightness = brightness
        }
    }
}