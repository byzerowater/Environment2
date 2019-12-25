package com.zerowater.environment.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.zerowater.environment.util.ErrorMessageManager;
import com.zerowater.environment.util.ToastManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by YoungSoo Kim on 2019-10-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * UtilModule
 */
@Module
public class UtilModule {

    /**
     * 토스트 관리자 제공자
     *
     * @param context Context
     * @return 토스트 관리자
     */
    @Provides
    @Singleton
    static ToastManager provideToastManager(Context context) {
        return new ToastManager(context);
    }

    /**
     * 에러 메세지 관리자 제공자
     *
     * @param context Context
     * @param gson    Gson
     * @return 에러 메세지 관리자
     */
    @Provides
    @Singleton
    ErrorMessageManager provideErrorMessageManager(Context context, Gson gson) {
        return new ErrorMessageManager(context, gson);
    }
}
