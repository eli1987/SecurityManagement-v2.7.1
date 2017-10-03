package com.example.oryossipof.securitymanagement;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by אלי on 29/09/2017.
 */
@IgnoreExtraProperties
public class Lost {

    public String lostDescrption;
    public String month;
    public String DayFounded;
    public String username;
    public String whereLostFound;
    public String whoFound;
    public String imageUri;
    public String lostnumber;
    public String isReturn;

    public Lost()
    {

    }

    public Lost(String lostDescrption, String month, String username,String whereLostFound,String whoFound,String imageUri,String lostnumber,String isReturn,String DayFounded)
    {

        this.lostDescrption = lostDescrption;
        this.username =username;
        this.month = month;
        this.DayFounded = DayFounded;
        this.whoFound=whoFound;
        this.whereLostFound=whereLostFound;
        this.imageUri=imageUri;
        this.isReturn=isReturn;
        this.lostnumber=lostnumber;

    }

    public String getLostDescrption() {
        return lostDescrption;
    }

    public String getMonth() {
        return month;
    }

    public String getUsername() {
        return username;
    }

    public String getWhereLostFound() {
        return whereLostFound;
    }

    public String getWhoFound() {
        return whoFound;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLostDescrption(String lostDescrption) {
        this.lostDescrption = lostDescrption;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setWhereLostFound(String whereLostFound) {
        this.whereLostFound = whereLostFound;
    }

    public void setWhoFound(String whoFound) {
        this.whoFound = whoFound;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getLostnumber() {
        return lostnumber;
    }

    public void setLostnumber(String lostnumber) {
        this.lostnumber = lostnumber;
    }

    public void setDayFounded(String dayFounded) {
        DayFounded = dayFounded;
    }

    public String getDayFounded() {
        return DayFounded;
    }
}



