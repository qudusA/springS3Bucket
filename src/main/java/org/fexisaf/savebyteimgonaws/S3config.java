package org.fexisaf.savebyteimgonaws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class S3config {

    @Value("${aws.secret-access-key}")
    private String secretAccessKey;
    @Value("${aws.access-key-id}")
    private String accessKryId;
    @Value("${aws.region}")
    private String region;



    public AmazonS3 getClient() throws IOException {
        AWSCredentials credentials =
                new BasicAWSCredentials(accessKryId,
                        secretAccessKey);

       return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

    }


}
