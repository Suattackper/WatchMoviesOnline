package com.example.movieapp.model;

import java.util.List;

public class Episode {
    private String server_name;
    private List<ServerData> server_data;

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public List<ServerData> getServer_data() {
        return server_data;
    }

    public void setServer_data(List<ServerData> server_data) {
        this.server_data = server_data;
    }
}
