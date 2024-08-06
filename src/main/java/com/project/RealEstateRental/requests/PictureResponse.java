package com.project.RealEstateRental.requests;

public class PictureResponse {
    private String pictureUrl;
    private String pictureName;

    public PictureResponse() {
    }

    public PictureResponse(String pictureUrl, String pictureName) {
        this.pictureUrl = pictureUrl;
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}

