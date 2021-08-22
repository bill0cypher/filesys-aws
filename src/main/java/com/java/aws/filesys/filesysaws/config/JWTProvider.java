package com.java.aws.filesys.filesysaws.config;

import com.java.aws.filesys.filesysaws.config.props.JWTProperties;
import com.java.aws.filesys.filesysaws.exceptions.JWTAuthenticationException;
import com.java.aws.filesys.filesysaws.model.Role;
import com.java.aws.filesys.filesysaws.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final JWTProperties properties;

    @Autowired
    public JWTProvider(UserDetailsServiceImpl userDetailsService, JWTProperties properties) {
        this.userDetailsService = userDetailsService;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        properties.setSecret(Base64.getEncoder().encodeToString(properties.getSecret().getBytes()));
    }

    public String createToken(String username, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles.stream().map(role -> role.getAuthority().name()).collect(Collectors.toList()));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + properties.getValidInMilliSeconds()))
                .signWith(SignatureAlgorithm.HS256, properties.getSecret())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String username = decodeUsername(token);
        return new UsernamePasswordAuthenticationToken(
                username,
                "",
                userDetailsService.loadUserByUsername(username).getAuthorities());
    }


    public String decodeUsername(String token) {
        return Jwts.parser().setSigningKey(properties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (null != token && token.startsWith("Bearer_"))
            return token.substring(7);
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return null != token &&
                    !Jwts.parser()
                            .setSigningKey(properties.getSecret())
                            .parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (JWTAuthenticationException | IllegalArgumentException e) {
            throw new JWTAuthenticationException("JWT expired or empty.");
        }
    }

}
