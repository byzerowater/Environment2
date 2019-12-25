package com.zerowater.environment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * byzerowater@gmail.com
 * 에러 데이터
 */
public class ErrorData implements Parcelable {

    public static final Creator<ErrorData> CREATOR = new Creator<ErrorData>() {
        @Override
        public ErrorData createFromParcel(Parcel source) {
            return new ErrorData(source);
        }

        @Override
        public ErrorData[] newArray(int size) {
            return new ErrorData[size];
        }
    };
    /**
     * 에러코드
     */
    private int errorCode;
    /**
     * 에러 메세지
     */
    private String errorMsg;

    protected ErrorData(Parcel in) {
        this.errorCode = in.readInt();
        this.errorMsg = in.readString();
    }

    public ErrorData(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errorCode);
        dest.writeString(this.errorMsg);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
