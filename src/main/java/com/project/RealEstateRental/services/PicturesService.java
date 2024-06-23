package com.project.RealEstateRental.services;

import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;
    @Value("${image.directory.path}")
    private String imageDirectoryPath;
    public static final String FOLDER_PATH="C:/Users/lukat/OneDrive/Desktop/myfiles/";

    @Autowired
    public PicturesService(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    public boolean uploadPicturesToProperty(MultipartFile[] images, Properties property) throws IOException {
        // First delete all pictures
        List<Pictures> existingPictures = picturesRepository.findByProperty(property);
        if( existingPictures!=null ) {
            for (Pictures picture : existingPictures) {
                Files.delete(Path.of(picture.getPicturePath()));
                picturesRepository.delete(picture);
            }
        }
        int cnt=0;
        // Second add them again
        for(MultipartFile image: images){
            String picName=property.getIdProperty()+"_picture_"+cnt+".jpeg";
            cnt++;
            String picturePath=FOLDER_PATH+picName;
            //Pictures picture=
            picturesRepository.save(
                    new Pictures(picName,picturePath,property));
            image.transferTo(new File(picturePath));
        }
        return true;
    }

    public List<Pictures> getPicturesByProperty(Properties property){
        return picturesRepository.findByProperty(property);
    }

    public List<String> testGetImages(Properties property) {
        List<Pictures> pictures = picturesRepository.findByProperty(property);
        return pictures.stream()
                .map(Pictures::getPictureName) // Assuming picturePath is the URL or path
                .collect(Collectors.toList());
    }
}
