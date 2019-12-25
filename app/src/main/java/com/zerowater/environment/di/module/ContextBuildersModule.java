package com.zerowater.environment.di.module;

import com.zerowater.environment.di.scope.PerActivity;
import com.zerowater.environment.di.scope.PerService;
import com.zerowater.environment.fcm.FirebaseReceivedService;
import com.zerowater.environment.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by YoungSoo Kim on 2019-09-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * ContextBuildersModule
 */
@Module
public abstract class ContextBuildersModule {

    /**
     * MainActivity Injector
     *
     * @return MainActivity
     */
    @PerActivity
    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();

    /**
     * FirebaseReceivedService Injector
     *
     * @return FirebaseReceivedService
     */
    @PerService
    @ContributesAndroidInjector
    abstract FirebaseReceivedService contributeFirebaseReceivedService();
}
