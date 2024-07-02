package com.project.RealEstateRental.services;

import com.project.RealEstateRental.controllers.PicturesBody;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PicturesService {
    private final PicturesRepository picturesRepository;
    private final S3Service s3Service;

    @Autowired
    public PicturesService(PicturesRepository picturesRepository, S3Service s3Service) {
        this.picturesRepository = picturesRepository;
        this.s3Service = s3Service;
    }
    public List<Pictures> getPicturesByProperty(Properties property){
        return picturesRepository.findByProperty(property);
    }
    public List<String> getImages(Properties property) {
        List<Pictures> pictures = picturesRepository.findByProperty(property);
        return pictures.stream()
                .map(picture -> s3Service.generatePresignedUrl(picture.getPicturePath()).toString())
                .collect(Collectors.toList());
    }

    public String getThumbnail(String thumbnailName){
        String thumbnailPath="thumbnails/"+thumbnailName;
        return s3Service.generatePresignedUrl(thumbnailPath.toString()).toString();
    }

    public String updatePicturesToProperty(PicturesBody picturesBody, Properties property) throws IOException {
        List<Pictures> existingPictures = getPicturesByProperty(property);
        if (picturesBody.getDeletedPhotos() != null && picturesBody.getDeletedPhotos().length > 0) {
            deletePictures(picturesBody.getDeletedPhotos(), existingPictures);
        }

        boolean isThumbnailSet = false;
        if (!picturesBody.isThumbInNew()) {
            isThumbnailSet = true;
            handleExistingThumbnail(picturesBody, property);
        }

        if (picturesBody.getNewImages() != null && picturesBody.getNewImages().length > 0) {
            saveNewImages(picturesBody, property, isThumbnailSet);
        }

        return picturesBody.getThumbnailPhoto();
    }
    @Transactional
    void deletePictures(String[] deletePhotos, List<Pictures> existingPictures) throws IOException {
        for (String picToDelete : deletePhotos) {
            for (Pictures picture : existingPictures) {
                if (picToDelete.equals(picture.getPictureName())) {
                    s3Service.deleteFile(picture.getPicturePath());
                    picturesRepository.delete(picture);
                    break;
                }
            }
        }
    }

    @Transactional
    void deleteIfExistThumbnail(String thumbnail) throws IOException {
        if (thumbnail.length() > 13) {
            s3Service.deleteFile("thumbnails/" + thumbnail);
        }
    }

    @Transactional
    void handleExistingThumbnail(PicturesBody picturesBody, Properties property) throws IOException {
        if (!picturesBody.getThumbnailPhoto().equals(property.getThumbnail())) {
            deleteIfExistThumbnail(property.getThumbnail());
            if (picturesBody.getThumbnailPhoto().length() > 13) {
                copyThumbnail(picturesBody.getThumbnailPhoto(), property);
            } else {
                System.out.println("Invalid picture name on copying thumbnail: " + picturesBody.getThumbnailPhoto());
            }
        }
    }

    @Transactional
    void copyThumbnail(String thumbnail, Properties property) {
        String sourcePath = property.getIdProperty() + "/" + thumbnail;
        String thumbnailPath = "thumbnails/" + thumbnail;
        s3Service.copyFile(sourcePath, thumbnailPath);
    }

    @Transactional
    boolean handleNewThumbnail(PicturesBody picturesBody, Properties property, MultipartFile image, String picName) throws IOException {
        if (picturesBody.getThumbnailPhoto().equals(image.getOriginalFilename())) {
            deleteIfExistThumbnail(property.getThumbnail());
            picturesBody.setThumbnailPhoto(picName);
            copyThumbnail(picturesBody.getThumbnailPhoto(), property);
            return true;
        }
        return false;
    }

    @Transactional
    void saveNewImages(PicturesBody picturesBody, Properties property, boolean incomingIsThumbnailSet) throws IOException {
        boolean isThumbnailSet = incomingIsThumbnailSet;

        for (MultipartFile image : picturesBody.getNewImages()) {
            Pictures newPicture = picturesRepository.save(new Pictures("temp", "temp", property));

            String picName = property.getIdProperty() + "_picture_" + newPicture.getId() + ".jpeg";
            String picturePath = property.getIdProperty() + "/" + picName;

            newPicture.setPictureName(picName);
            newPicture.setPicturePath(picturePath);
            picturesRepository.save(newPicture);

            s3Service.uploadFile(image, picturePath);

            if (!isThumbnailSet) {
                isThumbnailSet = handleNewThumbnail(picturesBody, property, image, picName);
            }
        }
    }
}
