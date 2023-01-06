package com.jb.spotifybackend.model;

public class SpotifySong {

    private String trackName;
    private String trackId;
    private String trackImageURL;
    private String artist;
    private String album;
    private Boolean isCover;

    public SpotifySong(String trackName, String trackId, String trackImageURL, String artist, String album, Boolean isCover) {
        this.trackName = trackName;
        this.trackId = trackId;
        this.trackImageURL = trackImageURL;
        this.artist = artist;
        this.album = album;
        this.isCover = isCover;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
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

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        isCover = cover;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackName='" + trackName + '\'' +
                ", trackId='" + trackId + '\'' +
                ", trackImageURL='" + trackImageURL + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", isCover=" + isCover +
                '}';
    }
}
