package com.zerowater.environment.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2016-08-01.
 * company Ltd
 * byzerowater@gmail.com
 * 토스트 관리자
 */
public class ToastManager {
    /**
     * Context
     */
    private Context mContext = null;
    /**
     * Toast
     */
    private Toast mToast = null;

    /**
     * 토스트 관리자
     *
     * @param context Context
     */
    public ToastManager(Context context) {
        mContext = context;
    }

    /**
     * 토스트 보여주기
     *
     * @param toastText 보여 줄 문자열
     */
    public void showToast(String toastText) {
        try {

            if (mToast == null) {
                mToast = Toast.makeText(mContext, toastText, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(toastText);
            }

            mToast.show();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * 토스트 보여주기
     *
     * @param resId 보여 줄 문자열 id
     */
    public void showToast(@StringRes int resId) {
        try {
            String toastText = mContext.getString(resId);
            if (mToast == null) {
                mToast = Toast.makeText(mContext, toastText, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(toastText);
            }

            mToast.show();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * 토스트 숨기기
     */
    public void hideToast() {
        try {
            if (mToast != null) {
                mToast.cancel();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
