package com.zerowater.environment.util;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-09-15.
 * company Ltd
 * byzerowater@gmail.com
 * FileLoggingTree
 */
public class FileLoggingTree extends Timber.DebugTree {

    /**
     * TAG
     */
    private static final String TAG = FileLoggingTree.class.getSimpleName();
    /**
     * ODSLogs
     */
    private static final String DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ODSLogs";
    /**
     * Context
     */
    private Context mContext;

    /**
     * FileLoggingTree
     * 파일로 로그 남기기
     *
     * @param context Context
     */
    public FileLoggingTree(Context context) {
        mContext = context;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        super.log(priority, tag, message, t);

        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Completable.fromAction(() -> {
                try {

                    File directory = new File(DIRECTORY);

                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    String fileNameTimeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                    String logTimeStamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    String fileName = fileNameTimeStamp + ".txt";

                    File file = new File(directory, fileName);

                    if (!file.exists()) {
                        file.createNewFile();
                    } else {
                        OutputStream fileOutputStream = new FileOutputStream(file, true);

                        fileOutputStream.write((logTimeStamp + " : " + tag + " - " + message + "\n").getBytes());
                        if (t != null) {
                            fileOutputStream.write((logTimeStamp + " : exception " + t.toString() + "\n").getBytes());
                        }
                        fileOutputStream.close();
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Error while logging into file : " + e);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }

    }
}
