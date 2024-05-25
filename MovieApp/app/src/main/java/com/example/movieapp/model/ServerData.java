package com.example.movieapp.model;

public class ServerData {
    private String name;
    private String slug;
    private String filename;
    private String link_embed;
    private String link_m3u8;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLink_embed() {
        return link_embed;
    }

    public void setLink_embed(String link_embed) {
        this.link_embed = link_embed;
    }

    public String getLink_m3u8() {
        return link_m3u8;
    }

    public void setLink_m3u8(String link_m3u8) {
        this.link_m3u8 = link_m3u8;
    }
}
