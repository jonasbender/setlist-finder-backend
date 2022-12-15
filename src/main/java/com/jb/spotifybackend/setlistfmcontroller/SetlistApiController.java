package com.jb.spotifybackend.setlistfmcontroller;


import com.jb.spotifybackend.utils.KeyStore;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/api")
public class SetlistApiController {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @RequestMapping(value = "setlists/{artist}", method = RequestMethod.GET)
    public String getSetlists(@PathVariable("artist") String artist) throws Exception {

        String mbid = getMBID(artist);
        System.out.println(mbid);
        String jsonSetlists = getArtistSetlists(mbid);


        return jsonSetlists;

    };

    public String getArtistSetlists(String mbid) throws Exception {
        System.out.println(mbid);
        String url = "https://api.setlist.fm/rest/1.0/artist/" + mbid + "/setlists?p=1";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("Accept", "application/json")
                .setHeader("x-api-key", KeyStore.API_KEY)
                .build();

        System.out.println(request);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(response.body());

        System.out.println(response);

        if (response.statusCode() == 429) {
            System.out.println(response.headers());
        }
        String jsonObject = response.body();

        return jsonObject;
    };

    public String getMBID(String artist) throws Exception {
        System.out.println(artist);
        artist = artist.replace(" ", "%20");
        String url = "https://api.setlist.fm/rest/1.0/search/artists?artistName=" + artist + "&p=1&sort=relevance";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("Accept", "application/json")
                .setHeader("x-api-key", KeyStore.API_KEY)
                .build();

        System.out.println(request);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray result = jsonObject.getJSONArray("artist");
        String mbid = result.getJSONObject(0).getString("mbid");
        System.out.println(mbid);

        return mbid;
    };


    public String getSetlistById(String setlistId) throws Exception {
        String url = "https://api.setlist.fm/rest/1.0/setlist/" + setlistId;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("Accept", "application/json")
                .setHeader("x-api-key", KeyStore.API_KEY)
                .build();

        System.out.println(request);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(response.body());

        System.out.println(response);

        if (response.statusCode() == 429) {
            System.out.println(response.headers());
        }
        String jsonObject = response.body();

        return jsonObject;
    };


}
