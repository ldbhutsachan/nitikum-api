package com.ldb.iadoc.Contrller;
import org.springframework.beans.factory.annotation.Value;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.Document.*;
import com.ldb.iadoc.Model.Document.Report.DocumentReportRes;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.GroupHeaderReq;
import com.ldb.iadoc.Model.GroupHeaderRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Service.DocumentService;
import com.ldb.iadoc.Service.MediaUploadServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.util.Elements;
import javax.xml.crypto.Data;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class DocumentController {
    public static final Logger log = LogManager.getLogger(LoginController.class);
    @Autowired
    private DocumentService documentService;
    @Autowired
    MediaUploadServiceImpl mediaUploadService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Store uploads under a short, ASCII-only filename to avoid Linux filesystem
     * limits (255 bytes) when users upload long Lao filenames.
     */
    private static String buildTempFileName(UUID uuid, String originalFilename) {
        String ext = extractExtension(originalFilename);
        if (ext.isEmpty()) {
            return uuid.toString();
        }
        return uuid.toString() + "." + ext;
    }

    private static String extractExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        String name = originalFilename.trim();
        int dot = name.lastIndexOf('.');
        if (dot < 0 || dot == name.length() - 1) {
            return "";
        }
        String ext = name.substring(dot + 1).toLowerCase(Locale.ROOT);
        // Keep extension small and ASCII to avoid any path / header parsing surprises.
        ext = ext.replaceAll("[^a-z0-9]", "");
        if (ext.length() > 10) {
            ext = ext.substring(0, 10);
        }
        return ext;
    }

    /**
     * Raised when a file can't be saved locally, watermarked, or pushed to the
     * upload server. Carries a user-facing (Lao) message so SaveDoc can report
     * a proper status/message instead of silently swallowing the failure.
     */
    private static class DocumentFileUploadException extends RuntimeException {
        DocumentFileUploadException(String message) {
            super(message);
        }
        DocumentFileUploadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static ReponeRes buildResult(String resCode, String resMgs) {
        Message message = new Message();
        message.setResCode(resCode);
        message.setResMgs(resMgs);
        return new ReponeRes(message);
    }

    private static boolean isPdfFile(File file) {
        // PDF must start with "%PDF-" (0x25 0x50 0x44 0x46 0x2D)
        if (file == null || !file.isFile()) {
            return false;
        }
        try {
            Path p = file.toPath();
            if (Files.size(p) < 5) {
                return false;
            }
            byte[] header = new byte[5];
            try (java.io.InputStream in = Files.newInputStream(p)) {
                int read = in.read(header);
                if (read < 5) {
                    return false;
                }
            }
            return header[0] == '%' && header[1] == 'P' && header[2] == 'D' && header[3] == 'F' && header[4] == '-';
        } catch (Exception e) {
            return false;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/Audit/getAuditListCheck")
    public DocumentAuditRes getAuditListCheck(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getAuditListCheck controller<=========================");
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getAuditListCheck(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Audit/getWaitListCheckByUser")
    public DocumentAuditRes getWaitListCheckByUser(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getWaitListCheckByUser controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getWaitListCheckByUser(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocument")
    public DocumentAuditRes getShareDocument(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocument controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocument(documentReq);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/Share/genPdf")
    public DocumentAuditRes genPdf(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocument controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentGen(documentReq);
        return result;
    }
    //----------------------------Report
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocumentReport")
    public GroupHeaderRes getShareDocumentReport(@RequestBody GroupHeaderReq documentReq){
        log.info("POST /Share/getShareDocumentReport - startDate={}, endDate={}, related={}, status={}",
                documentReq.getStartDate(), documentReq.getEndDate(), documentReq.getRelated_Name(), documentReq.getStatus());
        return documentService.getShareDocumentReport(documentReq);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocumentReportText02")
    public GroupHeaderRes getShareDocumentReportText02(@RequestBody GroupHeaderReq documentReq){
        log.info("POST /Share/getShareDocumentReportText02 - startDate={}, endDate={}, related={}",
                documentReq.getStartDate(), documentReq.getEndDate(), documentReq.getRelated_Name());
        return documentService.getShareDocumentReportText02(documentReq);
    }
    //===============================================================> get data show meeting <===============================
    @CrossOrigin(origins = "*")
    @PostMapping("/Search/getShareDocumentByCondition")
    public DocumentAuditRes getShareDocumentByCondition(@RequestBody docSerachReq docSerachReq){
        log.info("====================================================>getShareDocumentByCondition controller<=========================");
        System.out.println("getIdYear:"+docSerachReq.getIdYear());
        System.out.println("getIddocType:"+docSerachReq.getIddocType());
        System.out.println("getIdqter:"+docSerachReq.getIdqter());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentByCondition(docSerachReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Search/getShareDocumentByConditionText")
    public DocumentAuditRes getShareDocumentByConditionText(@RequestBody docSerachReq docSerachReq){
        log.info("====================================================>getShareDocumentByConditionText controller<=========================");
        System.out.println("getIdYear:"+docSerachReq.getTextSearch());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentByConditionText(docSerachReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Search/getShareDocumentByConditionTextByTextSearch")
    public DocumentAuditRes getShareDocumentByConditionTextByTextSearch(@RequestBody docSerachReq docSerachReq){
        log.info("====================================================>getShareDocumentByConditionText controller<=========================");
        System.out.println("getIdYear:"+docSerachReq.getTextSearch());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentByConditionText(docSerachReq);
        return result;
    }

    //===================read data ReadDoc=============
    @CrossOrigin(origins = "*")
    @PostMapping("/document/getReportDocument")
    public DocumentReportRes getReportDocument(@RequestBody DocumentReq documentReq) throws ParseException {
        log.info("====================================================>ReadData controller<=========================");
        DocumentReportRes result = new DocumentReportRes();
        result = documentService.getReportDocument(documentReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Audit/AuditDoc")
    public ReponeRes AuditDoc(@RequestBody DocumentReq documentReq){
        log.info("====================================================>AuditDoc controller<=========================");
        ReponeRes result = new ReponeRes();
        result = documentService.audit_doc(documentReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Audit/rejectDocument")
    public ReponeRes rejectDocument(@RequestBody DocumentReq documentReq){
        log.info("====================================================>rejectDocument controller<=========================");
        ReponeRes result = new ReponeRes();
        result = documentService.rejectDocument(documentReq);
        return  result;
    }
    /**
     * Saves one language's files (local save -> optional watermark -> upload) and
     * returns the uploaded paths. Throws DocumentFileUploadException the moment any
     * step fails, so SaveDoc can stop and report a real status/message instead of
     * silently continuing with a partially-saved document.
     */
    private List<String> processFilesOrThrow(MultipartFile[] files, String label) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            try {
                // Step 1: Save original file temporarily
                UUID uuid = UUID.randomUUID();
                String safeFileName = buildTempFileName(uuid, originalFilename);
                File targetFile = new File(uploadDir, safeFileName);
                targetFile.getParentFile().mkdirs(); // ensure directory exists
                file.transferTo(targetFile);

                File fileToUpload = targetFile;
                String ext = extractExtension(originalFilename);
                if ("pdf".equals(ext) && isPdfFile(targetFile)) {
                    try {
                        // Step 2: Generate watermarked PDF
                        String outputPath = System.getProperty("java.io.tmpdir") + File.separator + uuid + "-gen.pdf";
                        fileToUpload = mediaUploadService.genPDFS(targetFile.getAbsolutePath(), outputPath);
                    } catch (Exception watermarkEx) {
                        // If the file isn't a valid PDF or iText fails, still upload original.
                        log.warn("Watermark failed for {} file {}, uploading original instead", label, originalFilename, watermarkEx);
                        fileToUpload = targetFile;
                    }
                } else if ("pdf".equals(ext)) {
                    log.warn("{} file has .pdf extension but is not a valid PDF header: {}", label, originalFilename);
                }

                // Step 3: Upload (generated PDF if watermark succeeded, otherwise original).
                // uploadDirectoryDocLaGen throws (with the real cause attached) on any
                // failure - network error, timeout, or non-2xx response from the upload
                // server - so it's handled below by the generic catch, which preserves
                // that cause for logging instead of reporting a blank/unknown failure.
                String uploadedPath = mediaUploadService.uploadDirectoryDocLaGen(fileToUpload, uuid);
                if (uploadedPath == null || uploadedPath.isBlank()) {
                    throw new DocumentFileUploadException(
                            "ອັບໂຫລດໄຟລ໌ບໍ່ສໍາເລັດ (" + label + "): " + originalFilename);
                }
                fileNames.add(uploadedPath);

            } catch (DocumentFileUploadException e) {
                throw e;
            } catch (Exception e) {
                log.error("Error processing {} file {}", label, originalFilename, e);
                throw new DocumentFileUploadException(
                        "ອັບໂຫລດໄຟລ໌ບໍ່ສໍາເລັດ (" + label + "): " + originalFilename, e);
            }
        }
        return fileNames;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/Document/SaveDoc" , consumes = {"multipart/form-data"})
    public ReponeRes SaveDoc(
                    @RequestParam(name="filesLao" , required=false) MultipartFile[] filesLao,
                    @RequestParam(name="filesEn" , required=false) MultipartFile[] filesEn,
                    @RequestParam("docNo") String docNo,
                    @RequestParam("subjectName") String subjectName,
                    @RequestParam("docDate") String docDate,
                    @RequestParam("docType") String docType,
                    @RequestParam("related") String related,///------------------------ຜ່າຍທີ່ຕ້ອງການເຜີຍເເຜ່
                    @RequestParam("deptCode") String deptCode,
                    @RequestParam("shareUserById") String shareUserById,
                    @RequestParam("markerId") String markerId,
                    @RequestParam("sharingType") String sharingType,
                    @RequestParam("details") String details,
                    @RequestParam("related_No") String related_No,///------------------------ພາກສ່ວນຮັບຜິດຊອບ
                    @RequestParam("related_Name") String related_Name,
                    @RequestParam("type") String type,
                    @RequestParam(value = "old_image1", required = false) String  old_image1,
                    @RequestParam(value = "old_image2", required = false) String  old_image2
    ){
        log.info("POST /Document/SaveDoc - docNo={}, subjectName={}, sharingType={}", docNo, subjectName, sharingType);
        try {
            DocumentReq data = new DocumentReq();
            data.setRelated_No(related_No);
            data.setRelated_Name(related_Name);
            data.setDocNo(docNo);
            data.setSubjectName(subjectName);
            data.setDocDate(docDate);
            data.setDocType(docType);
            data.setRelated(related);
            data.setDeptCode(deptCode);
            data.setShareUserById(shareUserById);
            data.setMarkerId(markerId);
            data.setSharingType(sharingType);
            data.setDetails(details);
            data.setType(type);

            //==========================ກວດສອບ ໄຟທີ 1==================================
            if (filesEn == null || filesEn.length == 0) {
                log.warn("SaveDoc: no EN files uploaded, reusing old_image1");
                data.setDocPath(old_image1);
            } else {
                log.info("SaveDoc: processing {} EN file(s)", filesEn.length);
                List<String> fileNamesEn = processFilesOrThrow(filesEn, "EN");
                data.setDocPath(fileNamesEn.isEmpty() ? null : String.join(",", fileNamesEn));
            }

            //   ==========================ກວດສອບ ໄຟທີ 2==================================
            if (filesLao == null || filesLao.length == 0) {
                log.warn("SaveDoc: no LAO files uploaded, reusing old_image2");
                data.setDocPathLa(old_image2);
            } else {
                log.info("SaveDoc: processing {} LAO file(s)", filesLao.length);
                List<String> fileNamesLa = processFilesOrThrow(filesLao, "LAO");
                data.setDocPathLa(fileNamesLa.isEmpty() ? null : String.join(",", fileNamesLa));
            }

            // Doc key generation now happens inside SaveDocument itself, under the same
            // lock/transaction as the insert that consumes it - see SaveDocument's javadoc.
            return documentService.SaveDocument(data);

        } catch (DocumentFileUploadException e) {
            // Couldn't save/upload one of the files to the file server: report it clearly
            // instead of silently continuing or returning an empty response.
            log.error("SaveDoc failed: {}", e.getMessage(), e);
            return buildResult(Constant.codeError, e.getMessage());
        } catch (Exception e) {
            log.error("SaveDoc failed with an unexpected error", e);
            return buildResult(Constant.codeError, Constant.msgFailSave);
        }
    }
    //***************************************************update data for document *************************************
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/Document/updateDocument" , consumes = {"multipart/form-data"})
    public ReponeRes updateDocument(
            @RequestParam(name="filesLao" , required=false) MultipartFile[] filesLao,
            @RequestParam(name="filesEn" , required=false) MultipartFile[] filesEn,
            @RequestParam("docNo") String docNo,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("docDate") String docDate,
            @RequestParam("docType") String docType,
            @RequestParam("related") String related,
            @RequestParam("markerId") String markerId,
            @RequestParam("type") String type,
            @RequestParam("sharingType") String sharingType,
            @RequestParam("details") String details,
            @RequestParam("related_Name") String related_Name,
            @RequestParam("ses_status") String ses_status,
            @RequestParam("w_status_show") String w_status_show,
            @RequestParam("id") String id,
            @RequestParam(value = "old_image1", required = false) String  old_image1,
            @RequestParam(value = "old_image2", required = false) String  old_image2
    ){
        log.info("==============>update document controller<==================");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyss");
        String namefile = formatter.format(date);
        ReponeRes result = new ReponeRes();
        try{
            String filesLaoPDF = "dataLao";
            String filesEnPDF = "dataEn";
            if (filesLao == null || filesLao.length == 0) {
                 filesLaoPDF = "lo";
            }
            if (filesEn == null || filesEn.length == 0) {
                 filesEnPDF = "en";
            }
            DocumentReq data = new DocumentReq();
            String a1 = "0";
            if (docNo =="" || docNo.equals("")){
                data.setDocNo(a1);
            }
            else if (subjectName == "" || subjectName.equals("")){
                data.setSubjectName(a1);
            }
            else if (docDate == "" || docDate.equals("")){
                data.setDocNo(a1);
            }
            else if (docDate == "" || docDate.equals("")){
                data.setDocDate("");
            }
            else if (details == "" || details.equals("")){
                data.setDetails(a1);
            }
           //data.setRelated_No(related_No);
            data.setRelated_Name(related_Name);
            data.setDocNo(docNo);
            data.setSubjectName(subjectName);
            data.setDocDate(docDate);
            data.setDocType(docType);
            data.setRelated(related);
            data.setMarkerId(markerId);
            data.setSharingType(sharingType);
            data.setDetails(details);
            data.setSes_status(ses_status);
            data.setW_status_show(w_status_show);
            data.setId(id);
            data.setType(type);
            String fileNameEn = "";
            String fileNameLa = "";
            List<String> fileNamesEn = new ArrayList<>();
            List<String> fileNamesLa = new ArrayList<>();

            //==========================ກວດສອບ ໄຟທີ 1==================================
            if (filesEn == null) {
                log.warn("************* file EN is null ****************");
                data.setDocPath(old_image1);
            } else {
                log.info("************* processing EN files ****************");
                for (MultipartFile file : filesEn) {
                    try {
                        // Step 1: Save original file temporarily
                        UUID uuid = UUID.randomUUID();
                        String safeFileName = buildTempFileName(uuid, file.getOriginalFilename());
                        // Step 1: Save original file temporarily

                        File targetFile = new File(uploadDir, safeFileName);
                        targetFile.getParentFile().mkdirs(); // ensure directory exists
                        file.transferTo(targetFile);

                        File fileToUpload = targetFile;
                        String ext = extractExtension(file.getOriginalFilename());
                        if ("pdf".equals(ext) && isPdfFile(targetFile)) {
                            try {
                                String outputPath = System.getProperty("java.io.tmpdir") + File.separator + uuid + "-gen.pdf";
                                fileToUpload = mediaUploadService.genPDFS(targetFile.getAbsolutePath(), outputPath);
                            } catch (Exception watermarkEx) {
                                log.warn("Watermark failed for EN file {}, uploading original instead", file.getOriginalFilename(), watermarkEx);
                                fileToUpload = targetFile;
                            }
                        } else if ("pdf".equals(ext)) {
                            log.warn("EN file has .pdf extension but is not a valid PDF header: {}", file.getOriginalFilename());
                        }

                        fileNamesEn.add(mediaUploadService.uploadDirectoryDocLaGen(fileToUpload, uuid));

                    } catch (Exception e) {
                        log.error("Error processing EN file {}", file.getOriginalFilename(), e);
                    }
                }

                if (fileNamesEn.isEmpty()) {
                    data.setDocPath(null);
                } else {
                    data.setDocPath(String.join(",", fileNamesEn));
                }

            }
            //   ==========================ກວດສອບ ໄຟທີ 2==================================
            if (filesLao == null) {
                log.warn("************* file LAO is null ****************");
                data.setDocPathLa(old_image2);
            } else {
                log.info("************* processing Lao files ****************");
                for (MultipartFile file : filesLao) {
                    try {
                        // Step 1: Save original file temporarily
                        UUID uuid = UUID.randomUUID();
                        String safeFileName = buildTempFileName(uuid, file.getOriginalFilename());
                        // Step 1: Save original file temporarily

                        File targetFile = new File(uploadDir, safeFileName);
                        targetFile.getParentFile().mkdirs(); // ensure directory exists
                        file.transferTo(targetFile);

                        File fileToUpload = targetFile;
                        String ext = extractExtension(file.getOriginalFilename());
                        if ("pdf".equals(ext) && isPdfFile(targetFile)) {
                            try {
                                String outputPath = System.getProperty("java.io.tmpdir") + File.separator + uuid + "-gen.pdf";
                                fileToUpload = mediaUploadService.genPDFS(targetFile.getAbsolutePath(), outputPath);
                            } catch (Exception watermarkEx) {
                                log.warn("Watermark failed for Lao file {}, uploading original instead", file.getOriginalFilename(), watermarkEx);
                                fileToUpload = targetFile;
                            }
                        } else if ("pdf".equals(ext)) {
                            log.warn("Lao file has .pdf extension but is not a valid PDF header: {}", file.getOriginalFilename());
                        }

                        fileNamesLa.add(mediaUploadService.uploadDirectoryDocLaGen(fileToUpload, uuid));

                    } catch (Exception e) {
                        log.error("Error processing Lao file {}", file.getOriginalFilename(), e);
                    }
                }

                if (fileNamesLa.isEmpty()) {
                    data.setDocPathLa(null);
                } else {
                    data.setDocPathLa(String.join(",", fileNamesLa));
                }

            }




            List<KeyReq> rspListData = documentService.getMaxKey();
            String keyDocNo  = rspListData.get(0).getKeyDocNo();
            result = documentService.updateDocument(data,filesLaoPDF,filesEnPDF,keyDocNo);
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                System.out.println("ArrayIndexOutOfBoundsException occurred");
            } else {
                System.out.println("An exception occurred: " + e.getClass().getSimpleName());
            }
            String errorMessage = e.getMessage();
            System.out.println("Error message: " + errorMessage);
            e.printStackTrace();
        }
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Document/updateStatusShow")
    public ReponeRes updateStatusShow(@RequestBody StatusShowReq documentReq){
        ReponeRes result = new ReponeRes();
        try{
           result = documentService.updateDocumentStatusShow(documentReq);
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                System.out.println("ArrayIndexOutOfBoundsException occurred");
            } else {
                System.out.println("An exception occurred: " + e.getClass().getSimpleName());
            }
            String errorMessage = e.getMessage();
            System.out.println("Error message: " + errorMessage);
            e.printStackTrace();
        }
        return result;
    }

    //*************report document
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocumentSubject")
    public DocumentAuditRes getShareDocumentSubject(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocumentSubject controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        System.out.println("markerId:"+documentReq.getSubjectName());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentSubject(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocumentKanang")
    public DocumentAuditRes getShareDocumentKanang(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocumentKanang controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        System.out.println("getSecCode:"+documentReq.getSecCode());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentKanang(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getDocumentPopUp")
    public DocumentAuditRes getDocumentPopUp(@RequestBody DocumentReq documentReq){
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getDocumentPopUp(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/getShareDocumentDoctype")
    public DocumentAuditRes getShareDocumentDoctype(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocumentDoctype controller<=========================");
        System.out.println("getMarkerId:"+documentReq.getMarkerId());
        System.out.println("getDocType:"+documentReq.getDocNo());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentDoctype(documentReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Share/ReadyDoc")
    public ReponeRes ReadDoc(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocumentDoctype controller<=========================");
        System.out.println("getReadBy:"+documentReq.getReadBy());
        System.out.println("getId:"+documentReq.getId());
        ReponeRes result =new ReponeRes();
        result = documentService.ReadDoc(documentReq);
        return result;
    }

}
