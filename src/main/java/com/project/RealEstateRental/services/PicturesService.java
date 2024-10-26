package com.project.RealEstateRental.services;

import com.project.RealEstateRental.dtos.PictureResponse;
import com.project.RealEstateRental.dtos.PicturesRequest;
import com.project.RealEstateRental.dtos.UpdatePicturesBody;
import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;
    private final S3Service s3Service;

    @Value("${cloud-front}")
    private String cloudFront;

    @Autowired
    public PicturesService(PicturesRepository picturesRepository, S3Service s3Service) {
        this.picturesRepository = picturesRepository;
        this.s3Service = s3Service;
    }
    public List<Pictures> getPicturesByProperty(Properties property){
        return picturesRepository.findByProperty(property);
    }

    public List<PictureResponse> getImages(Properties property){
        return picturesRepository.findPictureDetailsByPropertyOrdered(property);
    }

    @Transactional
    public String handle(PicturesRequest picturesRequest,Properties property)throws IOException{
        String newThumbnail="";
        List<Pictures> existingPictures = getPicturesByProperty(property);
        if (picturesRequest.getDeletedPhotos() != null && picturesRequest.getDeletedPhotos().length > 0) {
            existingPictures = deletePictures(picturesRequest.getDeletedPhotos(), existingPictures);
        }


        List<String> sequenceArray=Arrays.asList(picturesRequest.getSequenceArray());

        for(Pictures picture: existingPictures) {
            int sequenceIndex = sequenceArray.indexOf(picture.getPictureName());
            if(sequenceIndex != -1){
                picture.setSequenceNumber(sequenceIndex+1);
            }
            if(sequenceIndex==0){
                newThumbnail=picture.getThumbnailPath();
            }
        }

        BufferedImage watermarkImage = ImageUtils.loadImage("src/main/resources/transparentLogo.png");

        if(picturesRequest.getNewImages() != null && picturesRequest.getNewImages().length > 0){
            for (MultipartFile imageFile : picturesRequest.getNewImages()) {

                BufferedImage image = ImageIO.read(imageFile.getInputStream());
                BufferedImage watermarkedImage = ImageUtils.addWatermark(image, watermarkImage);

                File tempFile = new File("temp_watermarked_image.png");
                ImageUtils.saveImage(watermarkedImage, tempFile.getAbsolutePath());

                // Upload watermarked image to S3
                String picName = property.getIdProperty() + "_picture_" + Pictures.getNextId() + ".png";
                String picturePath = property.getIdProperty() + "/" + picName;
                s3Service.uploadFile(tempFile, picturePath);
                tempFile.delete();

                BufferedImage thumbnailImage = resizeImage(watermarkedImage, 300, 300); // Thumbnail size: 150x150 pixels
                File thumbnailFile = new File("temp_thumbnail_image.png");
                ImageUtils.saveImage(thumbnailImage, thumbnailFile.getAbsolutePath());

                // Upload thumbnail image to S3
                String thumbnailPath = property.getIdProperty() + "/thumbnails/" + picName;
                s3Service.uploadFile(thumbnailFile, thumbnailPath);
                thumbnailFile.delete();

                int sequenceIndex = sequenceArray.indexOf(imageFile.getOriginalFilename());
                if(sequenceIndex==0){
                    newThumbnail=cloudFront+"/"+thumbnailPath;
                }
                existingPictures.add(new Pictures(picName,cloudFront+"/"+picturePath,cloudFront+"/"+thumbnailPath,property,sequenceIndex+1));
            }
        }

        picturesRepository.saveAll(existingPictures);

        return newThumbnail;
    }
    List<Pictures> deletePictures(String[] deletePhotos, List<Pictures> existingPictures) throws IOException {
        Set<String> photosToDelete = new HashSet<>(Arrays.asList(deletePhotos));

        // Prepare lists for deletion and retention
        List<Pictures> picturesToDelete = new ArrayList<>();
        List<Pictures> remainingPictures = new ArrayList<>();

        String prefixToRemove=cloudFront+"/";
        for (Pictures picture : existingPictures) {
            if (photosToDelete.contains(picture.getPictureName())) {
                picturesToDelete.add(picture);
                s3Service.deleteFile(picture.getPicturePath().replace(prefixToRemove,""));
                s3Service.deleteFile(picture.getThumbnailPath().replace(prefixToRemove,""));
            } else {
                remainingPictures.add(picture);
            }
        }

        picturesRepository.deleteAll(picturesToDelete);

        return remainingPictures;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Check if the original image is larger than the target dimensions
        if (originalWidth <= targetWidth && originalHeight <= targetHeight) {
            // Return the original image if it doesn't need resizing
            return originalImage;
        }

        // Calculate the aspect ratio
        double aspectRatio = (double) originalWidth / originalHeight;

        int newWidth;
        int newHeight;

        // Determine the new dimensions while maintaining aspect ratio
        if (aspectRatio > 1) { // Wider than tall
            newWidth = targetWidth;
            newHeight = (int) (targetWidth / aspectRatio);
        } else { // Taller than wide or square
            newHeight = targetHeight;
            newWidth = (int) (targetHeight * aspectRatio);
        }

        // Create a new BufferedImage with target dimensions
        BufferedImage thumbnail = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = thumbnail.createGraphics();

        // Clear the background
        g.setColor(Color.WHITE); // or any other background color
        g.fillRect(0, 0, targetWidth, targetHeight);

        // Calculate the x and y coordinates to center the image
        int x = (targetWidth - newWidth) / 2;
        int y = (targetHeight - newHeight) / 2;

        // Set rendering hints for better quality
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the resized image centered
        g.drawImage(originalImage, x, y, newWidth, newHeight, null);
        g.dispose();

        return thumbnail;
    }
}