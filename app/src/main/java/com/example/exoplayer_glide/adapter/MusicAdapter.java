package com.example.exoplayer_glide.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exoplayer_glide.R;
import com.example.exoplayer_glide.model.MusicModel;

public class MusicAdapter extends ListAdapter<MusicModel, MusicAdapter.ViewHolder> {
    private final OnMusicItemClickListener callback;


    public interface OnMusicItemClickListener {
        void onMusicItemClicked(MusicModel music);
    }

    public MusicAdapter(OnMusicItemClickListener callback) {
        super(diffUtil);
        this.callback = callback;
    }

    public final class ViewHolder extends RecyclerView.ViewHolder{
        private TextView track, artist;
        private ImageView coverImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            track = itemView.findViewById(R.id.item_track_text_view);
            artist = itemView.findViewById(R.id.item_artist_text_view);
            coverImg = itemView.findViewById(R.id.item_cover_image_view);
        }

        public void bind(MusicModel music) {
            Log.d("!@!@!@", ""+music.artist + ", " +music.hashCode());

            track.setText(music.getTrack());
            artist.setText(music.getArtist());

            //Glide 사용(이미지)
            Glide.with(coverImg.getContext())
                    .load(music.getCoverUrl())
                    .into(coverImg);

            if (music.isPlaying()) {
                itemView.setBackgroundColor(Color.GRAY);
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onMusicItemClicked(music);
                }
            });
        }
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    private static final DiffUtil.ItemCallback<MusicModel> diffUtil = new DiffUtil.ItemCallback<MusicModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MusicModel oldItem, @NonNull MusicModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MusicModel oldItem, @NonNull MusicModel newItem) {
            return oldItem.equals(newItem);
        }
    };

}
