package com.zerowater.environment.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-07.
 * company Ltd
 * byzerowater@gmail.com
 * 유틸
 */
public class Util {

    /**
     * 키보드 가리기
     *
     * @param view View
     */
    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Data를 Map으로 변경
     *
     * @param d 변경 할 데이터
     * @return 변경 된 Map
     */
    public static Map<String, Object> toMap(Object d) {

        Map<String, Object> hashMap = new HashMap<>();

        if (d != null) {

            Field[] fieldlist = d.getClass().getDeclaredFields();

            for (Field field : fieldlist) {

                field.setAccessible(true);

                Object value = null;
                try {
                    value = field.get(d);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (value != null && !field.getName().equals("serialVersionUID")) {
                    hashMap.put(field.getName(), value);

                    Timber.d("value = " + value);
                }
            }
        }

        return hashMap;
    }
}

