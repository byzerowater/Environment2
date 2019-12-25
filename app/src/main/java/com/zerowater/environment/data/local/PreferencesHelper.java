package com.zerowater.environment.data.local;

import android.content.Context;

import com.zerowater.environment.util.SharedPreferencesCache;
import com.zerowater.environment.util.StringUtil;

import androidx.annotation.Nullable;

/**
 * Created by YoungSoo Kim on 2017-02-23.
 * company Ltd
 * byzerowater@gmail.com
 * PreferencesHelper 접근 및 관리
 */
public class PreferencesHelper {
    /**
     * SharedPreferences 이름
     */
    public static final String PREF_FILE_NAME = "zero_app_pref_file";
    /**
     * 앱 아이디 키
     */
    private static final String PREF_KEY_APP_ID = "pref_key_app_id";
    /**
     * 클라이언트 아이디 키
     */
    private static final String PREF_KEY_CLIENT_ID = "pref_key_client_id";
    /**
     * 서버 접근 토큰 키
     */
    private static final String PREF_KEY_AUTH_TOKEN = "pref_key_auth_token";
    /**
     * 서버 계정 키
     */
    private static final String PREF_KEY_ACCOUNT = "pref_key_account";


    /**
     * SharedPreferencesCache
     */
    private final SharedPreferencesCache mPref;

    /**
     * 데이터 매니져 데이터 접근 및 관리
     *
     * @param context Context
     */
    public PreferencesHelper(Context context) {
        mPref = new SharedPreferencesCache(context, PREF_FILE_NAME);
    }

    /**
     * 앱 아이디 저장
     *
     * @param appId 앱 아이디
     */
    public void putAppId(String appId) {
        mPref.put(PREF_KEY_APP_ID, appId);
    }

    /**
     * 앱 아이디 가져오기
     *
     * @return 앱 아이디
     */
    @Nullable
    public String getAppId() {
        return mPref.getString(PREF_KEY_APP_ID);
    }

    /**
     * 클라이언트 아이디 저장
     *
     * @param clientId 클라이언트 아이디
     */
    public void putClientId(String clientId) {
        mPref.put(PREF_KEY_CLIENT_ID, clientId);
    }

    /**
     * 클라이언트 아이디 가져오기
     *
     * @return 클라이언트 아이디
     */
    @Nullable
    public String getClientId() {
        return mPref.getString(PREF_KEY_CLIENT_ID);
    }

    /**
     * 서버 접근 토큰 저장
     *
     * @param authToken 접근 토큰
     */
    public void putAuthToken(String authToken) {
        if (!StringUtil.isEmpty(authToken)) {
            mPref.put(PREF_KEY_AUTH_TOKEN, authToken);
        } else {
            mPref.remove(PREF_KEY_AUTH_TOKEN);
        }
    }

    /**
     * 서버 접근 토큰 가져오기
     *
     * @return 접근 토큰
     */
    @Nullable
    public String getAuthToken() {
        return mPref.getString(PREF_KEY_AUTH_TOKEN);
    }

    /**
     * 계정 저장
     *
     * @param account 계정
     */
    public void putAccount(String account) {
        if (!StringUtil.isEmpty(account)) {
            mPref.put(PREF_KEY_ACCOUNT, account);
        } else {
            mPref.remove(PREF_KEY_ACCOUNT);
        }
    }

    /**
     * 계정 저장 가져오기
     *
     * @return 계정
     */
    @Nullable
    public String getAccount() {
        return mPref.getString(PREF_KEY_ACCOUNT);
    }

    /**
     * SharedPreferences 전체 삭제
     */
    public void clear() {
        mPref.clearAll();
    }


}
