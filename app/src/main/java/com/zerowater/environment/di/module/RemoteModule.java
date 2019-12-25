package com.zerowater.environment.di.module;

import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.local.PreferencesHelper;
import com.zerowater.environment.data.remote.NetworkService;
import com.zerowater.environment.fcm.FCMLiveDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by YoungSoo Kim on 2019-09-20.
 * Zero Ltd
 * byzerowater@gmail.com
 * RemoteModule
 */
@Module(includes = {ApiModule.class})
public class RemoteModule {

    /**
     * Retrofit 인터페이스 정의 제공자
     *
     * @param retrofit Retrofit
     * @return Retrofit 인터페이스 정의
     */
    @Provides
    @Singleton
    NetworkService provideNetworkService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    /**
     * 푸시 데이터 전달 제공자
     *
     * @return 푸시 데이터 전달
     */
    @Provides
    @Singleton
    FCMLiveDataRepository provideFCMLiveDataRepository() {
        return new FCMLiveDataRepository();
    }

    /**
     * 데이터 관리자 제공자
     *
     * @param networkService    Retrofit 인터페이스 정의
     * @param preferencesHelper PreferencesHelper 접근 및 관리
     * @return 데이터 관리자
     */
    @Provides
    @Singleton
    DataManager provideDataManager(NetworkService networkService, PreferencesHelper preferencesHelper) {
        return new DataManager(networkService, preferencesHelper);
    }
}
