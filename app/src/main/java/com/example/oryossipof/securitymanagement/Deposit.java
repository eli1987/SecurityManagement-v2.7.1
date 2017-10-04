package com.example.oryossipof.securitymanagement;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by אלי on 04/10/2017.
 */
@IgnoreExtraProperties
public class Deposit {

    public String depositDescrption;
    public String month;
    public String DayDelivered;
    public String username;
    public String whoDepositFor;
    public String whoDeliverDeposit;
    public String imageUri;
    public String depositnumber;
    public String isReturn;

    public Deposit()
    {

    }

    public Deposit(String depositDescrption, String month, String username,String whoDepositFor,String whoDeliverDeposit,String imageUri,String depositnumber,String isReturn,String DayDelivered)
    {

        this.depositDescrption = depositDescrption;
        this.username =username;
        this.month = month;
        this.DayDelivered = DayDelivered;
        this.whoDepositFor=whoDepositFor;
        this.whoDeliverDeposit=whoDeliverDeposit;
        this.imageUri=imageUri;
        this.isReturn=isReturn;
        this.depositnumber=depositnumber;

    }


    public String getImageUri() {
        return imageUri;
    }

    public String getDayDelivered() {
        return DayDelivered;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public String getDepositDescrption() {
        return depositDescrption;
    }

    public String getMonth() {
        return month;
    }

    public String getDepositnumber() {
        return depositnumber;
    }

    public String getUsername() {
        return username;
    }

    public String getWhoDeliverDeposit() {
        return whoDeliverDeposit;
    }

    public String getWhoDepositFor() {
        return whoDepositFor;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDayDelivered(String dayDelivered) {
        DayDelivered = dayDelivered;
    }

    public void setDepositDescrption(String depositDescrption) {
        this.depositDescrption = depositDescrption;
    }

    public void setDepositnumber(String depositnumber) {
        this.depositnumber = depositnumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWhoDeliverDeposit(String whoDeliverDeposit) {
        this.whoDeliverDeposit = whoDeliverDeposit;
    }

    public void setWhoDepositFor(String whoDepositFor) {
        this.whoDepositFor = whoDepositFor;
    }
}



