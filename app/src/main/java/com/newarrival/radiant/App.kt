package com.newarrival.radiant

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.newarrival.radiant.helper.AppActivityManager
import com.newarrival.radiant.ui.SplashActivity

class App : Application(){
    var startCount: Int = 0

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                AppActivityManager.pushActivity(p0)
            }

            override fun onActivityStarted(activity: Activity) {
                startCount++
                if (startCount == 1) {
                    if (activity !is SplashActivity) {
                        activity.startActivity(Intent(activity, SplashActivity::class.java))
                    }
                }
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
                startCount--
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                AppActivityManager.popActivity(p0)
            }
        })
    }

}