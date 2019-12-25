package com.zerowater.environment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2016-08-08.
 * company Ltd
 * byzerowater@gmail.com
 * 앱 버전 데이터
 */
public class VersionData implements Parcelable {

    public static final Parcelable.Creator<VersionData> CREATOR = new Parcelable.Creator<VersionData>() {
        @Override
        public VersionData createFromParcel(Parcel source) {
            return new VersionData(source);
        }

        @Override
        public VersionData[] newArray(int size) {
            return new VersionData[size];
        }
    };
    /**
     * URL
     */
    private String appUrl;
    /**
     * 앱 버전
     */
    private String appVer;
    /**
     * 강제 업데이트 여부
     */
    private String frcUdtFg;
    /**
     * 업데이트 내용
     */
    private String udtMsg;

    public VersionData(String appUrl, String appVer, String frcUdtFg, String udtMsg) {
        this.appUrl = appUrl;
        this.appVer = appVer;
        this.frcUdtFg = frcUdtFg;
        this.udtMsg = udtMsg;
    }

    protected VersionData(Parcel in) {
        this.appUrl = in.readString();
        this.appVer = in.readString();
        this.frcUdtFg = in.readString();
        this.udtMsg = in.readString();
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getFrcUdtFg() {
        return frcUdtFg;
    }

    public void setFrcUdtFg(String frcUdtFg) {
        this.frcUdtFg = frcUdtFg;
    }

    public String getUdtMsg() {
        return udtMsg;
    }

    public void setUdtMsg(String udtMsg) {
        this.udtMsg = udtMsg;
    }

    @Override
    public String toString() {
        return "VersionData{" +
                "appUrl='" + appUrl + '\'' +
                ", appVer='" + appVer + '\'' +
                ", frcUdtFg='" + frcUdtFg + '\'' +
                ", udtMsg='" + udtMsg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appUrl);
        dest.writeString(this.appVer);
        dest.writeString(this.frcUdtFg);
        dest.writeString(this.udtMsg);
    }
}
