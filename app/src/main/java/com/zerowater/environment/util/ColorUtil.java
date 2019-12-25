package com.zerowater.environment.util;

import android.content.Context;

import androidx.core.content.ContextCompat;

/**
 * Created by YoungSoo Kim on 2018-06-21.
 * yap company
 * byzerowater@gmail.com
 * 컬러 설정 유틸
 */
public class ColorUtil {

    /**
     * 컬러 가져오기
     *
     * @param context  Context
     * @param colorRes 컬러 리소스
     * @return 컬러값
     */
    public static int getColor(Context context, int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }
}
