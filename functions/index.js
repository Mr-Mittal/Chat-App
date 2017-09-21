const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
exports.sendNotification = functions.database.ref('/Messages').onWrite(event => {

                const payload = {
                        notification: {
                            title: 'Confession',
                            body: 'Someone Confessed',
                            sound: "default"
                        }
                    };



                 const options = {
                       priority: "high",
                       timeToLive: 60 * 60 * 24
                   };
                   console.log('Sending notifications');
                   return admin.messaging().sendToTopic("android", payload, options);

                });
exports.addWelcomeMessages = functions.auth.user().onCreate(event => {
                  //const user = event.data;
                  console.log('A new user signed in for the first time.');

                  //return admin.database().ref('/Messages').push({
                   // name: 'Firebase Bot',
                    //photoUrl: '/images/firebase-logo.png', // Firebase logo
                   // text: '${user} signed in for the first time! Welcome !'
                 // });
                  const payload = {
                                          notification: {
                                              title: 'Confession',
                                              body: 'A New Member Added In Our Family',
                                              sound: "default"
                                          }
                                      };



                                   const options = {
                                         priority: "high",
                                         timeToLive: 60 * 60 * 24
                                     };
                                     console.log('Sending notifications');
                                     return admin.messaging().sendToTopic("android", payload, options);
                });