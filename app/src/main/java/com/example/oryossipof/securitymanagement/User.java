package com.example.oryossipof.securitymanagement;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by or yossipof on 27/09/2017.
 */
@IgnoreExtraProperties
class User {


    public String username;
    public  String time;
    public String description;
    public String urlImage;

    public User(String description, String time, String username,String urlImage)
    {
        this.username = username;
        this.time = time;
        this.description = description;
        this.urlImage =urlImage;

    }

    public User()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
