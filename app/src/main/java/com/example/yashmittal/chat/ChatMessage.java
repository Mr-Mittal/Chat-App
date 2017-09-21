package com.example.yashmittal.chat;

import java.util.Date;

/**
 * Created by yashmittal on 26/04/17.
 */

public class ChatMessage {
    private String URL;
    private String MsgText;
    private String MsgUser;
    private String Model;
    private long MsgTym;

    public ChatMessage() {

    }
    public ChatMessage(String url, String msgUser)
    {
        this.URL = url;
        this.MsgUser = msgUser;
       // this.Model = Model;

        MsgTym = new Date().getTime();
   }

    public ChatMessage(String msgText, String msgUser, String Model)
    {
        this.MsgText = msgText;
        this.MsgUser = msgUser;
        this.Model = Model;

        MsgTym = new Date().getTime();
    }

   public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMsgUser() {
        return MsgUser;
    }

    public void setMsgUser(String msgUser) {
        MsgUser = msgUser;
    }

    public long getMsgTym() {
        return MsgTym;
    }

    public void setMsgTym(long msgTym) {
        MsgTym = msgTym;
    }

    public String getMsgText() { return MsgText;}

    public void setMsgText(String msgText) {
        MsgText = msgText;
    }

}
