package com.ldb.iadoc.Dao.upload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface MediaUploadService {

    public String uploadDirectoryDocEn(MultipartFile file);
    public String uploadDirectoryDocLa(MultipartFile file);
}
