package com.zerowater.environment.data.model.base;

/**
 * Created by YoungSoo Kim on 2016-08-02.
 * company Ltd
 * byzerowater@gmail.com
 * 공통 요청 데이터
 */
public class BaseRequest {

    /**
     * 데이터 body 영역
     */
    private Object body;

    /**
     * 공통 요청 데이터
     *
     * @param body 데이터 body 영역
     */
    public BaseRequest(Object body) {
        this.body = body;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "body=" + body +
                '}';
    }
}
