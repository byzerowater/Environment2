package com.zerowater.environment.common;

import android.Manifest;

/**
 * Created by YoungSoo Kim on 2017-05-11.
 * company Ltd
 * byzerowater@gmail.com
 */
public class Const {
    /**
     * 앱 권한들
     */
    public static final String[] APP_PERMISSION = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * FLAG false 0
     */
    public static final String FLAG_FALSE = "0";
    /**
     * FLAG ture 1
     */
    public static final String FLAG_TRUE = "1";
    /**
     * FLAG Y
     */
    public static final String FLAG_Y = "Y";
    /**
     * FLAG N
     */
    public static final String FLAG_N = "N";

    /**
     * 파일 권한 요청 코드
     */
    public static final int REQUEST_FILE_PERMISSION = 0x15;
    /**
     * 파일 권한 설정 요청 코드
     */
    public static final int REQUEST_SETTING_PERMISSION = 0x16;
    /**
     * 파일 설치 요청 코드
     */
    public static final int REQUEST_INSTALL = 0x17;

}
