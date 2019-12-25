package com.zerowater.environment.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.zerowater.environment.R;
import com.zerowater.environment.common.Const;
import com.zerowater.environment.data.model.VersionData;
import com.zerowater.environment.data.vo.AlertResource;
import com.zerowater.environment.data.vo.Resource;
import com.zerowater.environment.databinding.FragmentMainBinding;
import com.zerowater.environment.fcm.FCMLiveDataRepository;
import com.zerowater.environment.ui._base.BaseDaggerFragment;
import com.zerowater.environment.ui.alert.AlertDialog;
import com.zerowater.environment.ui.main.board.BoardFragment;
import com.zerowater.environment.ui.main.history.HistoryFragment;
import com.zerowater.environment.util.DownloadHelper;
import com.zerowater.environment.util.VersionUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;


/**
 * Created by YoungSoo Kim on 2019-09-26.
 * Zero Ltd
 * byzerowater@gmail.com
 * MainFragment
 */
public class MainFragment extends BaseDaggerFragment<FragmentMainBinding, MainViewModel> implements EasyPermissions.PermissionCallbacks {

    /**
     * 푸시 데이터 전달
     */
    @Inject
    FCMLiveDataRepository mFCMLiveDataRepository;

    /**
     * 다운로드
     */
    @Inject
    DownloadHelper mDownloadHelper;
    /**
     * 앱 버전 데이터
     */
    private VersionData mVersionData;

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected Class getModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onActivityCreated(FragmentMainBinding viewDataBinding, MainViewModel viewModel) {
        Timber.i("onActivityCreated");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isIgnoringBatteryOptimizations(getActivity())) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
        }

        viewDataBinding.setViewModel(viewModel);
        viewDataBinding.setViewPagerAdapter(new MainPagerAdapter(
                getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                getResources().getStringArray(R.array.main_tab)));

        viewModel.getResponse().observe(this, versionDataResource -> {
            Resource.Status status = versionDataResource.getStatus();
            switch (status) {
                case SUCCESS:
                    VersionData versionData = versionDataResource.getData();
                    if (versionData != null) {
                        viewDataBinding.setVersionData(versionData);
                        showUpdateDialog(versionData);
                    }
                    break;
            }
        });

        mFCMLiveDataRepository.getPushResource().observe(this, pushResource -> {
            Timber.i(pushResource.toString());

            switch (pushResource.getPushType()) {
                case APP_UPDATE:
                    viewModel.getVersion();
                    break;
            }
        });

        viewModel.getVersion();
    }

    /**
     * 배터리 화이트 리스트 등록 여부
     *
     * @param context Context
     * @return 배터리 화이트 리스트 등록 여부
     */
    public boolean isIgnoringBatteryOptimizations(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isIgnoringBatteryOptimizations = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isIgnoringBatteryOptimizations = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }

        return isIgnoringBatteryOptimizations;
    }

    /**
     * 업데이트 다이얼로그 보여주기
     *
     * @param versionData 앱 버전 데이터
     */
    private void showUpdateDialog(VersionData versionData) {
        if (VersionUtil.isUpdateApk(versionData) && !containsDialog(AlertResource.Type.UPDATE)) {
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
        if (mVersionData != null) {
            switch (requestCode) {
                case Const.REQUEST_INSTALL:
                    if (Const.FLAG_TRUE.equals(mVersionData.getFrcUdtFg())) {
                        showUpdateDialog(mVersionData);
                    }
                    break;
                case Const.REQUEST_SETTING_PERMISSION:
                    if (EasyPermissions.hasPermissions(getActivity(), Const.APP_PERMISSION)) {
                        mDownloadHelper.download(this, mVersionData.getAppUrl());
                    } else if (Const.FLAG_TRUE.equals(mVersionData.getFrcUdtFg())) {
                        showUpdateDialog(mVersionData);
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
                showUpdateDialog(mVersionData);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 메인 페이지 어답터
     */
    public static class MainPagerAdapter extends FragmentPagerAdapter {
        /**
         * 리스트
         */
        private static final int BOARD = 0;
        /**
         * 내역
         */
        private static final int HISTORY = 1;
        /**
         * 탭
         */
        private static final int[] TABS = new int[]{BOARD, HISTORY};
        /**
         * 탭 이름들
         */
        private final String[] TABS_TITLE;

        /**
         * 메인 페이지 어답터
         *
         * @param fm        FragmentManager
         * @param behavior  determines if only current fragments are in a resumed state
         * @param tabsTitle 탭 이름들
         */
        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior, String[] tabsTitle) {
            super(fm, behavior);
            TABS_TITLE = tabsTitle;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (TABS[position]) {
                case BOARD:
                    fragment = BoardFragment.newInstance();
                    break;
                case HISTORY:
                    fragment = HistoryFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TABS_TITLE[position];
        }
    }

}
