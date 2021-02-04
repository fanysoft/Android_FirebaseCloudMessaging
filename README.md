# Android_FirebaseCloudMessaging
This is Firebase Cloud Messaging demo handling both directions of communitations
see https://firebase.google.com/docs/cloud-messaging

a] from server do end-device

to test it your can use PostMan scripts located at https://github.com/ganny26/firebase-notification-postman-collection
payload
- header - add key "Authorization", value "key=**YOUR SERVER KEY**"
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

for PHP implementation see files register.php and push_notification.php added into root of this repo.

result http://www.vancura.cz/programing/Android/Demo/FirebaseCloudMessaging/device-2021-02-03-220502.png

b] from end-device to server

you have to implement your own XMPP Server - like comunity PHP version at https://github.com/sourc7/FCMStream.
Once you dispatch message from end-device you will receive it at server like example:

=== Connecting to fcm-xmpp.googleapis.com:5235...  === 5357279@gcm.googleapis.com/test0FAD177A === Streaming FCM Cloud Connection Server... === 21:09:19 === === Read character 32 === 21:09:19 === =  21:09:41 === {"data":{"my_action":"SAY_HELLO 1 to Server","my_message":"Hello World 1 from Android app"},"time_to_live":86400,"from":"cKyN....

