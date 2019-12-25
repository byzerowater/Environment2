package com.zerowater.environment.di.module;

import com.zerowater.environment.di.scope.PerFragment;
import com.zerowater.environment.ui.login.LoginFragment;
import com.zerowater.environment.ui.main.MainFragment;
import com.zerowater.environment.ui.main.board.BoardFragment;
import com.zerowater.environment.ui.main.history.HistoryFragment;
import com.zerowater.environment.ui.splash.SplashFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by YoungSoo Kim on 2019-09-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * FragmentBuildersModule
 */
@Module
public abstract class FragmentBuildersModule {

    /**
     * SplashFragment Injector
     *
     * @return SplashFragment
     */
    @PerFragment
    @ContributesAndroidInjector(modules = DownloadModule.class)
    abstract SplashFragment contributeSplashFragment();

    /**
     * LoginFragment Injector
     *
     * @return LoginFragment
     */
    @PerFragment
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    /**
     * MainFragment Injector
     *
     * @return MainFragment
     */
    @PerFragment
    @ContributesAndroidInjector(modules = DownloadModule.class)
    abstract MainFragment contributeMainFragment();

    /**
     * BoardFragment Injector
     *
     * @return BoardFragment
     */
    @PerFragment
    @ContributesAndroidInjector
    abstract BoardFragment contributeBoardFragment();

    /**
     * HistoryFragment Injector
     *
     * @return HistoryFragment
     */
    @PerFragment
    @ContributesAndroidInjector
    abstract HistoryFragment contributeHistoryFragment();

}
