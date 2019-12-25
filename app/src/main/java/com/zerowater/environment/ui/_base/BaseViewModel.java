package com.zerowater.environment.ui._base;

import android.app.Dialog;

import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.vo.AlertResource;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.data.vo.RetryResource;
import com.zerowater.environment.util.DialogUtil;
import com.zerowater.environment.util.ErrorMessageManager;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-25.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 ViewModel
 */
public class BaseViewModel<T> extends ViewModel {
    /**
     * 데이터 관리자
     */
    private final DataManager mDataManager;
    /**
     * 에러 메세지 관리자
     */
    private final ErrorMessageManager mErrorMessageManager;
    /**
     * 통신 Observable 관리 CompositeDisposable
     */
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 다이얼로그 관리 맵
     * 화면에 표시되어있는 다이얼로그를 관리
     */
    private final HashMap<AlertResource.Type, Dialog> mDialogMap = new HashMap<>();
    /**
     * 통신 결과 LiveData
     */
    private MutableLiveData<Resource<T>> mResponse = new MutableLiveData<>();
    /**
     * 재시도 LiveData
     */
    private MutableLiveData<RetryResource> mRetryResponse = new MutableLiveData<>();

    /**
     * 기본 상속형 ViewModel
     *
     * @param dataManager         데이터 관리자
     * @param errorMessageManager 에러 메세지 관리자
     */
    public BaseViewModel(DataManager dataManager, ErrorMessageManager errorMessageManager) {
        mDataManager = dataManager;
        mErrorMessageManager = errorMessageManager;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public LiveData<Resource<T>> getResponse() {
        return mResponse;
    }

    protected void setResponse(Resource<T> resource) {
        mResponse.setValue(resource);
    }

    public LiveData<RetryResource> getRetryResponse() {
        return mRetryResponse;
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    protected ErrorMessageManager getErrorMessageManager() {
        return mErrorMessageManager;
    }

    /**
     * 재시도 알림 다이얼로그를 보여준다
     *
     * @param e Throwable
     * @return 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     */
    protected Flowable<Integer> showRetryDialog(Throwable e) {
        Timber.i("showRetryDialog Throwable");
        Timber.e(e);
        PublishProcessor<Integer> processor = PublishProcessor.create();
        AlertResource alertResource = mErrorMessageManager.getAlertResource(e);
        mRetryResponse.setValue(
                RetryResource.retry(
                        alertResource,
                        processor,
                        e
                ));

        return processor;
    }

    /**
     * 재시도 알림 다이얼로그를 보여준다
     *
     * @param alertResource 알림정보 리소스
     * @param e             Throwable
     * @return 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     */
    protected Flowable<Integer> showRetryDialog(AlertResource alertResource, Throwable e) {
        Timber.i("showRetryDialog Alert %s, Throwable ", alertResource.toString());
        Timber.e(e);
        PublishProcessor<Integer> processor = PublishProcessor.create();
        mRetryResponse.setValue(
                RetryResource.retry(
                        alertResource,
                        processor,
                        e
                ));

        return processor;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
        dismissDialog(mDialogMap);
    }

    /**
     * 다이얼로그가 그려져 있는지 여부
     *
     * @param type 확인할 알림 유형
     * @return 다이얼로그가 그려져 있는지 여부
     */
    protected boolean containsDialog(AlertResource.Type type) {
        return mDialogMap.containsKey(type);
    }

    /**
     * 다이얼로그를 관리맵에 추가
     *
     * @param type   관리맵에 추가 할 알림 유형
     * @param dialog 관리할 Dialog
     */
    protected void addDialog(AlertResource.Type type, Dialog dialog) {
        if (!containsDialog(type)) {
            mDialogMap.put(type, dialog);
        }
    }

    /**
     * 다이얼로그를 관리맵에 제거
     *
     * @param type 관리맵에서 제거 할 알림 유형
     */
    protected void removeDialog(AlertResource.Type type) {
        mDialogMap.remove(type);
    }

    /**
     * 화면 종료시 그려져있는 다이얼로그들을 종료
     *
     * @param dialogMap 그려져있는 다이얼로그 관리맵
     */
    private void dismissDialog(HashMap<AlertResource.Type, Dialog> dialogMap) {
        if (dialogMap != null) {
            for (Map.Entry<AlertResource.Type, Dialog> typeDialogEntry : dialogMap.entrySet()) {
                DialogUtil.dismissDialog(typeDialogEntry.getValue());
            }
        }
    }

    /**
     * 로그인 계정 정보 삭제
     */
    public void clearAccount() {
        mDataManager.clearAccount();
    }


}
