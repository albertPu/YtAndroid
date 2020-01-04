package com.yt.net.entity

/**
 *Create By Albert on 2019/12/28
 */
class BaseResponse<T> {
    var code = 0
    var data: T? = null
    var message = ""
    var success = true
    var totalPage: Int? = null
}