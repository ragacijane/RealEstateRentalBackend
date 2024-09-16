package com.project.RealEstateRental.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Value("${aws.secret-access-key}")
    private String secretKey;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initializeAmazon() {
        logger.info("Initializing Amazon S3 Client with Access Key ID: {}, Bucket Name: {}, Region: {}", accessKeyId, bucketName, region);

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
    public void uploadFile(File file, String fileName) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete(); // Clean up the local file after upload
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
}
