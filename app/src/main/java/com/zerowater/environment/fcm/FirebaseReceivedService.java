package com.zerowater.environment.fcm;

import android.media.Ringtone;
import android.media.RingtoneManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zerowater.environment.data.DataManager;
import com.zerowater.environment.data.vo.PushResource;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import dagger.android.AndroidInjection;
import timber.log.Timber;


/**
 * Created by YoungSoo Kim on 2019-10-07.
 * Zero Ltd
 * byzerowater@gmail.com
 * 푸시 전달 서비스
 */
public class FirebaseReceivedService extends FirebaseMessagingService {

    /**
     * PUSH 유형 키
     */
    private static final String EXTRA_PUSH_TYPE = "type";
    /**
     * PUSH 메세지
     */
    private static final String EXTRA_PUSH_MESSAGE = "message";
    /**
     * 데이터 관리자
     */
    @Inject
    DataManager mDataManager;
    /**
     * 푸시 데이터 전달
     */
    @Inject
    FCMLiveDataRepository mFCMLiveDataRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type = remoteMessage.getData().get(EXTRA_PUSH_TYPE);
        String message = remoteMessage.getData().get(EXTRA_PUSH_MESSAGE);

        Timber.i("onMessageReceived type %s message %s", type, message);

        if (PushResource.PushType.APP_UPDATE.getValue().equals(type)) {
            mFCMLiveDataRepository.postPushResource(new PushResource(PushResource.PushType.APP_UPDATE, message));
        }

        Ringtone ringtone = RingtoneManager.getRingtone(FirebaseReceivedService.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        if (ringtone != null) {
            ringtone.play();
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Timber.d("Refreshed token %s", token);
    }

}
