package com.jb.spotifybackend.model;

import java.util.List;

public class PlaylistRequest {

    public String accessToken;
    private List<String> trackIds;
    private String playlistImageUrl;
    private String playlistName;
    private boolean isPrivate;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(List<String> trackIds) {
        this.trackIds = trackIds;
    }

    public String getPlaylistImageUrl() {
        return playlistImageUrl;
    }

    public void setPlaylistImageUrl(String playlistImageUrl) {
        this.playlistImageUrl = playlistImageUrl;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
