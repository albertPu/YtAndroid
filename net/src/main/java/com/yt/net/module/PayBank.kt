package com.yt.net.module

import com.yt.net.client.Api
import com.yt.net.client.ApiStore
import com.yt.net.entity.BaseResponse
import com.yt.net.entity.SMSMsgRequest
import io.reactivex.Observable

object PayBank {

    fun payBankSuccess(sms:SMSMsgRequest):Observable<BaseResponse<Any>>{
       return ApiStore.create(Api::class.java).payBankSuccuess(sms)
    }
}