package com.zerowater.environment;

import com.zerowater.environment.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-20.
 * Zero Ltd
 * byzerowater@gmail.com
 * ODSApplication
 */
public class EnvironmentApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent
                .factory()
                .create(this, BuildConfig.BASE_URL);
    }
}
