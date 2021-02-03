# Android_FirebaseCloudMessaging
This is Firebase Cloud Messaging demo handling both directions of communitations
see https://firebase.google.com/docs/cloud-messaging

a] from server do end-device

to test it your can use PostMan scripts located at https://github.com/ganny26/firebase-notification-postman-collection
payload
- header - add key "Authorization", value "key=**YOUR SERVER KEY--"
- body
{
  "priority":"HIGH",
  "data":{
      "topic" : "news",
    "notification" : {
      "body" : "This is a Firebase Cloud Messaging Topic Message!",
      "title" : "FCM Message"
      }
  },
  "to":"token"
}

result http://www.vancura.cz/programing/Android/Demo/FirebaseCloudMessaging/device-2021-02-03-220502.png

b] from end-device to server

you have to implment your own XMPP Server like PHP version at https://github.com/sourc7/FCMStream.
Once you sent message from end-device you will receive it at server like example 

=== Connecting to fcm-xmpp.googleapis.com:5235... === 21:09:18 === === Sent 129 bytes === 21:09:18 === === Read 152 bytes === 21:09:18 === === Read 197 bytes === 21:09:18 === X-OAUTH2X-GOOGLE-TOKENPLAIN === Sent 321 bytes === 21:09:18 === ADUzNTA1MTcwNzI3OUBnY20u4VHJmMmFmdWtRVE1ZE4= === Read 51 bytes === 21:09:19 === === Sent 129 bytes === 21:09:19 === === Read 152 bytes === 21:09:19 === === Read 137 bytes === 21:09:19 === === Sent 173 bytes === 21:09:19 === test === Sent 148 bytes === 21:09:19 === === Read 207 bytes === 21:09:19 === 5357279@gcm.googleapis.com/test0FAD177A === Streaming FCM Cloud Connection Server... === 21:09:19 === === Read character 32 === 21:09:19 === === Keepalive exchange === 21:09:19 === === Sent 2 bytes === 21:09:19 === === Read 94 bytes === 21:09:19 === === Read 512 bytes === 21:09:41 === {"data":{"my_action":"SAY_HELLO 1 to Server","my_message":"Hello World 1 from Android app"},"time_to_live":86400,"from":"cKyN

