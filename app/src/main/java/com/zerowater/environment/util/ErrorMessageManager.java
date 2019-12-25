package com.zerowater.environment.util;

import android.content.Context;

import com.google.gson.Gson;
import com.zerowater.environment.R;
import com.zerowater.environment.data.model.ErrorData;
import com.zerowater.environment.data.vo.AlertResource;

import java.net.HttpURLConnection;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2019-05-07.
 * Zero Ltd
 * byzerowater@gmail.com
 * 에러 메세지 관리자
 */
public class ErrorMessageManager {

    /**
     * Context
     */
    private Context mContext;
    /**
     * Gson
     */
    private Gson mGson;

    /**
     * 에러 메세지 관리자
     *
     * @param context Context
     * @param gson    Gson
     */
    public ErrorMessageManager(Context context, Gson gson) {
        mContext = context;
        mGson = gson;
    }

    /**
     * 에러 알림정보 가져오기
     *
     * @param e Throwable
     * @return 에러 알림정보
     */
    public AlertResource getAlertResource(Throwable e) {

        AlertResource.Type type = AlertResource.Type.APP;
        String message = null;

        if (e instanceof HttpException) {
            type = AlertResource.Type.SERVER;
            ErrorData errorData = getErrorData(e);
            if (errorData != null) {
                message = errorData.getErrorMsg();
                int errorCode = errorData.getErrorCode();
                if (HttpURLConnection.HTTP_UNAUTHORIZED == errorCode) {
                    type = AlertResource.Type.UNAUTHORIZED;
                }

            }
        } else if (e instanceof NoConnectivityException) {
            message = e.getMessage();
            type = AlertResource.Type.NO_NETWORK_CONNECTED;
        }

        String positive = mContext.getString(R.string.text_retry);
        String negative = mContext.getString(R.string.text_close);

        switch (type) {
            case NO_NETWORK_CONNECTED:
                negative = mContext.getString(R.string.text_setting);
                break;
            case SERVER:
            case APP:
                break;
            case UNAUTHORIZED:
                positive = null;
                negative = mContext.getString(R.string.text_confirm);
                break;
        }

        return new AlertResource(type,
                StringUtil.isEmptyReplace(message, mContext.getString(R.string.text_network_error)),
                positive,
                negative);
    }

    /**
     * 에러 데이터 가져오기
     *
     * @param e Throwable
     * @return 에러 데이터
     */
    private ErrorData getErrorData(Throwable e) {
        ErrorData errorData = null;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            try {
                if (!httpException.response().isSuccessful()) {
                    String body = httpException.response().errorBody().string();
                    if (!StringUtil.isEmpty(body)) {
                        errorData = new ErrorData(httpException.response().code(), body);
                    }
                }
            } catch (Exception e1) {
                Timber.e(e1);
            }
        }
        return errorData;
    }
}
