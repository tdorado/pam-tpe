package com.td.wallendar.utils.networking

class RequestException : Exception {
    private val code: Int

    constructor(code: Int) : super("RequestException with code: $code") {
        this.code = code
    }

    constructor(throwable: Throwable?) : super(throwable) {
        this.code = -1
    }

    fun getCode(): Int {
        return code
    }
}