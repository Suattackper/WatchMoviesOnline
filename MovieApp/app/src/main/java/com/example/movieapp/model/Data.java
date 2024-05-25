package com.example.movieapp.model;

import java.util.List;

public class Data {
    private SeoOnPage seoOnPage;
    private List<BreadCrumb> breadCrumb;
    private String titlePage;
    private List<Item> items;
    private Params params;
    private String type_list;
    private String APP_DOMAIN_FRONTEND;
    private String APP_DOMAIN_CDN_IMAGE;

    public SeoOnPage getSeoOnPage() {
        return seoOnPage;
    }

    public void setSeoOnPage(SeoOnPage seoOnPage) {
        this.seoOnPage = seoOnPage;
    }

    public List<BreadCrumb> getBreadCrumb() {
        return breadCrumb;
    }

    public void setBreadCrumb(List<BreadCrumb> breadCrumb) {
        this.breadCrumb = breadCrumb;
    }

    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getType_list() {
        return type_list;
    }

    public void setType_list(String type_list) {
        this.type_list = type_list;
    }

    public String getAPP_DOMAIN_FRONTEND() {
        return APP_DOMAIN_FRONTEND;
    }

    public void setAPP_DOMAIN_FRONTEND(String APP_DOMAIN_FRONTEND) {
        this.APP_DOMAIN_FRONTEND = APP_DOMAIN_FRONTEND;
    }

    public String getAPP_DOMAIN_CDN_IMAGE() {
        return APP_DOMAIN_CDN_IMAGE;
    }

    public void setAPP_DOMAIN_CDN_IMAGE(String APP_DOMAIN_CDN_IMAGE) {
        this.APP_DOMAIN_CDN_IMAGE = APP_DOMAIN_CDN_IMAGE;
    }
}
