package com.project.RealEstateRental.controllers;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PicturesBody {
    private String isThumbInNew;
    private String thumbnailPhoto;
    private String[] deletedPhotos;
    private MultipartFile[] newImages;

    public PicturesBody(String isThumbInNew,String thumbnailPhoto, String[] deletedPhotos, MultipartFile[] newImages) {
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
