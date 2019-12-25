package com.zerowater.environment.data.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by YoungSoo Kim on 2019-09-27.
 * Zero Ltd
 * byzerowater@gmail.com
 * 통신 리소스
 */
public class Resource<T> {
    /**
     * 상태
     */
    @NonNull
    private final Status status;

    /**
     * 데이터
     */
    @Nullable
    private final T data;

    /**
     * 통신 리소스
     *
     * @param status 상태
     * @param data   데이터
     */
    public Resource(@NonNull Status status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 통신 성공
     *
     * @param data 데이터
     * @param <T>  데이터 유형
     * @return 성공 통신 리소스
     */
    @NonNull
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data);
    }

    /**
     * 통신 실패
     *
     * @param data 데이터
     * @param <T>  데이터 유형
     * @return 실패 통신 리소스
     */
    @NonNull
    public static <T> Resource<T> error(@Nullable T data) {
        return new Resource<>(Status.ERROR, data);
    }

    /**
     * 통신중
     *
     * @param data 데이터
     * @param <T>  데이터 유형
     * @return 통신중 리소스
     */
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    /**
     * 통신 상태
     */
    public enum Status {
        /**
         * 성공
         */
        SUCCESS,
        /**
         * 실패
         */
        ERROR,
        /**
         * 로딩중
         */
        LOADING
    }
}
