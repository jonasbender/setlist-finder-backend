package com.jb.spotifybackend.controller;


import com.jb.spotifybackend.model.Song;
import com.jb.spotifybackend.model.SpotifySong;
import com.jb.spotifybackend.setlistfmcontroller.SetlistApiController;
import com.jb.spotifybackend.spotifycontroller.SpotifyApiController;
import com.wrapper.spotify.model_objects.specification.Track;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaylistImageController {

    public static ResponseEntity<byte[]> getPlaylistImage(String imageUrl, String artistName, String venue, String date) throws Exception {

        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();
            System.out.println("height: " + imageHeight);
            System.out.println("width: " + imageWidth);

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.WHITE);

            Font IBMPlex = Font.createFont(Font.TRUETYPE_FONT, PlaylistImageController.class.getResourceAsStream("/Fonts/IBMPlexSans-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(IBMPlex);

            int maxWidth = imageWidth - 20;
            int fontSize = calculateFontSize(artistName);
            int venueSize = calculateVenueFontSize(venue);


            //System.out.println("Font size: "+  fontSize);
            Font fontArtist = IBMPlex.deriveFont(Font.BOLD, fontSize);
            Font fontVenue = IBMPlex.deriveFont(Font.BOLD, venueSize);
            Font fontDate = IBMPlex.deriveFont(Font.BOLD, 30);


            // Calculate the position for the text overlay
            int x = 20; // Adjust the x-coordinate as needed
            int y = image.getHeight() - 20; // Adjust the y-coordinate as needed

            // Draw the text overlay
            graphics.setFont(fontArtist);
            graphics.drawString(artistName, x, y - 300);
            graphics.setFont(fontVenue);
            graphics.drawString(venue, x, y - 200);
            graphics.setFont(fontDate);
            graphics.drawString(date, x, y - 150);

            graphics.dispose();

            //String outputFilePath = "/Users/jonas/Downloads/modified_image.png";

            //ImageIO.write(image, "png", new File(outputFilePath));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Return the modified image as the response body
            return ResponseEntity.ok().body(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public static int calculateFontSize(String artistName) {
        int fontSize = 90;
        int stringSize = artistName.length();
        System.out.println("Länge: " + stringSize);
        if (stringSize < 8 ) {
            fontSize = 100;
        } else if (stringSize > 10 && stringSize <= 15) {
            fontSize = 70;
        }  if (stringSize > 15) {
            fontSize = 60;
        }

        System.out.println("font size: " + fontSize);
        return fontSize;
    };

    public static int calculateVenueFontSize(String venueName) {
        int fontSize = 40;
        int stringSize = venueName.length();
        System.out.println("Länge Venue: " + stringSize);
        if (stringSize > 25) {
            fontSize = 35;
        } if (stringSize > 35) {
            fontSize = 30;
        }
        return fontSize;
    }


    public static void main(String[] args) throws Exception {
        String imageUrl = "https://i.scdn.co/image/ab6761610000e5ebb10c34546a4ca2d7faeb8865";
        //String artistName = "Paramoremm";
        String artistName = "Jesse";
        String venue = "The Colosseum at Caesars Palace";
        String date = "2023-05-30";

        ResponseEntity<byte[]> modifiedImage = getPlaylistImage(imageUrl, artistName, venue, date);
        System.out.println(modifiedImage);
    }

};




