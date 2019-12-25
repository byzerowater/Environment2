package com.zerowater.environment.di.module;

import android.content.Context;

import com.zerowater.environment.util.DownloadHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by YoungSoo Kim on 2019-09-27.
 * Zero Ltd
 * byzerowater@gmail.com
 * DownloadModule
 */
@Module
public class DownloadModule {

    /**
     * DownloadHelper 제공자
     *
     * @param context Context
     * @return DownloadHelper
     */
    @Provides
    DownloadHelper provideDownloadHelper(Context context) {
        return new DownloadHelper(context);
    }
}
