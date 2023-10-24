package com.newarrival.radiant.ui

import android.app.ActionBar
import android.app.Dialog
import android.content.pm.PackageManager
import android.content.res.Resources.Theme
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.view.Gravity
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.lifecycleScope
import com.newarrival.radiant.R
import com.newarrival.radiant.databinding.ActivityLedBinding
import com.newarrival.radiant.databinding.DialogWarningBinding
import com.newarrival.radiant.extensions.toast
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LedActivity : BaseActivity<ActivityLedBinding>() {

    private var job: Job? = null

    private var isTorchOn = false //记录当前手电筒状态，true为开启，false为关闭
    private var flashModel = 0
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null

    private var speed: Long = 500

    override fun buildLayoutBinding(): ActivityLedBinding {
        return ActivityLedBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.ivLight.setOnClickListener {
            toggle()
            updateView()
        }

        binding.btnToggle.setOnClickListener {
            normalMode()
        }
        binding.btnStrobing.setOnClickListener {
            strobingMode()
        }
        binding.btnSos.setOnClickListener {
            sosMode()
        }
    }

    private fun initCamera() {
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            for (id in cameraManager!!.getCameraIdList()) {
                val characteristics = cameraManager!!.getCameraCharacteristics(id)
                val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                if (hasFlash != null && hasFlash) {
                    cameraId = id
                    break
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun openFlashlight() {
        try {
            if (cameraManager == null) {
                initCamera()
            }
            if (cameraId != null) {
                //需要初始化cameraManager之后才能，调用setTorchMode方法
                //需要确认cameraId存在
                cameraManager!!.setTorchMode(cameraId!!, true)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeFlashlight() {
        try {
            if (cameraManager == null) {
                initCamera()
            }
            if (cameraId != null) {
                cameraManager!!.setTorchMode(cameraId!!, false)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun toggle() {
        val enabled = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
        if (enabled) {
            try {
                if (isTorchOn) {
                    isTorchOn = false
                    flashModel = 0
                    job?.cancel()
                    closeFlashlight()
                } else {
                    isTorchOn = true
                    flashModel = 0
                    job?.cancel()
                    openFlashlight()
                }
            } catch (e: Exception) {
                toast(e.message)
                isTorchOn = false
            }
        } else {
            toast("error, phone not support flash")
        }
    }

    private fun normalMode() {
        if (!isTorchOn) {
            return
        }
        if (flashModel == 0) {
            return
        }
        flashModel = 0
        job?.cancel()
        openFlashlight()
        updateView()
    }

    private fun strobingMode() {
        if (!isTorchOn) {
            return
        }
        if (flashModel == 1) {
            return
        }
        showWarningDialog(speed) {
            speed = it
            try {
                job?.cancel()
                flashModel = 1
                flipFlash()
                updateView()
            } catch (e: Exception) {
                toast(e.message)
                flashModel = 0
            }
        }
    }

    private fun sosMode() {
        if (!isTorchOn) {
            return
        }
        try {
            job?.cancel()
            flashModel = 2
            flipFlash()
            updateView()
        } catch (e: Exception) {
            toast(e.message)
            flashModel = 0
        }
    }

    private fun updateView() {
        binding.btnToggle.setImageResource(R.mipmap.ic_led_open)
        binding.btnStrobing.setImageResource(R.mipmap.ic_led_strobing)
        binding.btnSos.setImageResource(R.mipmap.ic_sos)
        if (isTorchOn) {
            binding.ivLight.setImageResource(R.mipmap.ic_led_light)
            binding.viewDot.setBackgroundResource(R.drawable.shape_fff947_r8)
            when (flashModel) {
                0 -> {
                    binding.btnToggle.setImageResource(R.mipmap.ic_led_open_at)
                }

                1 -> {
                    binding.btnStrobing.setImageResource(R.mipmap.ic_led_strobing_at)
                }

                2 -> {
                    binding.btnSos.setImageResource(R.mipmap.ic_sos_at)
                }
            }
        } else {
            binding.ivLight.setImageResource(R.mipmap.ic_led)
            binding.viewDot.setBackgroundResource(R.drawable.shape_4b4f5b_r8)
        }
    }

    private fun flipFlash() {
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = lifecycleScope.launch {
            while (isActive) {
                when (flashModel) {
                    0 -> {
                        cancel()
                    }

                    1 -> {
                        delay(speed)
                        closeFlashlight()
                        delay(speed)
                        openFlashlight()
                    }

                    2 -> {
                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(900)
                        closeFlashlight()
                        delay(900)
                        openFlashlight()

                        delay(900)
                        closeFlashlight()
                        delay(900)
                        openFlashlight()

                        delay(900)
                        closeFlashlight()
                        delay(900)
                        openFlashlight()

                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(400)
                        closeFlashlight()
                        delay(400)
                        openFlashlight()

                        delay(1100)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        isTorchOn = false
        job?.cancel()
        closeFlashlight()
        updateView()
    }

    private var warningDialog: Dialog? = null

    private fun showWarningDialog(speed: Long, confirmCallback: ((Long) -> Unit)) {
        if (warningDialog?.isShowing == true) {
            warningDialog?.dismiss()
        }
        val rootBinding = DialogWarningBinding.inflate(layoutInflater)
        warningDialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(rootBinding.root)
        }

        rootBinding.tvSpeed.text = "${speed}ms"
        rootBinding.seekbar.progress = (speed - 100).toInt()
        rootBinding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = p1 + 100
                rootBinding.tvSpeed.text = "${progress}ms"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        rootBinding.btnConfirm.setOnClickListener {
            warningDialog?.dismiss()
            val progress = rootBinding.seekbar.progress + 100
            confirmCallback.invoke(progress.toLong())
        }
        rootBinding.btnCancel.setOnClickListener {
            warningDialog?.dismiss()
        }
        warningDialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr
            }
            Color.TRANSPARENT
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setGravity(Gravity.BOTTOM)
        }
        warningDialog?.show()
    }
}