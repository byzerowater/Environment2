package com.zerowater.environment.ui.main;

import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.model.VersionData;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.ui._base.BaseViewModel;
import com.zerowater.environment.util.ErrorMessageManager;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-26.
 * Zero Ltd
 * byzerowater@gmail.com
 * MainViewModel
 */
public class MainViewModel extends BaseViewModel<VersionData> {

    /**
     * 메뉴 위치 - 리스트
     */
    private static final int MENU_POSITION_BOARD = 0;

    /**
     * 메뉴 위치 LiveData
     */
    private MutableLiveData<Integer> mCurrentPosition
            = new MutableLiveData<>(MENU_POSITION_BOARD);


    /**
     * MainViewModel
     *
     * @param dataManager         데이터 관리자
     * @param errorMessageManager 에러 메세지 관리자
     */
    @Inject
    public MainViewModel(DataManager dataManager, ErrorMessageManager errorMessageManager) {
        super(dataManager, errorMessageManager);
    }

    public MutableLiveData<Integer> getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * 앱 버전 가져오기
     */
    public void getVersion() {
        setResponse(Resource.loading(null));
        getDataManager().getVersion()
                .subscribeOn(Schedulers.io())
                .retryWhen(err ->
                        err.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(e -> showRetryDialog(e))
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<VersionData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(VersionData versionData) {
                        setResponse(Resource.success(versionData));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        setResponse(Resource.error(null));
                    }
                });

    }

}
