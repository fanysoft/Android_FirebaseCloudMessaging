package com.example.josefvancura.firebasecloudmessagingmydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import static com.example.josefvancura.firebasecloudmessagingmydemo.MainActivity.textViewMessageRecieved;


/*
called once message recieved (from server to end-device)
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static String TAG = "myTAG-MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();

        String From = extras.getString("messageFrom");
        String messagePayload = extras.getString("messagePayload");
        String messageNotification = extras.getString("messageNotification");

        String allRecieved = "Message Recieved !\nfrom=" + From + "\nmessagePayload=" + messagePayload + "\nmessageNotification=" + messageNotification;
        // update GUI here
        textViewMessageRecieved.setText(allRecieved);

        Toast.makeText(context, allRecieved, Toast.LENGTH_SHORT).show();

        Log.d(TAG, "MyBroadcastReceiver onRecieve " + allRecieved);

    }
}
