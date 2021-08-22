package com.java.aws.filesys.filesysaws.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {

    private String accessKeyId;
    private String accessSecretKey;
    private String appBucket;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessSecretKey() {
        return accessSecretKey;
    }

    public String getAppBucket() {
        return appBucket;
    }

    public void setAppBucket(String appBucket) {
        this.appBucket = appBucket;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessSecretKey(String accessSecretKey) {
        this.accessSecretKey = accessSecretKey;
    }
}
