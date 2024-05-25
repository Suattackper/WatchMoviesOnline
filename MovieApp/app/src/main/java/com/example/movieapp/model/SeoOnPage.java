package com.example.movieapp.model;

import java.util.List;

public class SeoOnPage {
    private String og_type;
    private String titleHead;
    private String descriptionHead;
    private List<String> og_image;
    private String og_url;

    public String getOg_type() {
        return og_type;
    }

    public void setOg_type(String og_type) {
        this.og_type = og_type;
    }

    public String getTitleHead() {
        return titleHead;
    }

    public void setTitleHead(String titleHead) {
        this.titleHead = titleHead;
    }

    public String getDescriptionHead() {
        return descriptionHead;
    }

    public void setDescriptionHead(String descriptionHead) {
        this.descriptionHead = descriptionHead;
    }

    public List<String> getOg_image() {
        return og_image;
    }

    public void setOg_image(List<String> og_image) {
        this.og_image = og_image;
    }

    public String getOg_url() {
        return og_url;
    }

    public void setOg_url(String og_url) {
        this.og_url = og_url;
    }
}
