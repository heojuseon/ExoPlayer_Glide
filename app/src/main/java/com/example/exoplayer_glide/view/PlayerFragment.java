package com.example.exoplayer_glide.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exoplayer_glide.MusicModelMapper;
import com.example.exoplayer_glide.R;
import com.example.exoplayer_glide.adapter.MusicAdapter;
import com.example.exoplayer_glide.model.MusicDto;
import com.example.exoplayer_glide.model.MusicModel;
import com.example.exoplayer_glide.model.MusicService;
import com.example.exoplayer_glide.model.PlayerModel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlayerFragment extends Fragment {
    private MusicAdapter adapter;
    private PlayerModel model = new PlayerModel();
    private ExoPlayer player;


    ImageView playControlImageView, coverImageView, skipNextImageView, skipPrevImageView, playListImageView;
    TextView trackTextView, artistTextView, playTimeTextView, totalTimeTextView;
    PlayerView playerView;
    RecyclerView playListRecyclerView;
    SeekBar playListSeekBar, playerSeekBar;
    Group playListGroup, playerViewGroup;


    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Runnable updateSeekRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeek();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        playerView = view.findViewById(R.id.player_view);
        playListRecyclerView = view.findViewById(R.id.play_list_recycler_view);
        playControlImageView = view.findViewById(R.id.play_control_image_view);
        coverImageView = view.findViewById(R.id.cover_image_view);
        skipNextImageView = view.findViewById(R.id.skip_next_image_view);
        skipPrevImageView = view.findViewById(R.id.skip_prev_image_view);
        playListImageView  = view.findViewById(R.id.play_list_image_view);
        trackTextView = view.findViewById(R.id.track_text_view);
        artistTextView = view.findViewById(R.id.artist_text_view);
        playListSeekBar = view.findViewById(R.id.play_list_seek_bar);
        playerSeekBar = view.findViewById(R.id.player_seek_bar);
        playTimeTextView = view.findViewById(R.id.play_time_text_view);
        totalTimeTextView = view.findViewById(R.id.total_time_text_view);
        playListGroup = view.findViewById(R.id.play_list_group);
        playerViewGroup = view.findViewById(R.id.player_view_group);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPlayView();
        initPlayListButton();
        initPlayControlButtons();
        initSeekBar();
        initRecyclerView();
        getVideoListFromServer();
    }



    private void initSeekBar() {

        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null) {
                    player.seekTo(seekBar.getProgress() * 1000L);
                }
            }
        });
    }

    private void initPlayControlButtons() {
        playControlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    if (player.isPlaying()) {
                        player.pause();
                    } else {
                        player.play();
                    }
                }
            }
        });
        skipNextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicModel nextMusic = model.nextMusic();
                if (nextMusic != null) {
                    playMusic(nextMusic);
                }
            }
        });
        skipPrevImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicModel prevMusic = model.prevMusic();
                if (prevMusic != null) {
                    playMusic(prevMusic);
                }
            }
        });
    }


    private void initPlayView() {
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);
        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying){
                    playControlImageView.setImageResource(R.drawable.ic_baseline_pause_48);
                }else {
                    playControlImageView.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
            }

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                if (mediaItem == null) return;
                int newIndex = Integer.parseInt(mediaItem.mediaId);
                model.setCurrentPosition(newIndex);
                adapter.submitList(model.getAdapterModels());
                playListRecyclerView.scrollToPosition(model.getCurrentPosition());
                updatePlayerView(model.currentMusicModel());
            }

            @Override
            public void onPlaybackStateChanged(int state) {
//                Player.Listener.super.onPlaybackStateChanged(playbackState);
                updateSeek();
            }
        });
    }

    private void updateSeek() {
        if (player == null) return;
        long duration = player.getDuration() >= 0 ? player.getDuration() : 0;
        long position = player.getCurrentPosition();
        updateSeekUi(duration, position);
        int state = player.getPlaybackState();
        mainHandler.removeCallbacks(updateSeekRunnable);
        if (state != Player.STATE_IDLE && state != Player.STATE_ENDED) {
            mainHandler.postDelayed(updateSeekRunnable, 1000);
        }
    }

    private void updateSeekUi(long duration, long position) {
        playListSeekBar.setMax((int) (duration / 1000));
        playListSeekBar.setProgress((int) (position / 1000));
        playerSeekBar.setMax((int) (duration / 1000));
        playerSeekBar.setProgress((int) (position / 1000));
        @SuppressLint("DefaultLocale") String playTimeText = String.format("%02d:%02d", TimeUnit.MINUTES.convert(position, TimeUnit.MILLISECONDS), (position / 1000) % 60);
        @SuppressLint("DefaultLocale") String totalTimeText = String.format("%02d:%02d", TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS), (duration / 1000) % 60);
        playTimeTextView.setText(playTimeText);
        totalTimeTextView.setText(totalTimeText);
    }

    private void updatePlayerView(MusicModel currentMusicModel) {
        if (currentMusicModel == null) return;
        trackTextView.setText(currentMusicModel.getTrack());
        artistTextView.setText(currentMusicModel.getArtist());
        Glide.with(coverImageView.getContext())
                .load(currentMusicModel.getCoverUrl())
                .into(coverImageView);
    }

    private void initRecyclerView() {
        adapter = new MusicAdapter(new MusicAdapter.OnMusicItemClickListener() {
            @Override
            public void onMusicItemClicked(MusicModel music) {
                playMusic(music);
            }
        });
        playListRecyclerView.setAdapter(adapter);
        playListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void playMusic(MusicModel music) {
        model.updateCurrentPosition(music);
        if (player != null) {
            Log.d("@!@", ""+model.getCurrentPosition());
            player.seekTo(model.getCurrentPosition(), 0);
            player.play();
        }
    }

    private void initPlayListButton() {
        playListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getCurrentPosition() == -1) return;

                int playListGroupVisibility = playListGroup.getVisibility();
                int playerViewGroupVisibility = playerViewGroup.getVisibility();

                if (playListGroupVisibility == View.VISIBLE) {
                    playListGroup.setVisibility(View.INVISIBLE);
                    playerViewGroup.setVisibility(View.VISIBLE);
                } else {
                    playListGroup.setVisibility(View.VISIBLE);
                    playerViewGroup.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getVideoListFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://80d6e5ae-659a-46e6-9247-9b5248706bbc.mock.pstmn.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MusicService musicService = retrofit.create(MusicService.class);
        Call<MusicDto> call = musicService.listMusics();
        call.enqueue(new Callback<MusicDto>() {
            @Override
            public void onResponse(Call<MusicDto> call, Response<MusicDto> response) {
                Log.d("PlayerFragment", String.valueOf(response.body()));
                Log.d("PlayerFragment", "API Success");
                MusicDto musicDto = response.body();
                if (musicDto != null) {
//                    model = musicDto.mapper();
                    model = MusicModelMapper.mapper(musicDto);
                    setMusicList(model.getAdapterModels());
                    adapter.submitList(model.getAdapterModels());
                }
            }

            @Override
            public void onFailure(Call<MusicDto> call, Throwable t) {
                // Handle failure here
            }
        });
    }

    private void setMusicList(List<MusicModel> modelList) {

        if (player != null) {
            MediaItem[] mediaItems = new MediaItem[modelList.size()];
            for (int i = 0; i < modelList.size(); i++) {
                MediaItem mediaItem = new MediaItem.Builder()
                        .setMediaId(String.valueOf(modelList.get(i).getId()))
                        .setUri(modelList.get(i).getStreamUrl())
                        .build();
                mediaItems[i] = mediaItem;
            }
            player.addMediaItems(Arrays.asList(mediaItems));
            player.prepare();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.pause();
            mainHandler.removeCallbacks(updateSeekRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            mainHandler.removeCallbacks(updateSeekRunnable);
        }
    }


}