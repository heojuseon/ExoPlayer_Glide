package com.example.exoplayer_glide.model;

import java.util.List;

public class MusicDto {
    public List<MusicEntity> musics;

    public MusicDto(List<MusicEntity> musics) {
        this.musics = musics;
    }

    public List<MusicEntity> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicEntity> musics) {
        this.musics = musics;
    }
}
