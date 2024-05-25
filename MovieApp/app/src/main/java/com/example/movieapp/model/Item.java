package com.example.movieapp.model;

import java.util.List;
public class Item {
    private Modified modified;
    private String _id;
    private String name;
    private String slug;
    private String origin_name;
    private String type;
    private String poster_url;
    private String thumb_url;
    private boolean sub_docquyen;
    private boolean chieurap;
    private String time;
    private String episode_current;
    private String quality;
    private String lang;
    private int year;
    private List<Category> category;
    private List<Country> country;

    public Modified getModified() {
        return modified;
    }

    public void setModified(Modified modified) {
        this.modified = modified;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
