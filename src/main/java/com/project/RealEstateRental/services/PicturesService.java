package com.project.RealEstateRental.services;

import com.project.RealEstateRental.dtos.PictureResponse;
import com.project.RealEstateRental.dtos.UpdatePicturesBody;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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
    public List<PictureResponse> getImages(Properties property) {
        List<Pictures> pictures = picturesRepository.findByProperty(property);
        return pictures.stream()
                .map(picture -> new PictureResponse(s3Service.generatePresignedUrl(picture.getPicturePath()).toString(),picture.getPictureName()))
                .collect(Collectors.toList());
    }

    public String getThumbnail(String thumbnailName){
        String thumbnailPath="thumbnails/"+thumbnailName;
        return s3Service.generatePresignedUrl(thumbnailPath.toString()).toString();
    }

    public String updatePicturesToProperty(UpdatePicturesBody updatePicturesBody, Properties property) throws IOException {
        List<Pictures> existingPictures = getPicturesByProperty(property);
        if (updatePicturesBody.getDeletedPhotos() != null && updatePicturesBody.getDeletedPhotos().length > 0) {
            deletePictures(updatePicturesBody.getDeletedPhotos(), existingPictures);
        }

        boolean isThumbnailSet = false;
        if (!updatePicturesBody.isThumbInNew()) {
            isThumbnailSet = true;
            handleExistingThumbnail(updatePicturesBody, property);
        }

        if (updatePicturesBody.getNewImages() != null && updatePicturesBody.getNewImages().length > 0) {
            saveNewImages(updatePicturesBody, property, isThumbnailSet);
        }

        return updatePicturesBody.getThumbnailPhoto();
    }

    void deletePictures(String[] deletePhotos, List<Pictures> existingPictures) throws IOException {
        // Convert deletePhotos array to a Set for faster lookup
        Set<String> photosToDelete = new HashSet<>(Arrays.asList(deletePhotos));

        // Filter the existing pictures to find the ones to delete
        List<Pictures> picturesToDelete = existingPictures.stream()
                .filter(picture -> photosToDelete.contains(picture.getPictureName()))
                .collect(Collectors.toList());

        // Delete files from S3 and remove from repository
        for (Pictures picture : picturesToDelete) {
            s3Service.deleteFile(picture.getPicturePath());
        }

        picturesRepository.deleteAll(picturesToDelete);
    }

    void deleteIfExistThumbnail(String thumbnail) throws IOException {
        if (thumbnail.length() > 13) {
            s3Service.deleteFile("thumbnails/" + thumbnail);
        }
    }

    void handleExistingThumbnail(UpdatePicturesBody updatePicturesBody, Properties property) throws IOException {
        if (!updatePicturesBody.getThumbnailPhoto().equals(property.getThumbnail())) {
            deleteIfExistThumbnail(property.getThumbnail());
            if(!updatePicturesBody.getThumbnailPhoto().isEmpty()){
                if (updatePicturesBody.getThumbnailPhoto().length() > 13) {
                    copyThumbnail(updatePicturesBody.getThumbnailPhoto(), property);
                } else {
                    System.out.println("Invalid picture name on copying thumbnail: " + updatePicturesBody.getThumbnailPhoto() + " !");
                }
            }
        }
    }

    void copyThumbnail(String thumbnail, Properties property) {
        String sourcePath = property.getIdProperty() + "/" + thumbnail;
        String thumbnailPath = "thumbnails/" + thumbnail;
        s3Service.copyFile(sourcePath, thumbnailPath);
    }

    @Transactional
    boolean handleNewThumbnail(UpdatePicturesBody updatePicturesBody, Properties property, MultipartFile image, String picName) throws IOException {
        if (updatePicturesBody.getThumbnailPhoto().equals(image.getOriginalFilename())) {
            deleteIfExistThumbnail(property.getThumbnail());
            updatePicturesBody.setThumbnailPhoto(picName);
            copyThumbnail(updatePicturesBody.getThumbnailPhoto(), property);
            return true;
        }
        return false;
    }
    @Transactional
    void saveNewImages(UpdatePicturesBody updatePicturesBody, Properties property, boolean incomingIsThumbnailSet) throws IOException {
        boolean isThumbnailSet = incomingIsThumbnailSet;
        BufferedImage watermarkImage = ImageUtils.loadImage("src/main/resources/transparentLogo.png");

        for (MultipartFile imageFile : updatePicturesBody.getNewImages()) {
            Pictures newPicture = picturesRepository.save(new Pictures("temp", "temp", property));

            // Convert MultipartFile to BufferedImage
            BufferedImage image = ImageIO.read(imageFile.getInputStream());

            // Add watermark to image
            BufferedImage watermarkedImage = ImageUtils.addWatermark(image, watermarkImage);

            // Save watermarked image to a temporary location
            File tempFile = new File("temp_watermarked_image.png");
            ImageUtils.saveImage(watermarkedImage, tempFile.getAbsolutePath());

            // Upload watermarked image to S3
            String picName = property.getIdProperty() + "_picture_" + newPicture.getId() + ".png";
            String picturePath = property.getIdProperty() + "/" + picName;

            s3Service.uploadFile(tempFile, picturePath);

            // Clean up temporary file
            tempFile.delete();

            newPicture.setPictureName(picName);
            newPicture.setPicturePath(picturePath);
            picturesRepository.save(newPicture);

            if (!isThumbnailSet) {
                isThumbnailSet = handleNewThumbnail(updatePicturesBody, property, imageFile, picName);
            }
        }
    }

}