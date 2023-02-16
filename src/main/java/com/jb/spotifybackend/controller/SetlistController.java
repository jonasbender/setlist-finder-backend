package com.jb.spotifybackend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.model.SpotifySong;
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

        List<SpotifySong> spotifySongs = new ArrayList<>();

        String jsonSetlist = SetlistApiController.getSetlistById(setlistId);

        JSONObject jsonObject = new JSONObject(jsonSetlist);

        String artistName = jsonObject.getJSONObject("artist").getString("name");
        String venue = jsonObject.getJSONObject("venue").getString("name");
        String eventDate = jsonObject.getString("eventDate");

        // String jsonTestString = PlaylistImageController.getPlaylistImage();
        //System.out.println(jsonTestString);
        System.out.println("Test");

        List<Song> songs = parseSetlist(jsonSetlist, artistName);

        Integer totalDurationMs = 0;


        for (Song song : songs) {
            //System.out.println("test");
            //System.out.println(song);
            //System.out.println(song.getTrackName());
            String trackName = song.getTrackName();
            String artist = song.getArtist();

            String q = "track: " + trackName + " artist: " + artist;
            Track[] spotifyResponse = SpotifyApiController.getSongSearch(q);
            String trackId = spotifyResponse[0].getId();
            String trackImageURL = spotifyResponse[0].getAlbum().getImages()[0].getUrl();
            String album = spotifyResponse[0].getAlbum().getName();
            Boolean isCover = song.getCover();

            totalDurationMs += spotifyResponse[0].getDurationMs();

            System.out.println(trackId);
            System.out.println(trackImageURL);
            System.out.println(album);
            System.out.println(isCover);

            SpotifySong spotifySong = new SpotifySong(trackName, trackId, trackImageURL, artist, album, isCover);
            System.out.println(spotifySong);
            spotifySongs.add(spotifySong);
        }

        System.out.println("Total Duration: " + totalDurationMs);
        Integer totalDuration = totalDurationMs / 60000;
        System.out.println("Total Duration: " + totalDuration);

        System.out.println(spotifySongs);

        JSONArray jsonSongArray = new JSONArray();


        for (SpotifySong song : spotifySongs) {
            System.out.println(song.getTrackImageURL());
            JSONObject jsonSong = new JSONObject();
            jsonSong.put("trackName", song.getTrackName());
            jsonSong.put("trackId", song.getTrackId());
            jsonSong.put("trackImageURL", song.getTrackImageURL());
            jsonSong.put("artist", song.getArtist());
            jsonSong.put("album", song.getAlbum());
            jsonSong.put("isCover", song.getCover());
            jsonSongArray.put(jsonSong);
        }



        JSONObject jsonSongObject = new JSONObject();
        jsonSongObject.put("totalDuration", totalDuration);
        jsonSongObject.put("eventDate", eventDate);
        jsonSongObject.put("eventVenue", venue);
        jsonSongObject.put("artistName", artistName);
        jsonSongObject.put("tracks", jsonSongArray);

        String jsonSetlistString = jsonSongObject.toString(4);


        System.out.println(jsonSetlistString);
        System.out.println(jsonSetlist);
        return jsonSetlistString;

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
