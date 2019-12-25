package com.zerowater.environment.data.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by YoungSoo Kim on 2019-09-27.
 * Zero Ltd
 * byzerowater@gmail.com
 * 재시도 리소스
 */
public class RetryResource {
    /**
     * 알림정보 리소스
     */
    @NonNull
    private final AlertResource alertResource;
    /**
     * 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     */
    @Nullable
    private final PublishProcessor retryProcessor;
    /**
     * Throwable(에러)
     */
    @Nullable
    private final Throwable throwable;

    /**
     * 재시도 리소스
     *
     * @param alertResource  알림정보 리소스
     * @param retryProcessor 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     * @param mThrowable     Throwable(에러)
     */
    private RetryResource(@NonNull AlertResource alertResource, @Nullable PublishProcessor retryProcessor, @Nullable Throwable mThrowable) {
        this.alertResource = alertResource;
        this.retryProcessor = retryProcessor;
        this.throwable = mThrowable;
    }

    /**
     * 재시도
     *
     * @param alert          알림정보 리소스
     * @param retryProcessor 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     * @param err            Throwable(에러)
     * @return 재시도 리소스
     */
    public static RetryResource retry(@NonNull AlertResource alert, @Nullable PublishProcessor retryProcessor, @Nullable Throwable err) {
        return new RetryResource(alert, retryProcessor, err);
    }

    @NonNull
    public AlertResource getAlertResource() {
        return alertResource;
    }

    @Nullable
    public PublishProcessor getRetryProcessor() {
        return retryProcessor;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }
}
