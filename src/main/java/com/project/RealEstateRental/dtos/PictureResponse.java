package com.project.RealEstateRental.dtos;

public class PictureResponse {
    private String pictureName;
    private String picturePath;
    private String thumbnailPath;

    // Constructor
    public PictureResponse(String pictureName, String picturePath, String thumbnailPath) {
        this.pictureName = pictureName;
        this.picturePath = picturePath;
        this.thumbnailPath = thumbnailPath;
    }

    // Getters
    public String getPictureName() {
        return pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }
}
