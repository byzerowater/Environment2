package com.zerowater.environment.util;

import android.app.Dialog;

import java.util.List;

/**
 * Created by YoungSoo Kim on 2019-10-23.
 * yap company
 * byzerowater@gmail.com
 * 다이얼로그 유틸
 */
public class DialogUtil {

    /**
     * Dialog Dismiss
     *
     * @param dialogs 다이얼로그들
     */
    public static void dismissDialog(List<Dialog> dialogs) {
        if (dialogs != null) {
            for (Dialog dialog : dialogs) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * Dialog Dismiss
     *
     * @param dialog 다이얼로그
     */
    public static void dismissDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
