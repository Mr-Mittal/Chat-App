package com.example.yashmittal.chat;

/**
 * Created by yashmittal on 22/05/17.
 */

public class UserDetails {

    private String UName;
    private String EID;
    private String UID;

    public UserDetails() {
    }

    public UserDetails(String UName, String EID, String UID) {
        this.UName = UName;
        this.EID = EID;
        this.UID = UID;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
