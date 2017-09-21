package com.example.yashmittal.chat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by yashmittal on 04/05/17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServic";

    public FirebaseMessagingService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Log.d(TAG,"OnMessageRecived : Message Recivied \n" +
               "Title : "+ title + "\n" +
                "Message : " + message);
        sendNotification(title,message);

        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData().get("MsgText"), remoteMessage.getData().get("MsgUser"));
        }


        if (remoteMessage.getNotification() != null) {

        }

    }

    @Override
    public void onDeletedMessages() {

    }

    protected void sendNotification(String title ,String sender) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(sender)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
