package com.project.RealEstateRental.controllers;


import com.project.RealEstateRental.dtos.PicturesRequest;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.dtos.UpdatePicturesBody;
import com.project.RealEstateRental.dtos.PictureResponse;
import com.project.RealEstateRental.services.PicturesService;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadImages(
            @PathVariable int id,
             String[] deletedPhotos,
             MultipartFile[] newImages,
             String[] sequenceArray
    ){
        try {
            Properties property=propertiesService.getPropertyById(id);
            PicturesRequest picturesRequest =new PicturesRequest(deletedPhotos,newImages,sequenceArray);
            String result = picturesService.handle(picturesRequest,property);
            propertiesService.updateThumbnailPhoto(property, result);
            return ResponseEntity.ok(result);

        }catch(Exception e) {
            System.out.println("Failed"+e.getMessage());
            return ResponseEntity.badRequest().body("Images uploading FAILED! "+e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public List<PictureResponse> getPictures(
            @PathVariable int id
    ) {
        Properties property = propertiesService.getPropertyById(id);
        return picturesService.getImages(property);
    }
}
