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

        getMBID(artist);




        return "test";

    };

    public String getMBID(String artist) throws Exception {
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

        return "mbid";
    };

}
