
package com.ldb.iadoc.Service;

import com.ldb.iadoc.Dao.upload.MediaUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MediaUploadServiceImpl implements MediaUploadService {
    @Value("${upload.directory.docEn}")
    private String uploadDirectoryDocEn;

    @Value("${upload.directory.docEn}")
    private String uploadDirectoryDocLa;

    @Value("${media.upload.path}")
    private String uploadPath;
    @Override
    public String uploadDirectoryDocEn(MultipartFile file) {
        try {

            log.info("Begin Convert MultiPart File To Base64String");
            String base64String = new String(Base64.encodeBase64(file.getBytes()));
            log.info("Convert To Base64 String Completed");

            log.info("Get File Extension");
            String[] filePattern = (file.getOriginalFilename()).split("\\.");
            String extension = filePattern[filePattern.length-1];
            log.info("Media File Extension Is {}",extension);

            log.info("Generate Random Media Name");
            UUID uuid =  UUID.randomUUID();
            String fileName = uuid.toString() + "-"+uuid.toString() + "." + extension;
            log.info("New Generate File Name {}" + fileName);

            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadDirectoryDocEn);
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadDirectoryDocEn);
            }else{
                httpPost = new HttpPost(uploadDirectoryDocEn);
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            log.info("Start To Post Upload Image ...");
            HttpResponse rest = client.execute(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

    }
    @Override
    public String uploadDirectoryDocLa(MultipartFile file) {
        try {

            log.info("Begin Convert MultiPart File To Base64String");
            String base64String = new String(Base64.encodeBase64(file.getBytes()));
            log.info("Convert To Base64 String Completed");

            log.info("Get File Extension");
            String[] filePattern = (file.getOriginalFilename()).split("\\.");
            String extension = filePattern[filePattern.length-1];
            log.info("Media File Extension Is {}",extension);

            log.info("Generate Random Media Name");
            UUID uuid =  UUID.randomUUID();
            String fileName = uuid.toString() + "-"+uuid.toString() + "." + extension;
            log.info("New Generate File Name {}" + fileName);

            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadDirectoryDocLa);
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadDirectoryDocLa);
            }else{
                httpPost = new HttpPost(uploadDirectoryDocLa);
            }
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            log.info("Start To Post Upload Image ...");
            HttpResponse rest = client.execute(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

    }
}
