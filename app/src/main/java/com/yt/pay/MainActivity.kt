package com.yt.pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.READ_SMS
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.yt.pay.smsmsg.SMSReceiver
import kotlinx.android.synthetic.main.activity_main.*


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
        btn_msg.setOnClickListener {
            SMSReceiver.sendMsg(ed_msg.text.toString())
        }
    }
}
