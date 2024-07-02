package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.services.PicturesService;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pictures")
public class PicturesController {

    private final PicturesService picturesService;
    private final PropertiesService propertiesService;
    @Autowired
    public PicturesController(PicturesService picturesService, PropertiesService propertiesService) {
        this.picturesService = picturesService;
        this.propertiesService = propertiesService;
    }

    @GetMapping("/getThumbnail/{thumbnail}")
    public ResponseEntity<String> getThumbnail(
            @PathVariable String thumbnail
    ){
        String temp=picturesService.getThumbnail(thumbnail);
        return ResponseEntity.ok(temp);
    }
    @PostMapping("/updatePictures/{id}")
    public ResponseEntity<String> updateImageToFileSystem(
            @PathVariable int id,
            MultipartFile[] newImages,
            String thumbnailPhoto,
            String[] deletedPhotos,
            String isThumbInNew
    ){
        try {
            Properties property=propertiesService.getPropertyById(id);
            PicturesBody picturesBody=new PicturesBody(isThumbInNew,thumbnailPhoto,deletedPhotos,newImages);
            String result = picturesService.updatePicturesToProperty(picturesBody,property);
            propertiesService.updateThumbnailPhoto(property, result);
            return ResponseEntity.ok(result);

        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Images uploading FAILED! "+e.getMessage());
        }
    }

    @GetMapping("/getPictures/{id}")
    public List<String> getPictures(
            @PathVariable int id
    ) {
        Properties property = propertiesService.getPropertyById(id);

        return picturesService.getImages(property);
    }
}
