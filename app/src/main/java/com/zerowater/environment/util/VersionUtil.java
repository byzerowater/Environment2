package com.zerowater.environment.util;

import com.zerowater.environment.BuildConfig;
import com.zerowater.environment.data.model.VersionData;

/**
 * Created by YoungSoo Kim on 2019-10-12.
 * Zero Ltd
 * byzerowater@gmail.com
 * VersionUtil
 */
public class VersionUtil {

    /**
     * 앱 업데이트 여부 가져오기
     *
     * @param versionData 앱 버전 데이터
     * @return 앱 업데이트 여부
     */
    public static boolean isUpdateApk(VersionData versionData) {
        int newVersion = 0;
        if (versionData != null) {
            newVersion = StringUtil.getInt(versionData.getAppVer().replace(".", ""));
        }
        int appVersion = StringUtil.getInt(BuildConfig.VERSION_NAME.replace(".", ""));

        return appVersion < newVersion;
    }
}
