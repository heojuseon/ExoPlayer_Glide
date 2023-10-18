package com.example.exoplayer_glide.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicService {
    @GET("/v3/2a42c882-2455-4615-8d4a-b4470777716d")
    Call<MusicDto> listMusics();
}
