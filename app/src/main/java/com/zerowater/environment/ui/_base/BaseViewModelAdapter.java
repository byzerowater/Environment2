package com.zerowater.environment.ui._base;

import com.zerowater.environment.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YoungSoo Kim on 2019-10-16.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 RecyclerView.Adapter
 */
public abstract class BaseViewModelAdapter<T extends Object, VM extends BaseViewModel, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    /**
     * 데이터 리스트
     */
    private final List<T> mData = new ArrayList<>();
    /**
     * ViewModel
     */
    private VM mViewModel;

    /**
     * 기본 상속형 RecyclerView.Adapter
     *
     * @param viewModel ViewModel
     */
    public BaseViewModelAdapter(VM viewModel) {
        mViewModel = viewModel;
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(VM viewModel) {
        mViewModel = viewModel;
    }

    public List<T> getData() {
        return mData;
    }

    private void setData(List<T> data) {
        mData.clear();
        if (!ListUtil.isEmpty(data)) {
            mData.addAll(data);
        }
    }

    /**
     * DiffUtil.Callback 화면 갱신
     *
     * @param data 새로운 데이터
     */
    public void dispatchUpdatesTo(List<T> data) {
        BaseDiffUtilCallback diffUtilCallback = new BaseDiffUtilCallback(mData, data);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        setData(data);
        diffResult.dispatchUpdatesTo(this);

    }

}
