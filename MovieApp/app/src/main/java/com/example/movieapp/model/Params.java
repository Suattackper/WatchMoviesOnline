package com.example.movieapp.model;

import java.util.List;

public class Params {
    private String type_slug;
    private String keyword;
    private List<String> filterCategory;
    private List<String> filterCountry;
    private String filterYear;
    private String filterType;
    private String sortField;
    private String sortType;
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getType_slug() {
        return type_slug;
    }

    public void setType_slug(String type_slug) {
        this.type_slug = type_slug;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(List<String> filterCategory) {
        this.filterCategory = filterCategory;
    }

    public List<String> getFilterCountry() {
        return filterCountry;
    }

    public void setFilterCountry(List<String> filterCountry) {
        this.filterCountry = filterCountry;
    }

    public String getFilterYear() {
        return filterYear;
    }

    public void setFilterYear(String filterYear) {
        this.filterYear = filterYear;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
