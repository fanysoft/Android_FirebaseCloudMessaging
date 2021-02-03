package com.example.josefvancura.firebasecloudmessagingmydemo;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*
this service is registered in manifest and contains
-  method to recieve downstream message
- callback once newToken is updated
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "myTAG-MyFirebaseMessagingService";

    NotificationManager notificationManager;
    String CHANNEL_ID1 = "1";
    int NOTIFICATION_ID1 = 1;


    // Called when message is received.
    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived");

        // Intent to send data to BroadCast Reciever
        Intent intent = new Intent();

        // From
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        intent.putExtra("messageFrom",remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            String messagePayload = remoteMessage.getData().toString();
            Log.d(TAG, "Message contains data payload: " + messagePayload);
            intent.putExtra("messagePayload", messagePayload);

            // see all varibales in RemoteMessage.class
            String getSenderId = remoteMessage.getSenderId();
            String getTo = remoteMessage.getTo();
            long getSentTime = remoteMessage.getSentTime();

            Log.d(TAG, "senderId=" + getSenderId + " to=" + getTo + " sentTime=" + getSentTime);

            ShowNotification(messagePayload);


            /**
             // Check if data needs to be processed by long running job
             // For long-running tasks (10 seconds or more) use WorkManager.
            if (some){
                scheduleJob();
            } else {
                // handle now
            }
            **/

        }else{
            Log.d(TAG, "No data payload");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            String messageNotification = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message contains Notification Body: " + messageNotification);
            intent.putExtra("messageNotification", messageNotification);

        }else{
            Log.d(TAG, "No notification payload");
        }

        // inform BroadCast reciever
        intent.setAction("com.my.app.onMessageReceived");
        sendBroadcast(intent);

    }



    /**
     * Called if FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve
     * the token.
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onNewToken(String token) {

        Log.d(TAG, "Refreshed token: " + token);

        MainActivity.SendTokenToServer(getApplicationContext(), token);

    }


    /**
     * Schedule async work using WorkManager - for long running task - see above
     */
    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }


    private void ShowNotification(String text){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID1)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Cloud Messaging - received !")
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //.setContentIntent(pendingIntent);


        // Create a channel and set the importance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CH_NAME1";
            String description = "about CH_NAME1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID1, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //  If the build version is greater than JELLY_BEAN (Android 4.2) and lower than OREO (Android 8), set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }


        // Show the notification
        notificationManager.notify(NOTIFICATION_ID1, builder.build());


    }

}