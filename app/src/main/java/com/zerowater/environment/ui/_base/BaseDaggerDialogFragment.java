package com.zerowater.environment.ui._base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zerowater.environment.NavGraphDirections;
import com.zerowater.environment.R;
import com.zerowater.environment.data.vo.AlertResource;
import com.zerowater.environment.ui.alert.AlertDialog;
import com.zerowater.environment.util.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import dagger.android.support.DaggerDialogFragment;
import io.reactivex.processors.PublishProcessor;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-09-25.
 * Zero Ltd
 * byzerowater@gmail.com
 * 기본 상속형 DaggerDialogFragment
 */
public abstract class BaseDaggerDialogFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends DaggerDialogFragment {

    /**
     * 와이파이 설정 코드
     */
    private static final int REQUEST_WIFI_SETTINGS = 0x25;
    /**
     * 다이얼로그 관리 맵
     * 화면에 표시되어있는 다이얼로그를 관리
     */
    private final HashMap<AlertResource.Type, Dialog> mDialogMap = new HashMap<>();
    /**
     * ViewModel 관리
     */
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    /**
     * ViewDataBinding
     */
    private VDB mViewDataBinding;
    /**
     * 알림 눌러졌을때 전달 PublishProcessor(재시도, 닫기)
     */
    private PublishProcessor mSettingProcessor;

    /**
     * 화면을 구성할 레이아웃 리소스를 가져온다
     *
     * @return 화면을 구성할 레이아웃 리소스
     */
    protected abstract int getLayout();

    /**
     * ViewModel을 가져온다
     *
     * @return ViewModel
     */
    protected abstract Class<VM> getModelClass();

    /**
     * 화면 구성이 끝나면 호출되어진다
     *
     * @param viewDataBinding ViewDataBinding
     * @param viewModel       ViewModel
     */
    protected abstract void onActivityCreated(VDB viewDataBinding, VM viewModel);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewDataBinding.setLifecycleOwner(this);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
        VM viewModel = ViewModelProviders.of(this, mViewModelFactory).get(getModelClass());
        retryWhen(viewModel);
        onActivityCreated(mViewDataBinding, viewModel);
    }

    /**
     * 재시도 알림 다이얼로그를 보여준다
     *
     * @param baseViewModel BaseViewModel
     */
    private void retryWhen(BaseViewModel<VM> baseViewModel) {
        baseViewModel.getRetryResponse().observe(this, retryResource -> {
            PublishProcessor retryProcessor = retryResource.getRetryProcessor();
            AlertResource alertResource = retryResource.getAlertResource();

            if (!containsDialog(alertResource.getType())) {

                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.splashFragment
                        && !AlertResource.Type.NO_NETWORK_CONNECTED.equals(alertResource.getType())) {
                    alertResource.setNegative(null);
                }

                Timber.i("showRetryDialog retryWhen %s", alertResource.toString());
                AlertDialog alertDialog = new AlertDialog(
                        getActivity(),
                        alertResource,
                        new AlertDialog.OnAlertClickListener() {
                            @Override
                            public void onPositiveClick(Dialog dialog) {
                                removeDialog(alertResource.getType());
                                retryProcessor.onNext(1);
                            }

                            @Override
                            public void onNegativeClick(Dialog dialog) {
                                AlertResource.Type type = alertResource.getType();
                                removeDialog(type);
                                retryResource.getRetryProcessor();
                                switch (type) {
                                    case NO_NETWORK_CONNECTED:
                                        mSettingProcessor = retryProcessor;
                                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), REQUEST_WIFI_SETTINGS);
                                        break;
                                    case UNAUTHORIZED:
                                        baseViewModel.clearAccount();
                                        retryProcessor.onError(retryResource.getThrowable());
                                        NavHostFragment.findNavController(BaseDaggerDialogFragment.this).navigate(NavGraphDirections.actionSplash());
                                        break;
                                    case SERVER:
                                    case APP:
                                        retryProcessor.onError(retryResource.getThrowable());
                                        break;
                                }
                            }
                        });

                addDialog(alertResource.getType(), alertDialog);
                alertDialog.show();
            } else {
                retryProcessor.onError(retryResource.getThrowable());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_WIFI_SETTINGS:
                if (mSettingProcessor != null) {
                    mSettingProcessor.onNext(1);
                    mSettingProcessor = null;
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

}
