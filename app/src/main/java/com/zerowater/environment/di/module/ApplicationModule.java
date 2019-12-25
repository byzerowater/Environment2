package com.zerowater.environment.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by YoungSoo Kim on 2019-09-20.
 * Zero Ltd
 * byzerowater@gmail.com
 * ApplicationModule
 */
@Module(includes = ViewModelModule.class)
public abstract class ApplicationModule {

    /**
     * Context 제공자
     *
     * @param application Application
     * @return Context
     */
    @Binds
    abstract Context bindApplicationContext(Application application);

}