package com.zerowater.environment.ui.alert;

import android.app.Dialog;
import android.content.Context;

import com.zerowater.environment.R;
import com.zerowater.environment.data.vo.AlertResource;
import com.zerowater.environment.databinding.DialogAlertBinding;
import com.zerowater.environment.ui._base.BaseDialog;

import java.io.Serializable;

import androidx.annotation.NonNull;


/**
 * Created by YoungSoo Kim on 2019-09-30.
 * Zero Ltd
 * byzerowater@gmail.com
 * 알림 다이얼로그
 */
public class AlertDialog extends BaseDialog<DialogAlertBinding> {

    /**
     * 알림정보 리소스
     */
    private AlertResource mAlertResource;
    /**
     * 알림 버튼 클릭 리스너
     */
    private OnAlertClickListener mOnAlertClickListener;

    /**
     * 알림 다이얼로그
     *
     * @param context              Context
     * @param alertResource        알림정보 리소스
     * @param onAlertClickListener 알림 버튼 클릭 리스너
     */
    public AlertDialog(@NonNull Context context, AlertResource alertResource, OnAlertClickListener onAlertClickListener) {
        super(context);
        this.mAlertResource = alertResource;
        this.mOnAlertClickListener = onAlertClickListener;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_alert;
    }


    @Override
    protected void onDialogCreated(DialogAlertBinding viewDataBinding) {
        viewDataBinding.setAlertResource(mAlertResource);
        viewDataBinding.setOnAlertButtonClickListener(new OnAlertButtonClickListener() {
            @Override
            public void onPositiveClick() {
                mOnAlertClickListener.onPositiveClick(AlertDialog.this);
                dismiss();
            }

            @Override
            public void onNegativeClick() {
                mOnAlertClickListener.onNegativeClick(AlertDialog.this);
                dismiss();
            }
        });
    }

    /**
     * 알림 버튼 클릭 리스너(xml databinding)
     */
    public interface OnAlertButtonClickListener extends Serializable {
        /**
         * 긍정 버튼 클릭
         */
        void onPositiveClick();

        /**
         * 부정 버튼 클릭
         */
        void onNegativeClick();
    }

    /**
     * 알림 버튼 클릭 리스너
     */
    public interface OnAlertClickListener extends Serializable {
        /**
         * 긍정 버튼 클릭
         *
         * @param dialog Dialog
         */
        void onPositiveClick(Dialog dialog);

        /**
         * 부정 버튼 클릭
         *
         * @param dialog Dialog
         */
        void onNegativeClick(Dialog dialog);
    }

}
