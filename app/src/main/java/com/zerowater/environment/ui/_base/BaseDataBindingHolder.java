package com.zerowater.environment.ui._base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YoungSoo Kim on 2019-10-16.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 RecyclerView.ViewHolder
 */
public abstract class BaseDataBindingHolder<VDB extends ViewDataBinding, T extends Object> extends RecyclerView.ViewHolder {

    /**
     * ViewDataBinding
     */
    private VDB mViewDataBinding;

    /**
     * 기본 상속형 RecyclerView.ViewHolder
     *
     * @param viewDataBinding ViewDataBinding
     */
    public BaseDataBindingHolder(VDB viewDataBinding) {
        super(viewDataBinding.getRoot());
        mViewDataBinding = viewDataBinding;
    }

    /**
     * 데이터를 뷰에 결합 시켜준다
     *
     * @param position 데이터 위치
     * @param data     데이터
     */
    public abstract void bind(int position, T data);

    /**
     * ViewDataBinding을 가져온다
     *
     * @return ViewDataBinding
     */
    public VDB getViewDataBinding() {
        return mViewDataBinding;
    }
}
