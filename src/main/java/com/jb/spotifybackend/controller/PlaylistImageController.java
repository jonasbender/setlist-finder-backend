package com.jb.spotifybackend.controller;


import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.model.SpotifySong;
import com.jb.spotifybackend.setlistfmcontroller.SetlistApiController;
import com.jb.spotifybackend.spotifycontroller.SpotifyApiController;
import com.wrapper.spotify.model_objects.specification.Track;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PlaylistImageController {


    public static String getPlaylistImage() throws Exception {
        System.out.println("this is a test");
        return "";
    };



}
