package com.java.aws.filesys.filesysaws.controller;

import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.User;
import com.java.aws.filesys.filesysaws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/new")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        logger.info("REST request to save user");
        try {
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> findAllUsers() {
        logger.info("REST request to receive all users");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") Integer id) throws NoSuchEntryException {
        logger.info("REST request to get user by id");
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
        logger.info("REST request to delete user.");
        return new ResponseEntity<>(userService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/profile")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("REST request to update user.");
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }
}
