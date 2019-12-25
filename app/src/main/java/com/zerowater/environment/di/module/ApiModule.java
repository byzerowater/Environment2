package com.zerowater.environment.di.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zerowater.environment.BuildConfig;
import com.zerowater.environment.R;
import com.zerowater.environment.data.local.PreferencesHelper;
import com.zerowater.environment.util.EmptyCheckTypeAdapterFactory;
import com.zerowater.environment.util.NoConnectivityException;
import com.zerowater.environment.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-20.
 * Zero Ltd
 * byzerowater@gmail.com
 * ApiModule
 */
@Module
public class ApiModule {

    /**
     * OkHttpClient 제공자
     *
     * @param httpLoggingInterceptor  로그 Interceptor
     * @param headerInterceptor       헤더 Interceptor
     * @param networkCheckInterceptor 통신 연결 확인 Interceptor
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(@Named("httpLoggingInterceptor") Interceptor httpLoggingInterceptor,
                                            @Named("headerInterceptor") Interceptor headerInterceptor,
                                            @Named("networkCheckInterceptor") Interceptor networkCheckInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(networkCheckInterceptor)
                .build();
    }

    /**
     * Retrofit 제공자
     *
     * @param gson         Gson
     * @param okHttpClient OkHttpClient
     * @param baseUrl      서버 URL
     * @return Retrofit
     */
    @Provides
    @Singleton
    static Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient, @Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    /**
     * Gson 제공자
     *
     * @return Gson
     */
    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(
                        new EmptyCheckTypeAdapterFactory())
                .setLenient()
                .create();
    }

    /**
     * 헤더 Interceptor 제공자
     *
     * @param context           Constext
     * @param preferencesHelper PreferencesHelper 접근 및 관리
     * @return 헤더 Interceptor
     */
    @Provides
    @Singleton
    @Named("headerInterceptor")
    static Interceptor provideHeaderInterceptor(Context context, PreferencesHelper preferencesHelper) {
        return chain -> {
            Request original = chain.request();
            Headers headers = original.headers();

            if (headers.size() == 0) {
                Request.Builder builder = original.newBuilder();
                builder.header("Content-Type", "application/json")
                        .header("User-Agent", "Android Tablet")
                        .header("DEVICE-MODEL", Build.DEVICE)
                        .header("OS", "A")
                        .header("OS-VER", Build.VERSION.RELEASE)
                        .header("MARKET", Build.BRAND)
                        .method(original.method(), original.body());

                RequestBody body = original.body();

                Timber.i("body check %s", body);

                String appId = preferencesHelper.getAppId();
                String clientId = preferencesHelper.getClientId();
                String authorization = preferencesHelper.getAuthToken();

                if (StringUtil.isEmpty(appId)) {
                    final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    appId = UUID.nameUUIDFromBytes(androidId.getBytes(StandardCharsets.UTF_8)).toString();
                    preferencesHelper.putAppId(appId);
                }

                if (!StringUtil.isEmpty(appId)) {
                    builder.header("APP-ID", appId);
                }

                if (!StringUtil.isEmpty(clientId)) {
                    builder.header("CLIENT-ID", clientId);
                }

                if (!StringUtil.isEmpty(authorization)) {
                    builder.header("Authorization", authorization);
                }

                Timber.d("appId %s, clientId %s, authorization %s", appId, clientId, authorization);

                Request request = builder.build();
                return chain.proceed(request);
            }
            return chain.proceed(original);
        };

    }

    /**
     * 통신 연결 확인 Interceptor 제공자
     *
     * @param context Context
     * @return 통신 연결 확인 Interceptor
     */
    @Provides
    @Singleton
    @Named("networkCheckInterceptor")
    static Interceptor provideNetworkCheckInterceptor(Context context) {
        return chain -> {
            Request original = chain.request();
            if (isConnected(context)) {
                return chain.proceed(original);
            } else {
                throw new NoConnectivityException(context.getString(R.string.text_network_setting_error));
            }
        };

    }

    /**
     * 로그 Interceptor 제공자
     *
     * @return 로그 Interceptor
     */
    @Provides
    @Singleton
    @Named("httpLoggingInterceptor")
    static Interceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;

    }

    /**
     * 통신 연결 여부 가져오기
     *
     * @param context Context
     * @return 통신 연결 여부
     */
    static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


}
