package com.zerowater.environment.util;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * Created by YoungSoo Kim on 2019-10-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * 단일건 처리 MutableLiveData
 */
public class SingleMutableLiveData<T> extends MutableLiveData<T> {

    /**
     * 푸시 보냈는지 여부
     */
    private AtomicBoolean mPending = new AtomicBoolean(false);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @Override
    public void setValue(T value) {
        mPending.set(true);
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        mPending.set(true);
        super.postValue(value);
    }
}
