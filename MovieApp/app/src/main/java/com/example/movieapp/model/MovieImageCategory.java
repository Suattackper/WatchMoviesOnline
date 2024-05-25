package com.example.movieapp.model;

import java.util.List;

public class MovieImageCategory {
    private String title;
    private List<MovieImageItem> lstImvMovie;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieImageItem> getLstImvMovie() {
        return lstImvMovie;
    }

    public void setLstImvMovie(List<MovieImageItem> lstImvMovie) {
        this.lstImvMovie = lstImvMovie;
    }

    public MovieImageCategory(String title, List<MovieImageItem> lstImvMovie) {
        this.title = title;
        this.lstImvMovie = lstImvMovie;
    }
}
