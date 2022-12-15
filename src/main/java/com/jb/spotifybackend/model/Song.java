package com.jb.spotifybackend.model;

public class Song {

    private String songTitle;
    private String trackId;
    private String trackImageURL;
    private String artist;
    private String album;
    private String coverArtist;

    public Song(String songTitle, String trackId, String trackImageURL, String artist, String album, String coverArtist) {
        this.songTitle = songTitle;
        this.trackId = trackId;
        this.trackImageURL = trackImageURL;
        this.artist = artist;
        this.album = album;
        this.coverArtist = coverArtist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackImageURL() {
        return trackImageURL;
    }

    public void setTrackImageURL(String trackImageURL) {
        this.trackImageURL = trackImageURL;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCoverArtist() {
        return coverArtist;
    }

    public void setCoverArtist(String coverArtist) {
        this.coverArtist = coverArtist;
    }
}
