package com.project.RealEstateRental.controller;

public class PictureRequest {
    private String name;
    private String url;

    public PictureRequest() {
    }

    public PictureRequest(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
