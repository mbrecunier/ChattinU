package com.example.guest.chattinu.models;

import com.example.guest.chattinu.Constants;
import com.firebase.client.AuthData;

import java.util.Date;

/**
 * Created by Guest on 5/5/16.
 */
public class Message {
    private String sender;
    private String recipient;
    private String content;
    private Date timestamp;
    private String pushId;

    public Message (String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = new Date();
    }

    public Message() {}

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

//    @Override
//    public void onAuthenticated(AuthData authData) {
//        if (authData != null) {
//            String userUid = authData.getUid();
//            mSharedPreferencesEditor.putString(Constants.KEY_UID, userUid).apply();
//        }
//    }
}
