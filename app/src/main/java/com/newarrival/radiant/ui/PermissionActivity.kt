package com.newarrival.radiant.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.newarrival.radiant.BuildConfig
import com.newarrival.radiant.databinding.ActivityPermissionBinding
import com.newarrival.radiant.helper.AppActivityManager

class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {

    override fun buildLayoutBinding(): ActivityPermissionBinding {
        return ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnLater.setOnClickListener {
            finish()
        }

        binding.btnAgree.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), 2000)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2000) {
            if (grantResults[0] == 0) {
                if (!AppActivityManager.isHasActivityStack(MainActivity::class.java)) {
                    startActivity(Intent(this@PermissionActivity, MainActivity::class.java))
                }
                finish()
            } else {
                showTipsDialog("Camera permission is required. Please enable mobile camera permission in settings") {
                    start()
                }
            }
        }
    }

    //跳转
    private fun start() {
        val intent = Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(intent);
    }
}