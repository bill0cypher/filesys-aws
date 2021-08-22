package com.java.aws.filesys.filesysaws.controller;

import com.java.aws.filesys.filesysaws.config.JWTProvider;
import com.java.aws.filesys.filesysaws.dto.LoginPair;
import com.java.aws.filesys.filesysaws.dto.TokenDTO;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.User;
import com.java.aws.filesys.filesysaws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());
    private final UserService userService;
    private final JWTProvider provider;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JWTProvider provider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.provider = provider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(path = "/authentication")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginPair pair) throws NoSuchEntryException {
        logger.info(String.format("REST request to authenticate user: %s", pair.getUsername()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pair.getUsername(), pair.getPassword()));
        User user = userService.findByUsername(pair.getUsername());
        return new ResponseEntity<>(new TokenDTO(user.getUsername(), provider.createToken(user.getUsername(), user.getRoles())), HttpStatus.OK);
    }
}
