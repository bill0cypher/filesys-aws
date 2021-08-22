package com.java.aws.filesys.filesysaws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.java.aws.filesys.filesysaws.config.props.AWSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    private final AWSProperties properties;
    private final Logger logger = LoggerFactory.getLogger(AWSConfig.class.getName());
    private AmazonS3 client;

    @Autowired
    public AWSConfig(AWSProperties properties) {
        this.properties = properties;
    }

    private AWSCredentials initAWSCredentials() {
        logger.info("Init AWS credentials method called");
        return new BasicAWSCredentials(
                properties.getAccessKeyId(),
                properties.getAccessSecretKey()
        );
    }

    private AmazonS3 initAWSClient(AWSCredentials credentials) {
        logger.info("Init AWS client method called");
        String appBucket = properties.getAppBucket();
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        if (!client.doesBucketExist(appBucket))
            client.createBucket(appBucket);
        return client;
    }

    @Bean
    public AmazonS3 getClient() {
        if (client == null)
            client = initAWSClient(initAWSCredentials());
        return client;
    }
}
