package com.java.aws.filesys.filesysaws.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private String secret;
    private Long validInMilliSeconds;

    public JWTProperties() {
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getValidInMilliSeconds() {
        return validInMilliSeconds;
    }

    public void setValidInMilliSeconds(Long validInMilliSeconds) {
        this.validInMilliSeconds = validInMilliSeconds;
    }
}
