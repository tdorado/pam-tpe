package com.td.wallendar.utils.networking;

public class RequestException extends Exception {
    public RequestException(int code) {
        super("RequestException with code:" + code);
    }

    public RequestException(Throwable throwable) {
        super(throwable);
    }
}
