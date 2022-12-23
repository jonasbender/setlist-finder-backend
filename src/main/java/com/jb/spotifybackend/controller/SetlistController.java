package com.jb.spotifybackend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.setlistfmcontroller.SetlistApiController;
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

        List<Song> songs = new ArrayList<>();

        String jsonSetlist = SetlistApiController.getSetlistById(setlistId);

        System.out.println(jsonSetlist);

        JSONObject jsonObject = new JSONObject(jsonSetlist);
        System.out.println(jsonSetlist);

        String artistName = jsonObject.getJSONObject("artist").getString("name");
        System.out.println("Artist Name: " + artistName);

        JSONArray songArray = jsonObject.getJSONObject("sets").getJSONObject("set").getJSONArray("song");


        return jsonSetlist;

    };

    public class SetlistParser {
        public List<Song> parseSetlist(String setlistJson) throws IOException {
            List<Song> songs = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(setlistJson);

            JsonNode setlistNode = root.path("setlist");
            for (JsonNode songNode : setlistNode.path("sets").path("set").path("song")) {
                String songName = songNode.path("name").asText();
                String artistName = songNode.path("artist").path("name").asText();
                Song song = new Song(songName, artistName);
                songs.add(song);
            }

            return songs;
        }
    }


}
