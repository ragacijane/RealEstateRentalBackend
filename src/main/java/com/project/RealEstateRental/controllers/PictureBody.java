package com.project.RealEstateRental.controllers;

public class PictureBody {
    private String name;
    private String url;

    public PictureBody() {
    }

    public PictureBody(String name, String url) {
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
