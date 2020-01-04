package com.yt.pay

import android.app.Application
import com.yt.net.client.ApiStore


class YTApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        ApiStore.init(this)
    }
}
