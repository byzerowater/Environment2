package com.zerowater.environment.ui.splash;

import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.model.VersionData;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.ui._base.BaseViewModel;
import com.zerowater.environment.util.ErrorMessageManager;
import com.zerowater.environment.util.StringUtil;
import com.zerowater.environment.util.VersionUtil;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-24.
 * Zero Ltd
 * byzerowater@gmail.com
 * SplashViewModel
 */
public class SplashViewModel extends BaseViewModel<VersionData> {

    /**
     * 경로 이동 LiveData
     */
    private MutableLiveData<NaviStatus> mNaviStatus = new MutableLiveData<>();

    /**
     * SplashViewModel
     *
     * @param dataManager         데이터 관리자
     * @param errorMessageManager 에러 메세지 관리자
     */
    @Inject
    public SplashViewModel(DataManager dataManager, ErrorMessageManager errorMessageManager) {
        super(dataManager, errorMessageManager);
    }


    public LiveData<NaviStatus> getNaviStatus() {
        return mNaviStatus;
    }

    /**
     * 로그인
     */
    public void login() {

        getDataManager().getVersion()
                .subscribeOn(Schedulers.io())
                .retryWhen(err ->
                        err.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(e -> showRetryDialog(e))
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((versionData, throwable) -> {
                    Timber.e(throwable);
                    if (VersionUtil.isUpdateApk(versionData)) {
                        setResponse(Resource.success(versionData));
                    } else {
                        checkLogin();
                    }
                });
    }

    /**
     * 로그인 여부 확인 후 화면 이동
     */
    public void checkLogin() {
        if (StringUtil.isEmpty(getDataManager().getAuthToken())) {
            mNaviStatus.setValue(NaviStatus.LOGIN);
        } else {
            mNaviStatus.setValue(NaviStatus.MAIN);
        }
    }

    /**
     * 경로 이동
     */
    public enum NaviStatus {
        /**
         * 로그인
         */
        LOGIN,
        /**
         * 메인
         */
        MAIN
    }

}
