package com.example.movieapp.model;

public class AccountList {
    private String Name;
    private String Slug;
    private String ThumbUrl;

    public AccountList() {
    }

    public AccountList(String name, String slug, String thumbUrl) {
        Name = name;
        Slug = slug;
        ThumbUrl = thumbUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public String getThumbUrl() {
        return ThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        ThumbUrl = thumbUrl;
    }
}
