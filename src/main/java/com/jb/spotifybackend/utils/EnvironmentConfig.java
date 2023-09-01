package com.jb.spotifybackend.utils;

public class EnvironmentConfig {

    public static final String REDIRECT_URI;

    static {
        // Set the value based on the active profile
        if ("prod".equals(System.getProperty("spring.profiles.active"))) {
            REDIRECT_URI = "https://setlistfinderbackend-9a6bfa1f1e4a.herokuapp.com/api/callback/";
        } else {
            REDIRECT_URI = "https://setlistfinderbackend-9a6bfa1f1e4a.herokuapp.com/api/callback/";

            //REDIRECT_URI = "http://localhost:8080/api/callback";
        }
    }

}
