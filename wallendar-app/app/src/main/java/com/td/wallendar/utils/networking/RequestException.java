package com.td.wallendar.utils.networking;

public class RequestException extends Exception {

    private final int code;

    public RequestException(int code) {
        super("RequestException with code: " + code);
        this.code = code;
    }

    public RequestException(Throwable throwable) {
        super(throwable);
        this.code = -1;
    }

    public int getCode() {
        return code;
    }
}
