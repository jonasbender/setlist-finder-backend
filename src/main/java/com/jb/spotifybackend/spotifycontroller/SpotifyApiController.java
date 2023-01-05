package com.jb.spotifybackend.spotifycontroller;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

import static com.jb.spotifybackend.spotifycontroller.UserAuthController.spotifyApi;
import static com.jb.spotifybackend.spotifycontroller.ServerAuthController.clientCredentials_Sync;
import static com.jb.spotifybackend.spotifycontroller.ServerAuthController.spotifyApiLight;

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

    @GetMapping(value = "search/{q}")
    public Artist[] getSearchResults(@PathVariable("q") String q) {
        clientCredentials_Sync();
        final SearchArtistsRequest searchArtistsRequest = spotifyApiLight.searchArtists(q)
                .limit(4)
                .build();

            try {

                final Paging<Artist> artistPaging = searchArtistsRequest.execute();

                System.out.println("Total: " + artistPaging.getTotal());
                System.out.println(q);
                return artistPaging.getItems();

            } catch (IOException | SpotifyWebApiException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        return new Artist[0];
    }

    @GetMapping(value = "searchTrack/{q}")
    public static Track[] getSongSearch(@PathVariable("q") String q) {
        // track%3AThis%20is%20Why%20artist%3AParamore

        clientCredentials_Sync();
        final SearchTracksRequest searchTracksRequest = spotifyApiLight.searchTracks(q)
                .limit(1)
                .build();

        try {
            System.out.println("searchTracksRequest: " + searchTracksRequest);
            final Paging<Track> trackPaging = searchTracksRequest.execute();

            System.out.println("TrackId: " + Arrays.toString(trackPaging.getItems()));
            System.out.println("Total: " + trackPaging.getTotal());
            return trackPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(new Track[0]);
        return new Track[0];
    }
}
