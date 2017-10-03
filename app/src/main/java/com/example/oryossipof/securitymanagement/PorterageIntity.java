package com.example.oryossipof.securitymanagement;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by or yossipof on 01/10/2017.
 */

@IgnoreExtraProperties
class PorterageIntity {
    public String Numhands;
    public String Numpeople;
    public String Numstuff;
    public String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(String totalPeople) {
        this.totalPeople = totalPeople;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String date;
    public String totalPeople;
    public String totalMoney;



    public PorterageIntity(String numhands, String numpeople, String numstuff, String status, String date, String totalMoney, String totalPeople) {
        this.Numhands = numhands;
        this.Numpeople = numpeople;
        this.Numstuff = numstuff;
        this.status = status;
        this.date = date;
        this.totalMoney = totalMoney;
        this.totalPeople = totalPeople;

    }

    public PorterageIntity()
    {

    }

    public String getNumhands() {
        return Numhands;
    }

    public String getNumpeople() {
        return Numpeople;
    }

    public String getNumstuff() {
        return Numstuff;
    }

    public String getStatus() {
        return status;
    }

    public void setNumhands(String numhands) {
        Numhands = numhands;
    }

    public void setNumpeople(String numpeople) {
        Numpeople = numpeople;
    }

    public void setNumstuff(String numstuff) {
        Numstuff = numstuff;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
