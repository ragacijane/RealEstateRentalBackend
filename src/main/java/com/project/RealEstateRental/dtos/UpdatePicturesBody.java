package com.project.RealEstateRental.dtos;

import org.springframework.web.multipart.MultipartFile;

public class UpdatePicturesBody {
    private String isThumbInNew;
    private String thumbnailPhoto;
    private String[] deletedPhotos;
    private MultipartFile[] newImages;

    public UpdatePicturesBody(String isThumbInNew, String thumbnailPhoto, String[] deletedPhotos, MultipartFile[] newImages) {
        this.thumbnailPhoto = thumbnailPhoto;
        this.deletedPhotos = deletedPhotos;
        this.newImages = newImages;
        this.isThumbInNew=isThumbInNew;
    }
    public void setThumbnailPhoto(String str){
        thumbnailPhoto=str;
    }
    public String getThumbnailPhoto() {
        return thumbnailPhoto != null ? thumbnailPhoto : "";
    }

    public String[] getDeletedPhotos() {
        return deletedPhotos;
    }

    public MultipartFile[] getNewImages() {
        return newImages;
    }
    public void setIsThumbInNew(String str){isThumbInNew=str;}
    public boolean isThumbInNew() {
        return isThumbInNew.equals("true");
    }
}
