package com.example.exoplayer_glide.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    private List<MusicModel> playMusicList = new ArrayList<>();
    private int currentPosition = -1;
    private boolean isWatchingPlayListView = true;

    public PlayerModel() {
    }

    public PlayerModel(List<MusicModel> playMusicList, int currentPosition, boolean isWatchingPlayListView) {
        this.playMusicList = playMusicList;
        this.currentPosition = currentPosition;
        this.isWatchingPlayListView = isWatchingPlayListView;
    }

    public PlayerModel(List<MusicModel> playMusicList) {
        this.playMusicList = playMusicList;
        this.currentPosition = -1;
        this.isWatchingPlayListView = true;
    }

    public List<MusicModel> getAdapterModels() {
        List<MusicModel> adapterModels = new ArrayList<>();
        for (int index = 0; index < playMusicList.size(); index++) {
            MusicModel musicModel = playMusicList.get(index);
            boolean isPlaying = index == currentPosition;

            //이부분에서 객체를 한번 더 생성 하면서 오류 발생
            adapterModels.add(musicModel);  //mapper에 담은 리스트를 그대로 가져와야함 -> 그래야 같은 값

//            MusicModel newItem = new MusicModel(
//                    musicModel.getId(),
//                    musicModel.getTrack(),
//                    musicModel.getStreamUrl(),
//                    musicModel.getArtist(),
//                    musicModel.getCoverUrl(),
//                    isPlaying
//            );
//            adapterModels.add(newItem);
        }
        return adapterModels;
    }

    public void updateCurrentPosition(MusicModel musicModel) {
         currentPosition = playMusicList.indexOf(musicModel);

//        for (int i = 0; i < playMusicList.size(); i++) {
//            if (playMusicList.get(i).getId() == musicModel.getId()) {
//                currentPosition = i;
//                return;
//            }
//        }


    }

    public MusicModel nextMusic() {
        if (playMusicList.isEmpty()) return null;

        currentPosition = (currentPosition + 1) == playMusicList.size() ? 0 : currentPosition + 1;

        return playMusicList.get(currentPosition);
    }

    public MusicModel prevMusic() {
        if (playMusicList.isEmpty()) return null;

        currentPosition = (currentPosition - 1) < 0 ? playMusicList.size() - 1 : currentPosition - 1;

        return playMusicList.get(currentPosition);
    }

    public MusicModel currentMusicModel() {
        if (playMusicList.isEmpty()) return null;

        return playMusicList.get(currentPosition);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
