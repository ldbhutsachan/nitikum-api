
package com.ldb.iadoc.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.ldb.iadoc.Dao.upload.MediaUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MediaUploadServiceImpl implements MediaUploadService {

    @Value("${media.upload.url}")
    private String uploadURL;
    @Value("${media.upload.path}")
    private String uploadPath;
    @Value("${media.upload.url_pdf}")
    private String uploadURLPDF;

    private static final int UPLOAD_CONNECT_TIMEOUT_MS = 15_000;
    // Large signed PDFs (base64-encoded in the body) can legitimately take a while to
    // transfer, so give the socket a generous timeout instead of guessing low.
    private static final int UPLOAD_SOCKET_TIMEOUT_MS = 120_000;

    private static RequestConfig uploadRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(UPLOAD_CONNECT_TIMEOUT_MS)
                .setConnectionRequestTimeout(UPLOAD_CONNECT_TIMEOUT_MS)
                .setSocketTimeout(UPLOAD_SOCKET_TIMEOUT_MS)
                .build();
    }

    /**
     * Executes the upload POST and returns the response body. Unlike the old
     * fire-and-forget calls, this never silently treats a failure as success: any
     * connection error propagates as-is, and a non-2xx response is turned into an
     * IOException carrying the status code and response body so the real cause is
     * visible in the logs instead of being swallowed into a blank "" return value.
     */
    private String executeUpload(HttpPost httpPost) throws IOException {
        httpPost.setConfig(uploadRequestConfig());
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpPost)) {
            int status = response.getStatusLine().getStatusCode();
            String body = response.getEntity() != null
                    ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
                    : "";
            if (status < 200 || status >= 300) {
                throw new IOException("Upload server returned HTTP " + status + ": "
                        + (body.length() > 500 ? body.substring(0, 500) : body));
            }
            return body;
        }
    }

    public String uploadDirectoryGEN(File file) {
        try {
            log.info("=======start =======:"+file.getName());
            log.info("Begin Convert File To Base64String");
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String base64String = Base64.encodeBase64String(fileBytes);
            log.info("Convert To Base64 String Completed");

            log.info("Get File Extension");
            String fileNameOnly = file.getName(); // e.g. mydoc.pdf
            String[] filePattern = fileNameOnly.split("\\.");
            String extension = filePattern[filePattern.length - 1];
            log.info("Media File Extension Is {}", extension);

            log.info("Generate Random Media Name");
            UUID uuid = UUID.randomUUID();
            String newFileName = file.getName();
            log.info("New Generate File Name {}", newFileName);

            log.info("Begin To Calling Http Request Upload URL: {}", uploadURL);
            HttpPost httpPost;
            if ("pdf".equalsIgnoreCase(extension)) {
                httpPost = new HttpPost(uploadURLPDF);
            } else {
                httpPost = new HttpPost(uploadURL);
            }

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", newFileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            log.info("Start To Post Upload File ...");
            executeUpload(httpPost);
            log.info("Finish Upload: {}", newFileName);

            return uploadPath + newFileName;
        } catch (Exception ex) {
            log.error("Error uploading file", ex);
            return "";
        }
    }


    public File genPDFS(String docPathLa, String outputPath) throws Exception {
        log.info("=== genPDF controller for large files ===");

        String imageUrl = "https://dehome.ldblao.la/mobile/logo/ldb-logo.gif";
        ImageData imageData = ImageDataFactory.create(new URL(imageUrl));

        try (InputStream in = docPathLa.startsWith("http")
                ? new URL(docPathLa).openStream()
                : Files.newInputStream(Paths.get(docPathLa));
             PdfReader reader = new PdfReader(in);
             PdfWriter writer = new PdfWriter(outputPath);
             PdfDocument pdfDoc = new PdfDocument(reader, writer)) {

            final int cols = 4;
            final int rows = 4;

            int totalPages = pdfDoc.getNumberOfPages();
            for (int p = 1; p <= totalPages; p++) {
                PdfPage page = pdfDoc.getPage(p);
                float pageW = page.getPageSize().getWidth();
                float pageH = page.getPageSize().getHeight();
                float cellW = pageW / cols;
                float cellH = pageH / rows;

                try (Canvas canvas = new Canvas(page, page.getPageSize())) {
                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            Image img = new Image(imageData);
                            img.setOpacity(0.2f);
                            img.scaleToFit(cellW * 0.9f, cellH * 0.9f);
                            img.setRotationAngle(Math.toRadians(60));

                            float imgW = img.getImageScaledWidth();
                            float imgH = img.getImageScaledHeight();

                            float x = c * cellW + (cellW - imgW) / 2f;
                            float y = pageH - (r + 1) * cellH + (cellH - imgH) / 2f;

                            img.setFixedPosition(p, x, y);
                            canvas.add(img);
                        }
                    }
                }
            }
        }

        return new File(outputPath);
    }

    @Override
    public String uploadDirectoryDocLaGen(File file, UUID uuid) {
        String fileName;
        try {
            log.info("Begin Convert File To Base64 String");

            // Read file bytes
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String base64String = Base64.encodeBase64String(fileBytes);

            log.info("Convert To Base64 String Completed");

            // Get file extension safely
            String fileNameOriginal = file.getName();
            String extension = "";
            int dotIndex = fileNameOriginal.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < fileNameOriginal.length() - 1) {
                extension = fileNameOriginal.substring(dotIndex + 1).toLowerCase();
            }

            log.info("Media File Extension Is {}", extension);

            // Generate random media name (always enforce extension if present)
            fileName = extension.isEmpty()
                    ? uuid.toString()
                    : uuid.toString() + "." + extension;

            log.info("New Generated File Name {}", fileName);

            // Choose upload URL based on extension
            HttpPost httpPost = "pdf".equals(extension)
                    ? new HttpPost(uploadURLPDF)
                    : new HttpPost(uploadURL);

            // Prepare parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            log.info("Start To Post Upload File ...");
            executeUpload(httpPost);
            log.info("Finish File Upload: {}", fileName);

        } catch (Exception ex) {
            // Surface the real cause instead of swallowing it into a blank "" return -
            // the caller (DocumentController) logs this with a full stack trace, and
            // that's the only place the actual reason (timeout, connection refused,
            // HTTP 4xx/5xx from the upload server, etc.) is visible.
            log.error("Error uploading file {}", file.getName(), ex);
            throw new RuntimeException("Upload failed for " + file.getName() + ": " + ex.getMessage(), ex);
        }
        return uploadPath + fileName;
    }
    @Override
    public String uploadDirectoryDocLa(MultipartFile file) {
        try {
           // UUID uuid =  UUID.randomUUID();

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

            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadURL);
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadURLPDF);
            }else{
                httpPost = new HttpPost(uploadURL);
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            log.info("Start To Post Upload Image ...");
            executeUpload(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            log.error("Error uploading LAO file {}", file.getOriginalFilename(), ex);
            return "";
        }

    }
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

            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadURL);
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadURLPDF);
            }else{
                httpPost = new HttpPost(uploadURL);
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            log.info("Start To Post Upload Image ...");
            executeUpload(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            log.error("Error uploading EN file {}", file.getOriginalFilename(), ex);
            return "";
        }

    }


    public void genPDF(String docPathLa,String fileName) throws Exception {
        log.info("====================================================>genPDF controller<=========================");

        String pdfUrl = docPathLa;
        String imageUrl = "https://dehome.ldblao.la/mobile/logo/ldb-logo.gif";
        String outputPath = fileName;

        // Download PDF from URL
        File pdfFile = new File("log/temp.pdf");
        downloadFile(pdfUrl, pdfFile);

        // Load PDF
        PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
        PdfWriter writer = new PdfWriter(outputPath);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);

        // Load image data once
        ImageData imageData = ImageDataFactory.create(new URL(imageUrl));

        // Define grid: 4 columns x 1 row (4 images per page)
        final int cols = 4;
        final int rows = 4;

        int totalPages = pdfDoc.getNumberOfPages();
        for (int p = 1; p <= totalPages; p++) {
            PdfPage page = pdfDoc.getPage(p);
            float pageW = page.getPageSize().getWidth();
            float pageH = page.getPageSize().getHeight();
            float cellW = pageW / cols;
            float cellH = pageH / rows;

            Canvas canvas = new Canvas(page, page.getPageSize());

            // Add images in grid
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Image img = new Image(imageData);
                    img.setOpacity(0.2f); // semi-transparent
                    img.scaleToFit(cellW * 0.9f, cellH * 0.9f); // fit inside cell
                    img.setRotationAngle(Math.toRadians(60)); // tilt left 30 degrees

                    float imgW = img.getImageScaledWidth();
                    float imgH = img.getImageScaledHeight();

                    // Center image in its cell
                    float x = c * cellW + (cellW - imgW) / 2f;
                    float y = pageH - (r + 1) * cellH + (cellH - imgH) / 2f;

                    img.setFixedPosition(p, x, y);
                    canvas.add(img);
                }
            }

            canvas.close();
        }

        pdfDoc.close();
        pdfFile.delete();
    }

    private void downloadFile(String urlStr, File outputFile) throws Exception {
        URL url = new URL(urlStr);
        try (java.io.InputStream in = url.openStream();
             java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
