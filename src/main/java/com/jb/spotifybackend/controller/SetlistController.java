package com.jb.spotifybackend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.setlistfmcontroller.SetlistApiController;
import com.jb.spotifybackend.spotifycontroller.SpotifyApiController;
import com.wrapper.spotify.model_objects.specification.Track;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class SetlistController {


    @RequestMapping(value = "tracks/{setlistId}", method = RequestMethod.GET)
    public String getSetlistTracks(@PathVariable("setlistId") String setlistId) throws Exception {

        String jsonSetlist = SetlistApiController.getSetlistById(setlistId);

        JSONObject jsonObject = new JSONObject(jsonSetlist);

        String artistName = jsonObject.getJSONObject("artist").getString("name");

        List<Song> songs = parseSetlist(jsonSetlist, artistName);

        for (Song song : songs) {
            System.out.println("test");
            System.out.println(song);
            System.out.println(song.getTrackName());


        }

        //String q = "track%3A" + trackName + "%20artist%3A" + artist;
        //Track[] spotifyResponse = SpotifyApiController.getSongSearch(q);
        //System.out.println(spotifyResponse);

        return jsonSetlist;

    };

    public List<Song> parseSetlist(String setlistJson, String artistName) throws IOException {
        List<Song> songs = new ArrayList<>();
        Boolean isCover = Boolean.FALSE;
        String rootArtist = artistName;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(setlistJson);
        System.out.println("pointer");
        String fieldValue = root.path("id").asText();
        System.out.println(fieldValue);
        JsonNode setsNode = root.path("sets").path("set");

        for (JsonNode setNode : setsNode) {
            JsonNode songsNode = setNode.path("song");
            for (JsonNode songNode : songsNode) {


                System.out.println("pointer");
                String songName = songNode.path("name").asText();
                System.out.println(songName);

                if (songNode.has("cover")) {
                    artistName = songNode.path("cover").path("name").asText();
                    isCover = Boolean.TRUE;
                } else {
                    artistName = rootArtist;
                    isCover = Boolean.FALSE;
                }
                Song song = new Song(songName, artistName, isCover);
                System.out.println(song);
                songs.add(song);
            }
        }

        return songs;
    }



}
