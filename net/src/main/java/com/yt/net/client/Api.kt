package com.yt.net.client

import com.yt.net.entity.BaseResponse
import com.yt.net.entity.SMSMsgRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * created by Albert
 */
interface Api {

    @POST("pay/banksuccess")
    fun payBankSuccuess(@Body smsBody:SMSMsgRequest): Observable<BaseResponse<Any>>

}
