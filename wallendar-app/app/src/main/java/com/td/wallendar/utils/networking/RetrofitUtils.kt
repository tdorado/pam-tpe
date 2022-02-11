package com.td.wallendar.utils.networking

import com.td.wallendar.utils.networking.RequestException
import io.reactivex.Single
import retrofit2.Response
import java.net.UnknownHostException

object RetrofitUtils {
    fun <T> performRequest(request: Single<Response<T>>): Single<T> {
        return request
                .onErrorResumeNext { t: Throwable -> Single.error(convertException(t)) }
                .map { obj: Response<T> -> unwrapResponse(obj) }
    }

    @Throws(RequestException::class)
    fun <T> unwrapResponse(response: Response<T>): T {
        if (isRequestFailed(response)) {
            throw RequestException(response.code())
        }
        return response.body()!!
    }

    fun convertException(throwable: Throwable): Throwable {
        return NoConnectivityException((throwable as UnknownHostException))
    }

    private fun isRequestFailed(response: Response<*>): Boolean {
        return !response.isSuccessful || isErrorCode(response.code())
    }

    private fun isErrorCode(statusCode: Int): Boolean {
        return statusCode in 400..599
    }
}