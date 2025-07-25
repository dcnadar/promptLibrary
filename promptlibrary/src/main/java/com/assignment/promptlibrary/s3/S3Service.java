package com.assignment.promptlibrary.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  public S3Service(@Value("${aws.accessKeyId}") String accessKeyId,
      @Value("${aws.secretKey}") String secretKey,
      @Value("${aws.region}") String region) {

    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
    this.s3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
        .build();
  }

  public String uploadFile(MultipartFile file) throws IOException {
    String key = "prompts/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .acl("public-read")
        .contentType(file.getContentType())
        .build();

    s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

    return key;
  }

  public void deleteFile(String key) {
    s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key).build());
  }

  public String getFileUrl(String key) {
    return "https://" + bucketName + ".s3.amazonaws.com/" + key;
  }
}
