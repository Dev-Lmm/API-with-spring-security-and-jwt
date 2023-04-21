package com.dev.lmm.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadPhotoServiceImpl implements IUploadService {
    private final Logger log = LoggerFactory.getLogger(UploadPhotoServiceImpl.class);

    @Override
    public Resource upload(String photoName) throws MalformedURLException {
        Path photoPath = getPath(photoName);
        log.info(photoPath.toString());
        Resource resource = new UrlResource(photoPath.toUri());
        if (!resource.exists() && !resource.isReadable()){
            photoPath = Paths.get("src/main/resources/static/photos").resolve("default_img_user.png").toAbsolutePath();
            resource = new UrlResource(photoPath.toUri());
            log.error("Error uploading photo " + photoName);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile photo) throws IOException {
        String extension = getExtension(Objects.requireNonNull(photo.getOriginalFilename()));
        String photoName = generateFilename(extension);
        Path photoPath = getPath(photoName);
        log.info(photoPath.toString());
        Files.copy(photo.getInputStream(), photoPath);
        return photoName;
    }

    private static String getExtension(String filename) {
        String[] stringParts = filename.split("\\.");
        return stringParts[stringParts.length - 1];
    }

    @Override
    public boolean remove(String photoName) {
        if (photoName != null && photoName.length() > 0){
            Path previousPhotoPath = Paths.get("uploads").resolve(photoName).toAbsolutePath();
            File previousPhoto = previousPhotoPath.toFile();
            if (previousPhoto.exists() && previousPhoto.canRead()){
                previousPhoto.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String photoName) {
        return Paths.get("uploads").resolve(photoName).toAbsolutePath();
    }

    private static String generateFilename(String extension){
        return UUID.randomUUID().toString() + "." + extension;
    }
}
