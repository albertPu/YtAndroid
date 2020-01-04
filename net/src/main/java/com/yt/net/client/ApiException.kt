package com.yt.net.client

/**
 * created by Albert
 */
class ApiException(var code: Int, var msg: String?) : Exception() {

    override val message: String?
        get() = msg


}
