package com.zerowater.environment.data.vo;

/**
 * Created by YoungSoo Kim on 2019-10-22.
 * Zero Ltd
 * byzerowater@gmail.com
 * 푸시 리소스
 */
public class PushResource {

    /**
     * 푸시 유형
     */
    private PushType pushType;
    /**
     * 푸시 메세지
     */
    private String message;

    /**
     * 푸시 리소스
     *
     * @param pushType 푸시 유형
     * @param message  푸시 메세지
     */
    public PushResource(PushType pushType, String message) {
        this.pushType = pushType;
        this.message = message;
    }

    public PushType getPushType() {
        return pushType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PushResource{" +
                "pushType=" + pushType +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 푸시 유형
     */
    public enum PushType {
        /**
         * 앱 업데이트
         */
        APP_UPDATE("AU");
        /**
         * 푸시 유형 코드
         */
        private String value;

        /**
         * 푸시 유형
         *
         * @param value 푸시 유형 코드
         */
        PushType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
