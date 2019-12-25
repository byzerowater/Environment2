package com.zerowater.environment.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.zerowater.environment.BuildConfig;
import com.zerowater.environment.R;
import com.zerowater.environment.common.Const;

import java.io.File;
import java.util.List;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2015-07-16.
 * Zero Ltd
 * byzerowater@gmail.com
 * 다운로드
 */
public class DownloadHelper {

    /**
     * MIME_TYPE
     */
    private static final String MIME_TYPE = "application/vnd.android.package-archive";

    /**
     * Context
     */
    private Context mContext = null;
    /**
     * 다운로드를 관리해주는 Android Util
     */
    private DownloadManager mDownloadManager = null;
    /**
     * 다운로드할 요청을 설정하는 Android Request
     */
    private DownloadManager.Request mRequest = null;
    /**
     * 다운로드 상태를 알려주는 Android Query
     */
    private DownloadManager.Query mQuery = null;

    /**
     * 다운 받을 url
     */
    private Uri mUrlToDownload = null;
    /**
     * DownloadManager를 사용하여 다운 받을 경우 아이디
     */
    private long mDownloadId = -1L;
    /**
     * 설치 또는 취소시 이벤트를 받을 Fragment
     */
    private Fragment mFragment;
    /**
     * 다운로드 완료 후 처리
     */
    private BroadcastReceiver mCompleteReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                String action = intent.getAction();
                if (!StringUtil.isEmpty(action)) {

                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        long downloadId = intent.getLongExtra(
                                DownloadManager.EXTRA_DOWNLOAD_ID, mDownloadId);
                        mQuery = new DownloadManager.Query();
                        mQuery.setFilterById(downloadId);

                        if (mDownloadManager != null) {
                            Cursor cursor = mDownloadManager.query(mQuery);
                            String localUri = null;

                            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                                localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            }

                            startDownIntent(localUri);
                        }
                    }
                }
            }
            unregisterReceiver();
        }
    };

    /**
     * 다운로드
     *
     * @param context Context
     */
    public DownloadHelper(Context context) {
        this.mContext = context;
        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 다운로드
     *
     * @param fragment 설치 또는 취소시 이벤트를 받을 Fragment
     * @param url      다운로드 경로
     */
    public void download(Fragment fragment, String url) {
        Timber.i("download %s", url);
        mFragment = fragment;
        url = StringUtil.isEmptyReplace(url, BuildConfig.DEFAULT_DOWNLOAD_URL);
        registerReceiver();
        mUrlToDownload = Uri.parse(url);
        List<String> pathSegments = mUrlToDownload.getPathSegments();
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        for (String pathSegment : pathSegments) {
            Timber.i("pathSegment %s", pathSegment);
        }

        mRequest = new DownloadManager.Request(mUrlToDownload)
                .setTitle(mContext.getString(R.string.app_name))
                .setDescription(mContext.getString(R.string.text_update))
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pathSegments.get(pathSegments.size() - 1))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setMimeType(MIME_TYPE)
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network

        if (mDownloadManager != null) {
            mDownloadId = mDownloadManager.enqueue(mRequest);
        }
    }

    /**
     * 다운 완료시 받는 리시버 등록
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mContext.registerReceiver(mCompleteReceiver, intentFilter);
    }

    /**
     * 다운 완료시 받는 리시버 제거
     */
    private void unregisterReceiver() {
        mContext.unregisterReceiver(mCompleteReceiver);
    }


    /**
     * 다운로드 후 앱 설치
     * 앱 설치시 에러가 나면 다운로드 항목을 보여준다
     *
     * @param path
     */
    private void startDownIntent(String path) {
        if (StringUtil.isEmpty(path)) {
            Intent downloadViewIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            downloadViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(downloadViewIntent, Const.REQUEST_INSTALL);
        } else {
            try {
                String extension = MimeTypeMap.getFileExtensionFromUrl(path);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

                if (StringUtil.isEmpty(mimeType)) {
                    mimeType = MIME_TYPE;
                }

                Uri uriParser = Uri.parse(path);
                File downloadedFile = new File(uriParser.getPath());

                Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", downloadedFile);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, mimeType);
                installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(installIntent, Const.REQUEST_INSTALL);

            } catch (Exception e) {
                Intent downloadViewIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                downloadViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(downloadViewIntent, Const.REQUEST_INSTALL);
                e.printStackTrace();
            }
        }
    }

    /**
     * 설치 또는 취소시 이벤트 전달
     *
     * @param intent      Intent
     * @param requestCode 요청 코드
     */
    private void startActivityForResult(Intent intent, int requestCode) {
        if (mFragment == null) {
            mContext.startActivity(intent);
        } else {
            mFragment.startActivityForResult(intent, requestCode);
        }
    }

}
