package com.example.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.MovieImageItem;

import java.util.List;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder> {
    public MovieItemAdapter(List<MovieImageItem> movieImageItemList) {
        this.movieImageItemList = movieImageItemList;
    }

    List<MovieImageItem> movieImageItemList;
    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image_item, parent, false);

        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        MovieImageItem movieImageItem = movieImageItemList.get(position);
        holder.imvMovie.setImageResource(movieImageItem.getImvPhoto());
    }

    @Override
    public int getItemCount() {
        return movieImageItemList.size();
    }

    public static class MovieItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imvMovie;
        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imvMovie = itemView.findViewById(R.id.imvMovieItem);
        }
    }
}
