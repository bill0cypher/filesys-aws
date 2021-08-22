package com.java.aws.filesys.filesysaws.config;

import com.java.aws.filesys.filesysaws.config.filters.JWTFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JWTProvider provider;

    public JWTConfigurer(JWTProvider jwtProvider) {
        this.provider = jwtProvider;
    }

    @Override
    public void configure(HttpSecurity builder) {
        builder.addFilterBefore(new JWTFilter(provider), UsernamePasswordAuthenticationFilter.class);

    }
}
