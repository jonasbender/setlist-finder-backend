package com.jb.spotifybackend.spotifycontroller;


import com.jb.spotifybackend.model.PlaylistRequest;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.jb.spotifybackend.spotifycontroller.UserAuthController.spotifyApi;

@RestController
@RequestMapping("/api")
public class SpotifyPlaylistController {

   @PostMapping("/createPlaylist")
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistRequest playlistRequest) {
       System.out.println("accessToken : " + playlistRequest.getAccessToken());
       spotifyApi.setAccessToken(playlistRequest.accessToken);

       String userId = getUserProfileId();

        try {
            CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistRequest.getPlaylistName())
                    .collaborative(false)
                    .public_(playlistRequest.isPrivate())
                    .description("Playlist created with Setlist Finder ")
                    .build();
            Playlist playlist = createPlaylistRequest.execute();
            System.out.println("playlistID: " + playlist.getId());
            String[] songIds = playlistRequest.getTrackIds().toArray(new String[0]);
            System.out.println(songIds);
            AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(playlist.getId(), songIds)
                    .build();
            SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();

            return ResponseEntity.ok().build();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    };

   public static String getUserProfileId() {

       final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
               .build();
       String userId = "";
       try {
           final User user = getCurrentUsersProfileRequest.execute();
           System.out.println(user);
           userId = user.getId();
           System.out.println(userId);
       } catch (IOException | SpotifyWebApiException | ParseException e) {
           System.out.println("Error: " + e.getMessage());
       }
       return userId;
   }


}
