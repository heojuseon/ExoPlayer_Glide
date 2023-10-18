package com.example.exoplayer_glide;

import com.example.exoplayer_glide.model.MusicDto;
import com.example.exoplayer_glide.model.MusicEntity;
import com.example.exoplayer_glide.model.MusicModel;
import com.example.exoplayer_glide.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class MusicModelMapper {
    // MusicEntity를 이용하여 MusicModel을 생성하고 반환하는 메서드
    public static final MusicModel mapper(MusicEntity musicEntity, long id) {
        return new MusicModel(id, musicEntity.getTrack(), musicEntity.getStreamUrl(), musicEntity.getArtist(), musicEntity.getCoverUrl(), false);
    }

    public static PlayerModel mapper(MusicDto musicDto) {

        List<MusicModel> musicModelList = new ArrayList<>();
        List<MusicEntity> musicEntities = musicDto.getMusics();

        for (int i = 0; i < musicEntities.size(); i++) {
            MusicEntity musicEntity = musicEntities.get(i);
            MusicModel musicModel = mapper(musicEntity, (long) i);
            musicModelList.add(musicModel);
        }
        return new PlayerModel(musicModelList);
    }
}
