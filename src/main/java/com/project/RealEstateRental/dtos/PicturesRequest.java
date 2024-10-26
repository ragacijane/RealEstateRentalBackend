package com.project.RealEstateRental.dtos;

import org.springframework.web.multipart.MultipartFile;

public class PicturesRequest {
    private String[] deletedPhotos;
    private MultipartFile[] newImages;
    private String[] sequenceArray;

    public PicturesRequest(String[] deletedPhotos, MultipartFile[] newImages, String[] sequenceArray) {
        this.deletedPhotos = deletedPhotos;
        this.newImages = newImages;
        this.sequenceArray = sequenceArray;
    }

    public String[] getDeletedPhotos() {
        return deletedPhotos;
    }

    public MultipartFile[] getNewImages() {
        return newImages;
    }

    public String[] getSequenceArray() {
        return sequenceArray;
    }
}
