package com.zerowater.environment.di.module;

import com.zerowater.environment.ui.login.LoginViewModel;
import com.zerowater.environment.ui.main.MainViewModel;
import com.zerowater.environment.ui.main.board.BoardViewModel;
import com.zerowater.environment.ui.main.history.HistoryViewModel;
import com.zerowater.environment.ui.splash.SplashViewModel;
import com.zerowater.environment.viewmodel.ZeroViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by YoungSoo Kim on 2019-09-24.
 * Zero Ltd
 * byzerowater@gmail.com
 * ViewModelModule
 */
@Module
public abstract class ViewModelModule {

    /**
     * SplashViewModel 제공자
     *
     * @param splashViewModel SplashViewModel
     * @return SplashViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    /**
     * LoginViewModel 제공자
     *
     * @param loginViewModel LoginViewModel
     * @return LoginViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);


    /**
     * MainViewModel 제공자
     *
     * @param mainViewModel MainViewModel
     * @return MainViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    /**
     * BoardViewModel 제공자
     *
     * @param boardViewModel BoardViewModel
     * @return BoardViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(BoardViewModel.class)
    abstract ViewModel bindBoardViewModel(BoardViewModel boardViewModel);

    /**
     * HistoryViewModel 제공자
     *
     * @param historyViewModel HistoryViewModel
     * @return HistoryViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryPageViewModel(HistoryViewModel historyViewModel);

    /**
     * ViewModelFactory 제공자
     *
     * @param zeroViewModelFactory ViewModelFactory
     * @return ViewModelFactory
     */
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ZeroViewModelFactory zeroViewModelFactory);
}
