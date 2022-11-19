package com.jb.spotifybackend.controller;

import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jb.spotifybackend.controller.AuthController.spotifyApi;

@RestController
@RequestMapping("/api")
public class SpotifyApiController {

    @GetMapping(value = "user-top-artists")
    public Artist[] getUserTopArtists() {

        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(10)
                .offset(5)
                .build();

        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

            // return users top artists as JSON
            return artistPaging.getItems();
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }
        return new Artist[0];
    }
}
