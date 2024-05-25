package com.example.movieapp.model;

import java.util.List;
public class Movie {
    private String _id;
    private String name;
    private String slug;
    private String origin_name;
    private String content;
    private String type;
    private String status;
    private String poster_url;
    private String thumb_url;
    private boolean is_copyright;
    private boolean sub_docquyen;
    private boolean chieurap;
    private String trailer_url;
    private String time;
    private String episode_current;
    private String episode_total;
    private String quality;
    private String lang;
    private String notify;
    private String showtimes;
    private int year;
    private int view;
    private List<String> actor;
    private List<String> director;
    private List<Category> category;
    private List<Country> country;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public boolean isIs_copyright() {
        return is_copyright;
    }

    public void setIs_copyright(boolean is_copyright) {
        this.is_copyright = is_copyright;
    }

    public boolean isSub_docquyen() {
        return sub_docquyen;
    }

    public void setSub_docquyen(boolean sub_docquyen) {
        this.sub_docquyen = sub_docquyen;
    }

    public boolean isChieurap() {
        return chieurap;
    }

    public void setChieurap(boolean chieurap) {
        this.chieurap = chieurap;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEpisode_current() {
        return episode_current;
    }

    public void setEpisode_current(String episode_current) {
        this.episode_current = episode_current;
    }

    public String getEpisode_total() {
        return episode_total;
    }

    public void setEpisode_total(String episode_total) {
        this.episode_total = episode_total;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(String showtimes) {
        this.showtimes = showtimes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }
}
