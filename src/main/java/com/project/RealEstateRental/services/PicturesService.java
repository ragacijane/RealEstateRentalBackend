package com.project.RealEstateRental.services;

import com.project.RealEstateRental.controllers.PicturesBody;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PicturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;
    @Value("${image.directory.path}")
    private String imageDirectoryPath;

    @Autowired
    public PicturesService(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }
    public List<Pictures> getPicturesByProperty(Properties property){
        return picturesRepository.findByProperty(property);
    }
    public List<String> getImages(Properties property) {
        List<Pictures> pictures = picturesRepository.findByProperty(property);
        return pictures.stream()
                .map(Pictures::getPicturePath)
                .collect(Collectors.toList());
    }
    public String updatePicturesToProperty(PicturesBody picturesBody, Properties property) throws IOException {
        System.out.println(Arrays.toString(picturesBody.getDeletedPhotos()));
        System.out.println(Arrays.toString(picturesBody.getNewImages()));
        System.out.println(picturesBody.isThumbInNew());
        System.out.println(picturesBody.getThumbnailPhoto());
        // First delete if needed
        List<Pictures> existingPictures = getPicturesByProperty(property);
        if(picturesBody.getDeletedPhotos()!=null && picturesBody.getDeletedPhotos().length>0){
            System.out.println("Deleting");
            deletePictures(picturesBody.getDeletedPhotos(),existingPictures);
        }
        // Create folder if not exists
        String propertyFolderPath = imageDirectoryPath +"/"+ property.getIdProperty();
        createDirectory(propertyFolderPath);
        // Update thumbnail if it exists
        boolean isThumbnailSet=false;
        if(!picturesBody.isThumbInNew()){
            System.out.println("Thumbnailing");
            isThumbnailSet=true;
            handleExistingThumbnail(picturesBody,property,propertyFolderPath);
        }
        // Add new images
        if(picturesBody.getNewImages()!=null && picturesBody.getNewImages().length>0){
            System.out.println("Saving");
            saveNewImages(picturesBody,property,propertyFolderPath,isThumbnailSet);
        }
    System.out.println("****END****");
        return picturesBody.getThumbnailPhoto();
    }

    private void deletePictures(String[] deletePhotos, List<Pictures> existingPictures)throws IOException{
        for(String picToDelete: deletePhotos){
            for(Pictures picture: existingPictures){
                if(picToDelete.equals(picture.getPictureName())){
                    System.out.println("Deleting picture: " + imageDirectoryPath + "/" + picture.getPicturePath());
                    Files.deleteIfExists(Path.of((imageDirectoryPath + "/" + picture.getPicturePath())));
                    picturesRepository.delete(picture);
                    break;
                }
            }
        }
    }
    private void createDirectory(String directoryPath){
        File directory = new File(directoryPath);
        if(!directory.exists()){
            System.out.println("Creating directory: "+directoryPath);
            directory.mkdirs();
        }
    }
    private void deleteIfExistThumbnail(String thumbnail)throws IOException{
        if(thumbnail.length()>13){
            String thumbnailPath = imageDirectoryPath + "/thumbnails/" + thumbnail;
            System.out.println("Deleting old thumbnail: " + thumbnailPath);
            Files.deleteIfExists(Path.of(thumbnailPath));
        }
    }
    private void copyThumbnail(String thumbnail, String propertyFolder)throws IOException{
        String sourcePath = propertyFolder+"/"+thumbnail;
        String thumbnailPath = imageDirectoryPath + "/thumbnails/"+thumbnail;
        System.out.println("Copying new thumbnail from " + sourcePath + " to " + thumbnailPath);
        Files.copy(Path.of(sourcePath), Path.of(thumbnailPath));
    }
    private void handleExistingThumbnail(PicturesBody picturesBody,Properties property,String propertyFolderPath)throws IOException{
        // if incoming is NOT equal as current
        if (!picturesBody.getThumbnailPhoto().equals(property.getThumbnail())){
            deleteIfExistThumbnail(property.getThumbnail());
            // If incoming exists
            if(picturesBody.getThumbnailPhoto().length() > 13){
                copyThumbnail(picturesBody.getThumbnailPhoto(), propertyFolderPath);
            }
            else{
                System.out.println("Invalid picture name on copying thumbnail: "+picturesBody.getThumbnailPhoto());
            }
        }
    }
    private boolean handleNewThumbnail(PicturesBody picturesBody, Properties property, MultipartFile image, String picName, String propertyFolder) throws IOException {
        // If incoming equals adding
        if (picturesBody.getThumbnailPhoto().equals(image.getOriginalFilename())) {
            System.out.println("Handeling new thumbnail: "+picName);
            deleteIfExistThumbnail(property.getThumbnail());
            picturesBody.setThumbnailPhoto(picName);
            copyThumbnail(picturesBody.getThumbnailPhoto(),propertyFolder);
            return true;
        }
        return false;
    }
    private void saveNewImages(PicturesBody picturesBody,Properties property, String propertyFolderPath,boolean incomingIsThumbnailSet)throws IOException{
        boolean isThumbnailSet=incomingIsThumbnailSet;

        for (MultipartFile image : picturesBody.getNewImages()) {
            Pictures newPicture = picturesRepository.save(
                    new Pictures("temp", "temp", property));

            String picName = property.getIdProperty() + "_picture_" + newPicture.getId() + ".jpeg";
            String picturePath = property.getIdProperty() + "/" + picName;
            String fullPath = propertyFolderPath + "/" + picName;

            newPicture.setPictureName(picName);
            newPicture.setPicturePath(picturePath);
            picturesRepository.save(newPicture);
            System.out.println("Adding new picture: "+picName);
            image.transferTo(new File(fullPath));
            if (!isThumbnailSet) {
                isThumbnailSet = handleNewThumbnail(picturesBody, property, image, picName, propertyFolderPath);
            }

        }
    }



}
