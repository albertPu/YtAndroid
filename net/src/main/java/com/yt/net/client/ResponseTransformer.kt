package com.yt.net.client

import android.widget.Toast
import com.yt.net.entity.BaseResponse
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * created by Albert
 */
object ResponseTransformer {

    fun <T> handleResult(): ObservableTransformer<BaseResponse<T>, T> {
        return ObservableTransformer { observable ->
            observable.map {
                if (it.data != null) {
                    it.data
                } else {
                    throw CustomException.MsgException(it.message!!)
                }
            }.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { throwable: Throwable ->
                    Observable.error(CustomException.handleException(throwable))
                }.doOnError { e: Throwable ->
                   Toast.makeText(ApiStore.application, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

}

