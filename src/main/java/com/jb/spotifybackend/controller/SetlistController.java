package com.jb.spotifybackend.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.model.SpotifySong;
import com.jb.spotifybackend.setlistfmcontroller.SetlistApiController;
import com.jb.spotifybackend.spotifycontroller.SpotifyApiController;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jb.spotifybackend.controller.PlaylistImageController.getPlaylistImage;
import static com.jb.spotifybackend.spotifycontroller.SpotifyApiController.searchArtist;


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

        Artist[] artistObject = searchArtist(artistName);
        String artistImageUrl = artistObject[0].getImages()[0].getUrl();

        ResponseEntity<byte[]>  playlistImage = getPlaylistImage(artistImageUrl, artistName, venue, eventDate);
        String base64Image = Base64.getEncoder().encodeToString(playlistImage.getBody());


        System.out.println("songs: " + songs);
        for (Song song : songs) {
            //System.out.println("test");
            //System.out.println(song);
            //System.out.println(song.getTrackName());
            String trackName = song.getTrackName();
            String artist = song.getArtist();
            System.out.println("Anzahl an songs: " + songs.size());

            String cleanedTrackName = trackName.replace(" ", "%20");
            String cleanedArtistName = artistName.replace(" ", "%20");

            String q = "track: " + trackName + " artist: " + artistName;
            //String q = "track%3A" + cleanedTrackName + "%20artist%3A" + cleanedArtistName;
            Track[] spotifyResponse = SpotifyApiController.getSongSearch(q);
            System.out.println("Spotify Response " + spotifyResponse.toString());
            String trackId = spotifyResponse[0].getId();
            System.out.println("Track Id: "+trackId);
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
            //TimeUnit.SECONDS.sleep(1);
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
        jsonSongObject.put("artistImageUrl", artistImageUrl);
        jsonSongObject.put("playlistImage", base64Image);

        String jsonSetlistString = jsonSongObject.toString(4);


        //System.out.println(jsonSetlistString);
        //System.out.println(jsonSetlist);
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
