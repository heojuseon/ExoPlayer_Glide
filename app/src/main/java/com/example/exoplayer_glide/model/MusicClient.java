//package com.example.exoplayer_glide.model;
//
//import android.util.Log;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class MusicClient {
//   public static Retrofit retrofit;
//   private static MusicClient instance;
//   private MusicService musicService;
//
//
//    public static MusicClient getInstance() {
//        if (instance == null) {
//            instance = new MusicClient();
//        }
//        Log.d("MusicClient", "MusicClient : " + instance);
//        return instance;
//    }
//
//
//    public void getVideoListFromServer(Callback<MusicDto> musicDtoCallback) {
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://run.mocky.io/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        musicService = retrofit.create(MusicService.class);
//
//        setVideoListFromServer(musicDtoCallback);
//    }
//
//    private void setVideoListFromServer(Callback<MusicDto> musicDtoCallback) {
//        //응답을 받아오는 부분
//        Call<MusicDto> call = musicService.listMusics();
//        call.enqueue(musicDtoCallback);
//    }
//}
