package com.newarrival.radiant.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.lifecycleScope
import com.newarrival.radiant.databinding.ActivitySplashBinding
import com.newarrival.radiant.extensions.progressAnimation
import com.newarrival.radiant.helper.AppActivityManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private var job: Job? = null

    private var progressAnimator: ValueAnimator? = null

    override fun buildLayoutBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun onStart() {
        super.onStart()

        val delayTime: Long
        if (!AppActivityManager.isHasActivityStack(MainActivity::class.java)) {
            delayTime = 3500
        } else {
            delayTime = 2500
        }

        binding.progressbar.progress = 0
        job = lifecycleScope.launch {
            kotlin.runCatching {
                withTimeoutOrNull(3000) {
                    startProgressAnimation(delayTime)
                    launch {
                        delay(delayTime)
                    }
                }
            }.onSuccess {
                checkPermission()
            }.onFailure {
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopProgressAnimation()
        job?.cancel()
    }

    override fun onBackPressed() {
    }

    private fun startProgressAnimation(duration: Long) {
        progressAnimator?.cancel()
        progressAnimator = binding.progressbar.progressAnimation(duration, binding.progressbar.progress)
    }

    private fun stopProgressAnimation() {
        progressAnimator?.cancel()
        progressAnimator = null
    }

    private fun checkPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (!AppActivityManager.isHasActivityStack(MainActivity::class.java)) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        } else {
            startActivity(Intent(this@SplashActivity, PermissionActivity::class.java))
        }
        finish()
    }
}
