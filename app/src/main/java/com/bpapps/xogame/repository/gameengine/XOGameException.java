package com.bpapps.xogame.repository.gameengine;

public class XOGameException extends Exception {
    public XOGameException() {
    }

    public XOGameException(String message) {
        super(message);
    }

    public XOGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public XOGameException(Throwable cause) {
        super(cause);
    }

    public XOGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
