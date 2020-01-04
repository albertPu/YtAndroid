package com.yt.pay.smsmsg

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast
import com.yt.net.client.ResponseTransformer
import com.yt.net.entity.SMSMsgRequest
import com.yt.net.module.PayBank
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.regex.Pattern

class SMSReceiver : BroadcastReceiver() {

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {

        /**
         * 接收短信
         */
        val bundle = intent.extras
        if (bundle != null) {
            //通过pdus获得接收到的所有短信消息，获取短信内容
            val objects = bundle.get("pdus") as Array<Any>?

            //构建短信对象数组
            val smsMessages = arrayOfNulls<SmsMessage>(objects!!.size)
            for (i in objects.indices) {

                //获取单条短信内容，以pdu格式存，并生成短信对象
                smsMessages[i] = SmsMessage.createFromPdu(objects[i] as ByteArray)

                //发送方的电话号码
                val number = smsMessages[i]?.displayOriginatingAddress

                //获取短信的内容
                val content = smsMessages[i]?.displayMessageBody
                    ?.replace(" ", "")
                    ?.replace(",", "") ?: ""

                //使用Toast测试
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show()

                val sms = SMSMsgRequest(
                    getEndNo(content),
                    getPayMoney(content),
                    getDate(content),
                    getBankName(content),
                    getBalance(content),
                    content
                )

                PayBank.payBankSuccess(sms).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ResponseTransformer.handleResult())
                    .subscribe({

                    }, {

                    })
            }

            //拦截
            //            abortBroadcast();
        }

    }


    private fun getPayMoney(content: String): String? {
        try {
            val fsp = content.split("收入")
            val ssp = fsp[1].split("余额")
            // 小数
            var pattern = Pattern.compile("(\\d+\\.\\d+)")
            var matcher = pattern.matcher(ssp[0])
            if (matcher.find()) {
                return matcher.group(0)
            } else {
                pattern = Pattern.compile("(\\d+)")
                matcher = pattern.matcher(ssp[0])
                if (matcher.find()) {
                    return matcher.group(0)
                }
            }
        } catch (e: Exception) {

        }
        return null
    }

    private fun getBalance(content: String): String? {
        try {
            val fsp = content.split("余额")
            val pattern = Pattern.compile("(\\d+\\.\\d+)")
            val matcher = pattern.matcher(fsp[1])
            if (matcher.find()) {
                return matcher.group(0)
            }
        } catch (e: Exception) {

        }
        return null
    }

    private fun getBankName(content: String): String? {
        try {
            val index1 = content.indexOf("【")
            val index2 = content.indexOf("】")
            return content.substring(index1 + 1, index2)
        } catch (e: Exception) {
        }
        return null
    }

    private fun getEndNo(content: String): String? {
        try {
            val index = content.indexOf("尾号")
            val endNo = content.substring(index + 2, index + 6)
            return endNo
        } catch (e: Exception) {
        }
        return null
    }

    private fun getDate(content: String): String? {
        try {

            val index = content.indexOf("月")
            val month = content.substring(index - 2, index)
            val dayIndex = content.indexOf("日")
            val day = content.subSequence(index+1, dayIndex)
            val sf = content.substring(dayIndex+1, dayIndex + 6)
            return "${month}-${day} $sf"
        } catch (e: Exception) {
        }
        return null
    }
}
