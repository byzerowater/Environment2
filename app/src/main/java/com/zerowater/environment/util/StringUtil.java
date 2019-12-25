package com.zerowater.environment.util;

import android.text.TextUtils;

import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2016-06-17.
 * company Ltd
 * byzerowater@gmail.com
 * 문자열 유틸
 */
public class StringUtil {

    /**
     * 문자열 빈값 확인
     *
     * @param s 확인할 문자
     * @return 빈값 확인 결과
     */
    public static boolean isEmpty(String s) {
        boolean isEmpty = false;

        if (TextUtils.isEmpty(s) || s.trim().length() == 0 || "null".equalsIgnoreCase(s)) {
            isEmpty = true;
        }

        return isEmpty;
    }

    /**
     * 문자열 빈값 여부에 따라 대체 문자로 처리
     *
     * @param s       확인 할 문자열
     * @param replace 대체 문자열
     * @return 처리 된 문자
     */
    public static String isEmptyReplace(String s, String replace) {
        return isEmpty(s) ? replace : s;
    }

    /**
     * String 형을 int 형으로 변환
     *
     * @param s 변환 할 String
     * @return 변환 된 int
     */
    public static int getInt(String s) {
        int i = 0;
        if (!isEmpty(s)) {
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                Timber.e(e);
            }
        }

        return i;
    }

    /**
     * String 형을 double 형으로 변환
     *
     * @param s 변환 할 String
     * @return 변환 된 double
     */
    public static double getDouble(String s) {
        double d = 0;
        if (!isEmpty(s)) {
            try {
                d = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                Timber.e(e);
            }
        }

        return d;
    }

}
