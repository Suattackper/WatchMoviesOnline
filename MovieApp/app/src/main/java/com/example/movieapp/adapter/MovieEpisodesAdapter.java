package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.PlayMovieActivity;
import com.example.movieapp.R;
import com.example.movieapp.fragment.MovieDetailFragment;
import com.example.movieapp.fragment.MoviePlayingFragment;
import com.example.movieapp.model.Episode;
import com.example.movieapp.model.Item;
import com.example.movieapp.model.ServerData;

import java.util.List;

public class MovieEpisodesAdapter extends RecyclerView.Adapter<MovieEpisodesAdapter.MovieEpisodesAdapterHolder> {
    List<ServerData> episodeList;
    Context context;

    public MovieEpisodesAdapter(List<ServerData> episodeList, Context context) {
        this.episodeList = episodeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieEpisodesAdapter.MovieEpisodesAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_episode_item, parent, false);
        return new MovieEpisodesAdapter.MovieEpisodesAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieEpisodesAdapter.MovieEpisodesAdapterHolder holder, int position) {
        ServerData item = episodeList.get(position);

        holder.tvTapPhim.setText(String.valueOf(position+1));
        holder.btnTapPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MoviePlayingFragment fragment = new MoviePlayingFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("m3u8", item.getLink_m3u8());
//                fragment.setArguments(bundle);
//
//                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.frameLayout, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);

                Intent intent = new Intent(context, PlayMovieActivity.class);
                intent.putExtra("m3u8", item.getLink_m3u8());
                context.startActivity(intent);

            }
        });
        holder.btnTaiTapPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class MovieEpisodesAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvTapPhim;
        LinearLayout btnTapPhim;
        LinearLayout btnTaiTapPhim;
        public MovieEpisodesAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvTapPhim = itemView.findViewById(R.id.tvTapPhim);
            btnTapPhim = itemView.findViewById(R.id.btnTapPhim);
            btnTaiTapPhim = itemView.findViewById(R.id.btnTaiTapPhim);
        }
    }
}
