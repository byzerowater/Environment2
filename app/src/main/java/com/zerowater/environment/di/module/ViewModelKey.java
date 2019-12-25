package com.zerowater.environment.di.module;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

/**
 * Created by YoungSoo Kim on 2019-09-24.
 * Zero Ltd
 * byzerowater@gmail.com
 * ViewModelKey
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
