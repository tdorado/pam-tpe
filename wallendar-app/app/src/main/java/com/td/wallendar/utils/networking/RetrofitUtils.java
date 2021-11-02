package com.td.wallendar.utils.networking;

import java.io.IOException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import retrofit2.Response;

public class RetrofitUtils {

    private RetrofitUtils() {
        // no-op
    }

    public static <T> Single<T> performRequest(final Single<Response<T>> request) {
        return request
                .onErrorResumeNext(t -> Single.error(RetrofitUtils.convertException(t)))
                .map(RetrofitUtils::unwrapResponse);
    }

    public static <T> T unwrapResponse(final Response<T> response) throws RequestException {
        if (RetrofitUtils.isRequestFailed(response)) {
            throw new RequestException(response.code());
        }
        return response.body();
    }

    public static Throwable convertException(final Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return new NoConnectivityException(throwable);
        }
        if (throwable instanceof IOException) {
            return new RequestException(throwable);
        }
        return throwable;
    }

    private static boolean isRequestFailed(final Response response) {
        return !response.isSuccessful() || isErrorCode(response.code());
    }

    private static boolean isErrorCode(final int statusCode) {
        return statusCode >= 400 && statusCode < 600;
    }
}
