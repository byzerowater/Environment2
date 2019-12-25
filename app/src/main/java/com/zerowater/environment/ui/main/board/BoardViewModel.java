package com.zerowater.environment.ui.main.board;

import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.ui._base.BaseViewModel;
import com.zerowater.environment.util.ErrorMessageManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-10-14.
 * Zero Ltd
 * byzerowater@gmail.com
 * BoardViewModel
 */
public class BoardViewModel extends BaseViewModel<List<String>> {

    /**
     * BoardViewModel
     *
     * @param dataManager         데이터 관리자
     * @param errorMessageManager 에러 메세지 관리자
     */
    @Inject
    public BoardViewModel(DataManager dataManager, ErrorMessageManager errorMessageManager) {
        super(dataManager, errorMessageManager);
    }


    /**
     * 리스트 정보 가져오기
     */
    public void getList() {
        setResponse(Resource.loading(null));
        getDataManager().getList()
                .subscribeOn(Schedulers.io())
                .retryWhen(err ->
                        err.observeOn(AndroidSchedulers.mainThread())
                                .flatMap(e -> showRetryDialog(e))
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<String> list) {
                        setResponse(Resource.success(list));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        setResponse(Resource.error(null));
                    }
                });
    }


}
