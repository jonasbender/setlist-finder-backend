package com.jb.spotifybackend.utils;

import com.wrapper.spotify.SpotifyHttpManager;

import java.net.URI;

public class KeyStore {


    // Spotify Keys and Redirect URL
    public static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code/");
    public static final String CLIENT_ID = "013cc9a435c7467492532478e1144d3c";
    public static final String CLIENT_SECRET = "746bc9dc80924d48804b351fcba66356";

    // setlist.fm API Token

    public static final String API_KEY = "c35We3GBKCaoF21NOBOeDQobebn9ttQ8wtzq";
}