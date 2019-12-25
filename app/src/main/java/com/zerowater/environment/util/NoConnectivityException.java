package com.zerowater.environment.util;

import java.io.IOException;

/**
 * Created by YoungSoo Kim on 2019-05-02.
 * Zero Ltd
 * byzerowater@gmail.com
 * NoConnectivityException
 */
public class NoConnectivityException extends IOException {

    /**
     * NoConnectivityException
     * 통신 연결 없을 경우 Exception
     *
     * @param message
     */
    public NoConnectivityException(String message) {
        super(message);
    }
}
