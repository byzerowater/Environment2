package com.zerowater.environment.ui.login;

import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.zerowater.environment.R;
import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.ui._base.BaseViewModel;
import com.zerowater.environment.util.ErrorMessageManager;
import com.zerowater.environment.util.StringUtil;
import com.zerowater.environment.util.Util;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-25.
 * Zero ltd
 * byzerowater@gmail.com
 * LoginViewModel
 */
public class LoginViewModel extends BaseViewModel<Void> {

    /**
     * 로그인 아이디 LiveData
     */
    private MutableLiveData<String> mLoginId = new MutableLiveData<>();
    /**
     * 로그인 버튼 활성 상태 LiveData
     */
    private MediatorLiveData<Boolean> mLoginEnabled = new MediatorLiveData<>();
    /**
     * 키보드 열림 상태 LiveData
     */
    private MutableLiveData<Boolean> mOpenKeyboard = new MutableLiveData<>(false);

    @Inject
    public LoginViewModel(DataManager dataManager, ErrorMessageManager errorMessageManager) {
        super(dataManager, errorMessageManager);
        mLoginEnabled.addSource(mLoginId, s -> mLoginEnabled.setValue(!StringUtil.isEmpty(s)));
    }

    /**
     * 로그인
     *
     * @param view View
     */
    public void login(View view) {

        setResponse(Resource.loading(null));

        String id = mLoginId.getValue();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.e(task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    getDataManager().login()
                            .doOnSuccess(authTokenData -> getDataManager().putAccount(id))
                            .subscribeOn(Schedulers.io())
                            .retryWhen(err ->
                                    err.observeOn(AndroidSchedulers.mainThread())
                                            .flatMap(e -> showRetryDialog(e))
                            )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MaybeObserver<Void>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    addDisposable(d);
                                }

                                @Override
                                public void onSuccess(Void aVoid) {
                                    setResponse(Resource.success(aVoid));
                                    NavController navController = Navigation.findNavController(view);
                                    Util.hideSoftInputFromWindow(view);
                                    if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                                        navController.navigate(LoginFragmentDirections.actionMain());
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Timber.e(e);
                                    setResponse(Resource.error(null));
                                }

                                @Override
                                public void onComplete() {
                                    setResponse(Resource.success(null));
                                    NavController navController = Navigation.findNavController(view);
                                    Util.hideSoftInputFromWindow(view);
                                    if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                                        navController.navigate(LoginFragmentDirections.actionMain());
                                    }
                                }
                            });
                });
    }

    public MutableLiveData<String> getLoginId() {
        return mLoginId;
    }

    public LiveData<Boolean> getLoginEnabled() {
        return mLoginEnabled;
    }

    public LiveData<Boolean> getOpenKeyboard() {
        return mOpenKeyboard;
    }

    public void setOpenKeyboard(boolean isOpen) {
        mOpenKeyboard.setValue(isOpen);
    }
}
