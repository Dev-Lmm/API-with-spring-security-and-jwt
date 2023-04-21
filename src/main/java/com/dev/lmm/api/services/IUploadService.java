package com.dev.lmm.api.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadService {
    Resource upload(String photoName) throws MalformedURLException;
    String copy(MultipartFile photo) throws IOException;
    boolean remove(String photoName);
    Path getPath(String photoName);
}
