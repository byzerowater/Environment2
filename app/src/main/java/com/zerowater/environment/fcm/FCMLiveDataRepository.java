package com.zerowater.environment.fcm;

import com.zerowater.environment.data.vo.PushResource;
import com.zerowater.environment.util.SingleMutableLiveData;

import androidx.lifecycle.LiveData;

/**
 * Created by YoungSoo Kim on 2019-10-16.
 * Zero Ltd
 * byzerowater@gmail.com
 * 푸시 데이터 전달
 */
public class FCMLiveDataRepository {

    /**
     * 단일건 처리 MutableLiveData
     */
    private SingleMutableLiveData<PushResource> mPushResource = new SingleMutableLiveData<>();

    public LiveData<PushResource> getPushResource() {
        return mPushResource;
    }

    public void postPushResource(PushResource data) {
        mPushResource.postValue(data);
    }
}
