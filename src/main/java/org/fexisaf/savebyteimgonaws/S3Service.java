package org.fexisaf.savebyteimgonaws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3config s3;

    @Value("${aws.bucket-name}")
    private String bucketName;

    public PutObjectResult saveImageToS3(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        String fileName = uuid + "_" + date + file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.setContentLength(inputStream.available());

        PutObjectRequest request =
                new PutObjectRequest(bucketName, fileName, inputStream, metadata);
       return s3.getClient().putObject(request);

    }

    public InputStream getImage(String id) throws IOException {
        GetObjectRequest object = new GetObjectRequest(bucketName, id);
        S3Object s3Object = s3.getClient().getObject(object);
        return s3Object.getObjectContent();
    }

    public String deleteImage(String id) throws IOException {
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, id);
        s3.getClient().deleteObject(request);
        return "image deletion complete...";
    }

    public String updateImage(String id, MultipartFile file) throws Exception {
try {
      deleteImage(id);
      saveImageToS3(file);
    return "update successful...";
}catch (Exception e){
    throw new Exception(e);
}

    }
}
