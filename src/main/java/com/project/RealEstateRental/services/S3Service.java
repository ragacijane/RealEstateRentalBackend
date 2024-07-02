package com.project.RealEstateRental.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${AWS_ACCESS_KEY_ID:#{null}}")
    private String accessKeyId;

    @Value("${AWS_SECRET_ACCESS_KEY:#{null}}")
    private String secretKey;

    @Value("${S3_BUCKET_NAME:#{null}}")
    private String bucketName;

    @Value("${AWS_REGION:#{null}}")
    private String region;
    @PostConstruct
    private void initializeAmazon() {
        if (accessKeyId == null || secretKey == null || bucketName == null || region == null) {
            Dotenv dotenv = Dotenv.load();
            accessKeyId = dotenv.get("AWS_ACCESS_KEY_ID");
            secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
            bucketName = dotenv.get("S3_BUCKET_NAME");
            region = dotenv.get("AWS_REGION");
        }


        System.out.println("Initializing Amazon S3 Client with Access Key ID: "+accessKeyId + ", Bucket Name: " + bucketName+ ", Region: "+region);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)  // Adjust the region as needed
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void uploadFile(MultipartFile file, String fileName) throws IOException {
        File convertedFile = convertMultiPartToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();
    }

    public URL getFileUrl(String fileName) {
        return s3Client.getUrl(bucketName, fileName);
    }

    public URL generatePresignedUrl(String fileName) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 1 hour expiration
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

    public void copyFile(String sourceFileName, String destinationFileName) {
        s3Client.copyObject(bucketName, sourceFileName, bucketName, destinationFileName);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
