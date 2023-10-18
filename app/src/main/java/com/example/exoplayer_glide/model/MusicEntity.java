package com.example.exoplayer_glide.model;

import com.google.gson.annotations.SerializedName;

public class MusicEntity {
    private long id;
    @SerializedName("track")
    private String track;

    @SerializedName("streamUrl")
    private String streamUrl;

    @SerializedName("artist")
    private String artist;

    @SerializedName("coverUrl")
    private String coverUrl;
    private boolean isPlaying;  //현재 재생 되고 있는지 상태를 나타냄

    public MusicEntity(long id, String track, String streamUrl, String artist, String coverUrl, boolean isPlaying) {
        this.id = id;
        this.track = track;
        this.streamUrl = streamUrl;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.isPlaying = isPlaying;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

}
