package com.zerowater.environment.ui._base;

import com.zerowater.environment.data.model.base.BaseDiff;
import com.zerowater.environment.util.ListUtil;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by YoungSoo Kim on 2019-10-17.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 DiffUtil.Callback
 */
public class BaseDiffUtilCallback<T extends List<? extends BaseDiff>> extends DiffUtil.Callback {
    /**
     * 기존 데이터들
     */
    private final T oldList;
    /**
     * 새로운 데이터들
     */
    private final T newList;

    /**
     * 기본 상속형 DiffUtil.Callback
     *
     * @param oldList 기존 데이터들
     * @param newList 새로운 데이터들
     */
    public BaseDiffUtilCallback(T oldList, T newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return ListUtil.getListCount(oldList);
    }

    @Override
    public int getNewListSize() {
        return ListUtil.getListCount(newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equalsId(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equalsContents(newList.get(newItemPosition));
    }
}
