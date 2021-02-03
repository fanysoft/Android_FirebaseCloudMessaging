package com.example.josefvancura.firebasecloudmessagingmydemo;

import android.content.Context;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myTAG-MainActivity";

    Context context;
    public static TextView textViewToken, textViewMessageRecieved;
    private static EditText editText1, editText2;
    private static Button buttonSendMessage;

    private static final String URL = "https://www.vancura.cz/programing/Android/Firebase/Cloud%20Messaging/register.php";

    static RequestQueue queue;

    FirebaseMessaging firebaseMessaging;
    Integer msgId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "activity START");
        context = this;

        // GUI
        textViewToken = findViewById(R.id.textViewToken);
        textViewMessageRecieved = findViewById(R.id.textViewMessRecieved);
        buttonSendMessage = findViewById(R.id.button);
        editText1 = findViewById(R.id.EditText1);
        editText2 = findViewById(R.id.EditText2);

        // BroadCast reciever - intent - to get callback from Service
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.my.app.onMessageReceived");
        // BroadCast reciever - register
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, intentFilter);

        // FirebaseMessaging instance
        firebaseMessaging = FirebaseMessaging.getInstance();

        // Get token
        firebaseMessaging.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {

                        if (!task.isSuccessful()) {
                            Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d(TAG, "Token registration=" + token);

                        textViewToken.setText(token.toString());

                        // Store token at Server via PHP
                        SendTokenToServer(context, token);

                        // Subscribe to Topic
                        firebaseMessaging.subscribeToTopic("news").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (!task.isSuccessful()) {
                                            Log.e(TAG, "subscribed to Topic news FAILED");
                                        }
                                        Log.d(TAG, "subscribed to Topic news");

                                    }
                        });
                    }
        });



        // Send message
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Sender ID - A unique numerical value created when you create your Firebase project, available in the Cloud Messaging tab of the Firebase console Settings pane.
                // The sender ID is used to identify each sender that can send messages to the client app.
                final String SENDER_ID = BuildConfig.myHiddenSenderId; // REPLACE BY YOUR Sender id

                // Message ID - keep unique
                msgId = msgId + 1;

                String message = "defaultMessage" + " " + msgId;
                String action = "defaultAction" + " " + msgId;


                if (editText1.getText().length() > 0) {
                    message = editText1.getText().toString() + " " + msgId;
                }

                if (editText2.getText().length() > 0) {
                    action = editText2.getText().toString() + " " + msgId;
                }


                firebaseMessaging.send(new RemoteMessage.Builder(SENDER_ID + "@fcm.googleapis.com")
                        .setMessageId(Integer.toString(msgId))
                        .addData("my_message", message)
                        .addData("my_action",action)
                        .build());


                Log.d(TAG,"Sending message=" + message + ", action=" + action);

            }
        });

    }

    // Send token to dB
    static public void SendTokenToServer(Context context, String token) {

        Log.d(TAG, "sendRegistrationToServer - Sending Token to PHP " + token);

        queue = Volley.newRequestQueue(context);

        String url = URL + "?Token=" + token;

        // Request a string response from the provided URL - GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "sendRegistrationToServer - response="+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "sendRegistrationToServer - ERROR " + error);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


}
