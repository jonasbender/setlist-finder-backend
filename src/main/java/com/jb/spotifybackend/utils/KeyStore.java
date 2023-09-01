package com.jb.spotifybackend.utils;

import com.wrapper.spotify.SpotifyHttpManager;

import java.net.URI;

public class KeyStore {


    // Spotify Keys and Redirect URL
    //public static final URI redirectUri = SpotifyHttpManager.makeUri("https://setlistfinderbackend-9a6bfa1f1e4a.herokuapp.com/api/callback/");

    public static final URI redirectUri = URI.create(EnvironmentConfig.REDIRECT_URI);

    public static final String CLIENT_ID = System.getenv("CLIENT_ID");
    public static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");
    public static final String API_KEY = System.getenv("API_KEY");
}
