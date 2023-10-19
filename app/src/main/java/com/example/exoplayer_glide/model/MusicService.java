package com.example.exoplayer_glide.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicService {
    @GET("/api/music")
    Call<MusicDto> listMusics();
}
