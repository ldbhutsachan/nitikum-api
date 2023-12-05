package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.Document.DocumentAuditRes;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Document.DocumentRes;
import com.ldb.iadoc.Model.Document.Report.DocumentReportRes;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class DocumentController {
    public static final Logger log = LogManager.getLogger(LoginController.class);
    @Autowired
    private DocumentService documentService;
    @Autowired
    MediaUploadServiceImpl mediaUploadService;

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

    //===================read data ReadDoc=============
    @CrossOrigin(origins = "*")
    @PostMapping("/Report/getReportDocument")
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
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/Document/SaveDoc" , consumes = {"multipart/form-data"})
    public ReponeRes SaveDoc(
                    @RequestParam("filesEn") MultipartFile filesEn,
                    @RequestParam("filesLao") MultipartFile filesLao,
                    @RequestParam("docNo") String docNo,
                    @RequestParam("subjectName") String subjectName,
                    @RequestParam("docDate") String docDate,
                    @RequestParam("docType") String docType,
                    @RequestParam("related") String related,
                    @RequestParam("deptCode") String deptCode,
                    @RequestParam("shareUserById") String shareUserById,
                    @RequestParam("markerId") String markerId,
                    @RequestParam("sharingType") String sharingType,
                    @RequestParam("docStatus") String docStatus,
                    @RequestParam("details") String details
    ){
        log.info("====================================================>SaveDoc controller<=========================");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyss");
        String namefile = formatter.format(date);
        ReponeRes result = new ReponeRes();
        try{
            DocumentReq data = new DocumentReq();
            data.setDocNo(docNo);
            data.setSubjectName(subjectName);
            data.setDocDate(docDate);
            data.setDocType(docType);
            data.setRelated(related);
            data.setDeptCode(deptCode);
            data.setShareUserById(shareUserById);
            data.setMarkerId(markerId);
            data.setSharingType(sharingType);
            data.setDocStatus(docStatus);
            data.setDetails(details);
            String fileNameEn = "";
            String fileNameLa = "";
            List<String> fileNamesEn = new ArrayList<>();
            List<String> fileNamesLa = new ArrayList<>();
            //==========================ກວດສອບ ໄຟທີ 1==================================
            if(filesEn == null){
                log.warn("************* file EN is null ****************");
                data.setDocPath("");
            }
            else if(filesEn != null) {
                Arrays.asList(filesEn).stream().forEach(file -> {
                    fileNamesEn.add(mediaUploadService.uploadDirectoryDocEn(file));
                });
                log.info("Uploaded the files successfully: "+ fileNamesEn);
                fileNameEn = StringUtils.join(fileNamesEn, ',');
                data.setDocPath(fileNameEn);
            }
            //==========================ກວດສອບ ໄຟທີ 2==================================
            if(filesLao == null ){
                log.warn("************* file LAO is null ****************");
                data.setDocPathLa("");
            }else if(filesLao != null){
                log.warn("************* file LAO no null ****************");
                Arrays.asList(filesLao).stream().forEach(file -> {
                    fileNamesLa.add(mediaUploadService.uploadDirectoryDocLa(file));
                });
                fileNameLa = StringUtils.join(fileNamesLa, ',');
                data.setDocPathLa(fileNameLa);
            }
            result = documentService.SaveDocument(data);
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
    //*************report document
}
