package com.zerowater.environment.di.component;

import android.app.Application;

import com.zerowater.environment.EnvironmentApplication;
import com.zerowater.environment.di.module.ApplicationModule;
import com.zerowater.environment.di.module.CacheModule;
import com.zerowater.environment.di.module.ContextBuildersModule;
import com.zerowater.environment.di.module.RemoteModule;
import com.zerowater.environment.di.module.UtilModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by YoungSoo Kim on 2019-09-20.
 * Zero Ltd
 * byzerowater@gmail.com
 * ApplicationComponent
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        CacheModule.class,
        RemoteModule.class,
        UtilModule.class,
        ContextBuildersModule.class,
        AndroidInjectionModule.class
})
public interface ApplicationComponent extends AndroidInjector<EnvironmentApplication> {

    @Component.Factory
    interface Factory {

        ApplicationComponent create(@BindsInstance
                                            Application application,
                                    @BindsInstance
                                    @Named("baseUrl") String baseUrl);
    }


}

