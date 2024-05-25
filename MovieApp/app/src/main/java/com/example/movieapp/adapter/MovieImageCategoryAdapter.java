package com.example.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.MovieImageCategory;

import java.util.List;

public class MovieImageCategoryAdapter extends RecyclerView.Adapter<MovieImageCategoryAdapter.MovieImageCategoryViewHolder>{

    List<MovieImageCategory> movieImageCategoryList;
    Context context;

    public MovieImageCategoryAdapter(Context context, List<MovieImageCategory> movieImageCategoryList) {
        this.context = context;
        this.movieImageCategoryList = movieImageCategoryList;
    }

    @NonNull
    @Override
    public MovieImageCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_title_category, parent, false);

        return new MovieImageCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieImageCategoryViewHolder holder, int position) {
        MovieImageCategory movieImageCategory = movieImageCategoryList.get(position);
        holder.tvCategoryTitle.setText(movieImageCategory.getTitle());

        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvMovieList.setLayoutManager(manager);

        MovieItemAdapter movieItemAdapter = new MovieItemAdapter(movieImageCategory.getLstImvMovie());
        holder.rcvMovieList.setAdapter(movieItemAdapter);
    }

    @Override
    public int getItemCount() {
        return movieImageCategoryList.size();
    }

    public static class MovieImageCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryTitle;
        RecyclerView rcvMovieList;
        public MovieImageCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryTitle = itemView.findViewById(R.id.tvItemCate);
            rcvMovieList = itemView.findViewById(R.id.rcvMovieCateList);
        }
    }
}
