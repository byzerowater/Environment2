package com.zerowater.environment.data.model.base;


import androidx.annotation.Nullable;

/**
 * Created by YoungSoo Kim on 2019-10-17.
 * Zero Ltd
 * byzerowater@gmail.com
 * BaseDiff
 */
public abstract class BaseDiff {

    /**
     * 아이디 비교
     *
     * @param data 비교할 데이터
     * @return true 동일, false 동일하지 않음
     */
    public abstract boolean equalsId(Object data);

    /**
     * 내용 비교
     *
     * @param data 비교할 데이터
     * @return true 동일, false 동일하지 않음
     */
    public abstract boolean equalsContents(Object data);

    /**
     * 기본 비교는 equalsContents 를 사용
     *
     * @param obj 비교할 데이터
     * @return true 동일, false 동일하지 않음
     * @see #equalsContents(Object)
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        return equalsContents(obj);
    }
}
