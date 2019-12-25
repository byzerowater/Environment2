package com.zerowater.environment.data;

import com.zerowater.environment.BuildConfig;
import com.zerowater.environment.common.Const;
import com.zerowater.environment.data.local.PreferencesHelper;
import com.zerowater.environment.data.model.VersionData;
import com.zerowater.environment.data.remote.NetworkService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by YoungSoo Kim on 2019-10-23.
 * Zero Ltd
 * byzerowater@gmail.com
 * 데이터 관리자
 */
public class DataManager {

    /**
     * Retrofit 인터페이스 정의
     */
    private final NetworkService mNetworkService;
    /**
     * PreferencesHelper 접근 및 관리
     */
    private final PreferencesHelper mPreferencesHelper;

    /**
     * 데이터 관리자
     *
     * @param networkService    Retrofit 인터페이스 정의
     * @param preferencesHelper PreferencesHelper 접근 및 관리
     */
    public DataManager(NetworkService networkService, PreferencesHelper preferencesHelper) {
        mNetworkService = networkService;
        mPreferencesHelper = preferencesHelper;
    }

    /**
     * 앱 버전
     *
     * @return 앱 버전 응답 데이터
     */
    public Single<VersionData> getVersion() {
        return Single.just(new VersionData(BuildConfig.DEFAULT_DOWNLOAD_URL, BuildConfig.VERSION_NAME, Const.FLAG_N, ""));
    }

    /**
     * 로그인
     *
     * @return 로그인 응답 데이터
     */
    public Maybe<Void> login() {
        return Maybe.empty();
    }

    /**
     * 리스트 정보 조회
     *
     * @return 리스트 정보 응답 데이터
     */
    public Single<List<String>> getList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");
        list.add("eight");
        list.add("nine");
        list.add("ten");
        list.add("january");
        list.add("february");
        list.add("march");
        list.add("april");
        list.add("may");
        list.add("june");
        list.add("july");
        list.add("august");
        list.add("september");
        list.add("october");
        list.add("november");
        list.add("december");
        return Single.just(list);
    }

    /**
     * 앱 아이디 저장
     *
     * @param appId 앱 아이디
     */
    public void putAppId(String appId) {
        mPreferencesHelper.putAppId(appId);
    }

    /**
     * 앱 아이디 가져오기
     *
     * @return 앱 아이디
     */
    @Nullable
    public String getAppId() {
        return mPreferencesHelper.getAppId();
    }

    /**
     * 클라이언트 아이디 저장
     *
     * @param clientId 클라이언트 아이디
     */
    public void putClientId(String clientId) {
        mPreferencesHelper.putClientId(clientId);
    }

    /**
     * 클라이언트 아이디 가져오기
     *
     * @return 클라이언트 아이디
     */
    @Nullable
    public String getClientId() {
        return mPreferencesHelper.getClientId();
    }

    /**
     * 서버 접근 토큰 저장
     *
     * @param authToken 접근 토큰
     */
    public void putAuthToken(String authToken) {
        mPreferencesHelper.putAuthToken(authToken);
    }

    /**
     * 서버 접근 토큰 가져오기
     *
     * @return 접근 토큰
     */
    @Nullable
    public String getAuthToken() {
        return mPreferencesHelper.getAuthToken();
    }

    /**
     * 계정 저장
     *
     * @param account 계정
     */
    public void putAccount(String account) {
        mPreferencesHelper.putAccount(account);
    }

    /**
     * 계정 저장 가져오기
     *
     * @return 계정
     */
    @Nullable
    public String getAccount() {
        return mPreferencesHelper.getAccount();
    }

    /**
     * 로그인 계정 정보 삭제
     */
    public void clearAccount() {
        mPreferencesHelper.putAccount(null);
        mPreferencesHelper.putAuthToken(null);
    }
}
