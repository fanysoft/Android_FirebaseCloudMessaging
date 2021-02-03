# Android_FirebaseCloudMessaging
This is Firebase Cloud Messaging demo handling both directions of communitations
see https://firebase.google.com/docs/cloud-messaging

a] from server do end-device
to test it your can use PostMan scripts located here
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


b] from end-device to server

PHP scripts to be added later
