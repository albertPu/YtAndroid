package com.yt.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.READ_SMS
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                READ_SMS
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this@MainActivity,
                RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(READ_SMS, RECEIVE_SMS),0
            )
        }//动态
    }
}
