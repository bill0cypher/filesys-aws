package com.java.aws.filesys.filesysaws.config.filters;

import com.java.aws.filesys.filesysaws.config.JWTProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends GenericFilterBean {

    private final JWTProvider provider;

    public JWTFilter(JWTProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         String token = provider.resolveToken((HttpServletRequest) servletRequest);
        Optional.of(provider.validateToken(token))
                .filter(res -> res)
                .map(res -> provider.getAuthentication(token))
                .ifPresent(res -> SecurityContextHolder.getContext().setAuthentication(res));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
