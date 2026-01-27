package com.ldb.iadoc.Dao.upload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public interface MediaUploadService {
    public String uploadDirectoryGEN(File file);
    public String uploadDirectoryDocEn(MultipartFile file);
    public String uploadDirectoryDocLa(MultipartFile file);
    public String uploadDirectoryDocLaGen(File file,UUID uuid);
}
