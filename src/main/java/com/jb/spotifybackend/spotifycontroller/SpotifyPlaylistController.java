package com.jb.spotifybackend.spotifycontroller;


import com.jb.spotifybackend.model.PlaylistRequest;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

import static com.jb.spotifybackend.spotifycontroller.UserAuthController.spotifyApi;

@RestController
@RequestMapping("/api")
public class SpotifyPlaylistController {

   @PostMapping("/playlists")
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistRequest playlistRequest) {
        try {
            CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(playlistRequest.getUserId(), playlistRequest.getPlaylistName())
                    .collaborative(false)
                    .public_(playlistRequest.isPrivate())
                    .description("Playlist created with Setlist Finder ")
                    .build();
            Playlist playlist = createPlaylistRequest.execute();
            AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlist.getId(), playlistRequest.getSongIds().toArray(new String[0]))
                    .build();
            SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();

            return ResponseEntity.ok().build();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    };


}
