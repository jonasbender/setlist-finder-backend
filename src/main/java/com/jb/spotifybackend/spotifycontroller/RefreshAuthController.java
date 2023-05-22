package com.jb.spotifybackend.spotifycontroller;

import com.jb.spotifybackend.utils.KeyStore;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RefreshAuthController {

    @GetMapping(value = "updateTokens")
    public ResponseEntity<Map<String, String>> getRefreshedTokens (@RequestParam("refreshToken") String refreshToken) {
        System.out.println("Tokens are beeing updated");
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(KeyStore.CLIENT_ID)
                .setClientSecret(KeyStore.CLIENT_SECRET)
                .setRefreshToken(refreshToken)
                .build();

        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        Map<String, String> tokenResponse = new HashMap<>();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            tokenResponse.put("accessToken", authorizationCodeCredentials.getAccessToken());
            tokenResponse.put("refreshToken", authorizationCodeCredentials.getRefreshToken());

            System.out.println("new Access Token: " + authorizationCodeCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return ResponseEntity.ok().body(tokenResponse);
    }

}
