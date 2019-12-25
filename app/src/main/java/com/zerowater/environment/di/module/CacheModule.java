package com.zerowater.environment.di.module;

import android.content.Context;

import com.zerowater.environment.data.local.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by YoungSoo Kim on 2019-09-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * CacheModule
 */
@Module
public class CacheModule {

    /**
     * PreferencesHelper 접근 및 관리 제공자
     *
     * @param context Context
     * @return PreferencesHelper 접근 및 관리
     */
    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }
}
