package com.zerowater.environment.ui.splash;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.zerowater.environment.R;
import com.zerowater.environment.common.Const;
import com.zerowater.environment.data.model.VersionData;
import com.zerowater.environment.data.vo.AlertResource;
import com.zerowater.environment.databinding.FragmentSplashBinding;
import com.zerowater.environment.ui._base.BaseDaggerFragment;
import com.zerowater.environment.ui.alert.AlertDialog;
import com.zerowater.environment.util.DownloadHelper;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;


/**
 * Created by YoungSoo Kim on 2019-09-24.
 * Zero Ltd
 * byzerowater@gmail.com
 * SplashFragment
 */
public class SplashFragment extends BaseDaggerFragment<FragmentSplashBinding, SplashViewModel> implements EasyPermissions.PermissionCallbacks {

    /**
     * 다운로드
     */
    @Inject
    DownloadHelper mDownloadHelper;
    /**
     * SplashViewModel
     */
    private SplashViewModel mSplashViewModel;
    /**
     * 앱 버전 데이터
     */
    private VersionData mVersionData;

    @Override
    protected int getLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    protected Class getModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected void onActivityCreated(FragmentSplashBinding viewDataBinding, SplashViewModel viewModel) {

        mSplashViewModel = viewModel;

        viewModel.getNaviStatus().observe(this, naviStatus -> {

            NavController navController = NavHostFragment.findNavController(this);
            if (navController.getCurrentDestination().getId() == R.id.splashFragment) {
                switch (naviStatus) {
                    case LOGIN:
                        navController.navigate(SplashFragmentDirections.actionLogin());
                        break;
                    case MAIN:
                        navController.navigate(SplashFragmentDirections.actionMain());
                        break;
                }
            }

        });

        viewModel.getResponse().observe(this, versionDataResource -> {
            switch (versionDataResource.getStatus()) {
                case SUCCESS:
                    showUpdateDialog(versionDataResource.getData(), viewModel);
                    break;
            }
        });

        viewModel.login();

        Timber.i("onActivityCreated");

    }

    /**
     * 업데이트 다이얼로그 보여주기
     *
     * @param versionData 앱 버전 데이터
     * @param viewModel   SplashViewModel
     */
    private void showUpdateDialog(VersionData versionData, SplashViewModel viewModel) {
        if (!containsDialog(AlertResource.Type.UPDATE)) {
            AlertDialog alertDialog = new AlertDialog(
                    getActivity(),
                    new AlertResource(
                            AlertResource.Type.UPDATE,
                            versionData.getUdtMsg(),
                            getString(R.string.text_update),
                            Const.FLAG_FALSE.equals(versionData.getFrcUdtFg()) ?
                                    getString(R.string.text_late) : null),
                    new AlertDialog.OnAlertClickListener() {
                        @Override
                        public void onPositiveClick(Dialog dialog) {
                            removeDialog(AlertResource.Type.UPDATE);
                            mVersionData = versionData;
                            if (versionData != null) {
                                checkPermission();
                            }
                        }

                        @Override
                        public void onNegativeClick(Dialog dialog) {
                            removeDialog(AlertResource.Type.UPDATE);
                            mVersionData = null;
                            viewModel.checkLogin();
                        }
                    }
            );
            addDialog(AlertResource.Type.UPDATE, alertDialog);
            alertDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.i("onActivityResult requestCode %d", requestCode);
        if (mVersionData != null && mSplashViewModel != null) {
            switch (requestCode) {
                case Const.REQUEST_INSTALL:
                    if (Const.FLAG_TRUE.equals(mVersionData.getFrcUdtFg())) {
                        showUpdateDialog(mVersionData, mSplashViewModel);
                    } else {
                        mSplashViewModel.checkLogin();
                    }
                    break;
                case Const.REQUEST_SETTING_PERMISSION:
                    if (EasyPermissions.hasPermissions(getActivity(), Const.APP_PERMISSION)) {
                        mDownloadHelper.download(this, mVersionData.getAppUrl());
                    } else if (Const.FLAG_TRUE.equals(mVersionData.getFrcUdtFg())) {
                        showUpdateDialog(mVersionData, mSplashViewModel);
                    } else {
                        mSplashViewModel.checkLogin();
                    }
                    break;
            }
        }
    }

    /**
     * 권한 있는지 여부
     */
    @AfterPermissionGranted(Const.REQUEST_FILE_PERMISSION)
    private void checkPermission() {
        if (!EasyPermissions.hasPermissions(getActivity(), Const.APP_PERMISSION)) {
            requestPermissions(Const.APP_PERMISSION, Const.REQUEST_FILE_PERMISSION);
        } else {
            Timber.i("checkPermission");
            mDownloadHelper.download(this, mVersionData.getAppUrl());
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (!containsDialog(AlertResource.Type.PERMISSION)) {
                AlertDialog alertDialog = new AlertDialog(
                        getActivity(),
                        new AlertResource(
                                AlertResource.Type.PERMISSION,
                                getString(R.string.text_permission_guide),
                                getString(R.string.text_go_setting),
                                null),
                        new AlertDialog.OnAlertClickListener() {
                            @Override
                            public void onPositiveClick(Dialog dialog) {
                                removeDialog(AlertResource.Type.PERMISSION);
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Const.REQUEST_SETTING_PERMISSION);
                            }

                            @Override
                            public void onNegativeClick(Dialog dialog) {

                            }
                        });

                addDialog(AlertResource.Type.PERMISSION, alertDialog);
                alertDialog.show();
            }

        } else if (mVersionData != null) {
            if (Const.FLAG_TRUE.equals(mVersionData.getFrcUdtFg())) {
                showUpdateDialog(mVersionData, mSplashViewModel);
            } else if (mSplashViewModel != null) {
                mSplashViewModel.checkLogin();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
