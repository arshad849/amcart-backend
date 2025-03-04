package com.nagp.products.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    public List<String> uploadFile(List<MultipartFile> multipartFiles, String productId) {
        List<String> result = new ArrayList<>();
        String filePath = "";
        try {
            for(MultipartFile multipartFile: multipartFiles){
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(multipartFile.getContentType());
                objectMetadata.setContentLength(multipartFile.getSize());
                filePath = "products/"+productId+"/"+multipartFile.getOriginalFilename();
                s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
                String fileUri = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, filePath);
                result.add(fileUri);
            }
        } catch (IOException e) {
            log.error("Error occurred ==> {}", e.getMessage());
            throw new RuntimeException("Error occurred in file upload ==> "+e.getMessage());
        }
        return result;
    }
}
