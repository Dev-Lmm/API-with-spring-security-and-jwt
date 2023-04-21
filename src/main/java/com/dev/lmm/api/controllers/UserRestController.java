package com.dev.lmm.api.controllers;

import com.dev.lmm.api.models.entity.User;
import com.dev.lmm.api.services.IUploadService;
import com.dev.lmm.api.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dev-lmm")
@RequiredArgsConstructor
public class UserRestController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUploadService uploadPhotoService;
    HashMap<String, String> messageMap;

    @PostMapping("/users/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("photo") MultipartFile photo, @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(id);

        if(photo.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String photoName = null;
        try {
            photoName = uploadPhotoService.copy(photo);
        } catch (IOException e) {
            response.put("message", "Error uploading photo");
            //response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String previousNamePhoto = user.getPhoto();
        uploadPhotoService.remove(previousNamePhoto);
        user.setPhoto(photoName);
        response.put("user", user);
        response.put("message", "Photo uploaded successfully" + photoName);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
