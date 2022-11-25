package com.jb.spotifybackend.controller;


import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;


public class ServerAuthController {

    private static final String CLIENT_ID = "013cc9a435c7467492532478e1144d3c";
    private static final String CLIENT_SECRET = "746bc9dc80924d48804b351fcba66356";

    static final SpotifyApi spotifyApiLight = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApiLight.clientCredentials()
            .build();

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApiLight.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Access Token: " + spotifyApiLight.getAccessToken());
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
