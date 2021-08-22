package com.java.aws.filesys.filesysaws.controller;

import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.File;
import com.java.aws.filesys.filesysaws.service.FileService;
import com.java.aws.filesys.filesysaws.service.UserService;
import com.java.aws.filesys.filesysaws.util.FileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/files")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class.getName());
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping(path = "/file/new/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> saveUserFile(@PathVariable("user_id") Integer id,
                                             @RequestParam("file")MultipartFile file) {
        logger.info("REST request to upload file.");
        File res = null;
        try {
            res = fileService.save(new FileAdapter(file, userService.findById(id)).convertMultipartToFile());
        }
        catch (NoSuchEntryException e) {
            logger.error(String.format("REST request failed. User by id: %s doesn't exist", id));
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            logger.error("REST request failed. Malformed data provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res,
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/file/trash")
    public ResponseEntity<HttpStatus> deleteUserFiles(@RequestParam("user_id") Integer userId, @RequestParam("filename") String fileName)
    {
        try {
            fileService.delete(userId, fileName);
        } catch (NoSuchEntryException e) {
            logger.error(String.format("REST request failed. User with id: %s1, or file: %s2 doesn't exist", userId, fileName));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch ( BadRequestException e) {
            logger.error("REST request failed. Malformed data provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
