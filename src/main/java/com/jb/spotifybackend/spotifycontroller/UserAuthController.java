package com.jb.spotifybackend.spotifycontroller;


import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jb.spotifybackend.utils.KeyStore;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
public class UserAuthController {



    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(KeyStore.CLIENT_ID)
            .setClientSecret(KeyStore.CLIENT_SECRET)
            .setRedirectUri(KeyStore.redirectUri)
            .build();



    @GetMapping("login")
    @ResponseBody
    public String spotifyLogin(@RequestParam("lastViewedUrl") String lastViewedUrl, HttpServletRequest request) throws UnsupportedEncodingException {


        System.out.println("lastViewedUrl: " + lastViewedUrl);
        String state = Base64.getEncoder().encodeToString(lastViewedUrl.getBytes());

        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-private, user-read-email, playlist-modify-private, playlist-modify-public")
                .show_dialog(true)
                .state(state)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println(uri);
        return uri.toString();
    }

    @GetMapping(value = "callback")
    public String getSpotifyUserCode(@RequestParam("code") String userCode,@RequestParam("state") String state, HttpServletResponse response, HttpServletRequest request) throws IOException {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(userCode)
                .build();

        String accessToken = "";
        String refreshToken = "";

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            //set access and refresh token for further spotifyApi usage

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            System.out.println("Access Token: " + authorizationCodeCredentials.getAccessToken());
            System.out.println("Refresh Token: " + authorizationCodeCredentials.getRefreshToken());
            accessToken = authorizationCodeCredentials.getAccessToken();
            refreshToken = authorizationCodeCredentials.getRefreshToken();


        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error " + e.getMessage());
        }

        byte[] decodedState = Base64.getDecoder().decode(state);
        String lastViewedUrl = new String(decodedState);

        Pattern pattern = Pattern.compile("^(.*?)(?=&access_token=)");
        Matcher matcher = pattern.matcher(lastViewedUrl);

        if (matcher.find()) {
            String modifiedUrl = matcher.group(1);
            System.out.println("Modified URL: " + modifiedUrl);
            lastViewedUrl = modifiedUrl + "&access_token=" + accessToken + "&refresh_token=" + refreshToken;
        } else {
            lastViewedUrl = lastViewedUrl + "&access_token=" + accessToken + "&refresh_token=" + refreshToken;
        }
        System.out.println("access Token: " + accessToken);
        System.out.println("lastViewedUrl: " + lastViewedUrl);


        System.out.println("Last viewed Url: " + lastViewedUrl);

        response.sendRedirect(lastViewedUrl);

        return spotifyApi.getAccessToken();

    }




}
