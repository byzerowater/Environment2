package com.zerowater.environment.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoungSoo Kim on 2019-10-08.
 * Zero Ltd
 * byzerowater@gmail.com
 * 알림정보 리소스
 */
public class AlertResource implements Parcelable {

    /**
     * Parcelable CREATOR
     */
    public static final Parcelable.Creator<AlertResource> CREATOR = new Parcelable.Creator<AlertResource>() {
        @Override
        public AlertResource createFromParcel(Parcel source) {
            return new AlertResource(source);
        }

        @Override
        public AlertResource[] newArray(int size) {
            return new AlertResource[size];
        }
    };
    /**
     * 알림 유형
     */
    private Type type;
    /**
     * 안내 메세지
     */
    private String message;
    /**
     * 긍정적인 버튼 문구
     */
    private String positive;
    /**
     * 부정정인 버튼 문구
     */
    private String negative;

    /**
     * 알림정보 리소스
     *
     * @param type     알림 유형
     * @param message  안내 메세지
     * @param positive 긍정적인 버튼 문구
     * @param negative 부정정인 버튼 문구
     */
    public AlertResource(Type type, String message, String positive, String negative) {
        this.type = type;
        this.message = message;
        this.positive = positive;
        this.negative = negative;
    }

    protected AlertResource(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.message = in.readString();
        this.positive = in.readString();
        this.negative = in.readString();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", positive='" + positive + '\'' +
                ", negative='" + negative + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.message);
        dest.writeString(this.positive);
        dest.writeString(this.negative);
    }

    /**
     * 알림 유형
     */
    public enum Type {
        /**
         * 네트워크 미연결
         */
        NO_NETWORK_CONNECTED,
        /**
         * 서버 에러(200이 아닐경우)
         */
        SERVER,
        /**
         * 앱(소켓타임아웃 등)
         */
        APP,
        /**
         * 권한 없음
         */
        UNAUTHORIZED,
        /**
         * 로그아웃
         */
        LOGOUT,
        /**
         * 앱 업데이트
         */
        UPDATE,
        /**
         * 권한
         */
        PERMISSION

    }
}


