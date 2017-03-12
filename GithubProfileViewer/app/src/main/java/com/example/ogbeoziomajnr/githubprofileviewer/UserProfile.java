package com.example.ogbeoziomajnr.githubprofileviewer;

import java.io.Serializable;

/**
 * Created by SQ-OGBE PC on 10/03/2017.
 */

public class UserProfile implements Serializable {


    private String user_name;

    private String user_url;

    private String image_url;

    public UserProfile() {
    }

    public UserProfile(String user_name, String user_url, String image_url) {
        this.user_name = user_name;
        this.user_url = user_url;
        this.image_url = image_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

