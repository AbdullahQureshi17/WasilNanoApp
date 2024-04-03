package com.nano.liteloan.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.RegisterDeviceResponse;
import com.nano.liteloan.info.RegisterPushIdRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.PreferenceUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad on 07/20/2020.
 */
public class FireMsgService extends FirebaseMessagingService {

    String channelId = "1";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);


        Log.d("Msg", "Message received [" + remoteMessage + "]");
        Log.d("Msg", "Body!!!!!!!! received [" + remoteMessage.getData().get("image") + "]");

        // Create Notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap image = null;
        try {
            URL url = new URL(remoteMessage.getData().get("image"));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setLargeIcon(image)
                .setSubText("Notification")
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent);



        notificationManager
                .notify(1410, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


        registerPushId(s);
    }

    private void registerPushId(String pushId) {

        if (PreferenceUtils.getInstance().getDeviceId() == null) {
            // do nothing
        } else {

            RegisterPushIdRequest request = new RegisterPushIdRequest();
            request.pushId = pushId;
            request.deviceId = PreferenceUtils.getInstance().getDeviceId();

            AuthBusiness business = new AuthBusiness();
            business.registerPushId(request, new ResponseCallBack<RegisterDeviceResponse>() {
                @Override
                public void onSuccess(RegisterDeviceResponse body) {


                }

                @Override
                public void onFailure(String message) {

                }
            });
        }


    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channal";
            String description = "This is notification channal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
