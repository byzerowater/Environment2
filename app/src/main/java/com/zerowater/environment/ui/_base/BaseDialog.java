package com.zerowater.environment.ui._base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by YoungSoo Kim on 2019-10-08.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 Dialog
 */
public abstract class BaseDialog<VDB extends ViewDataBinding> extends Dialog {

    /**
     * ViewDataBinding
     */
    private VDB mViewDataBinding;

    /**
     * 기본 상속형 Dialog
     *
     * @param context Context
     */
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 화면을 구성할 레이아웃 리소스를 가져온다
     *
     * @return 화면을 구성할 레이아웃 리소스
     */
    protected abstract int getLayout();

    /**
     * 화면 구성이 끝나면 호출되어진다
     *
     * @param viewDataBinding ViewDataBinding
     */
    protected abstract void onDialogCreated(VDB viewDataBinding);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayout(), null, false);
        setContentView(mViewDataBinding.getRoot());
        setCancelable(false);
        onDialogCreated(mViewDataBinding);
    }
}

