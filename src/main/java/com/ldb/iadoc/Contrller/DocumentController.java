package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.Document.DocumentAuditRes;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Document.DocumentRes;
import com.ldb.iadoc.Model.Document.Report.DocumentReportRes;
import com.ldb.iadoc.Model.Document.docSerachReq;
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
                    @RequestParam(name="filesLao" , required=false) MultipartFile[] filesLao,
                    @RequestParam(name="filesEn" , required=false) MultipartFile[] filesEn,
                    @RequestParam("docNo") String docNo,
                    @RequestParam("subjectName") String subjectName,
                    @RequestParam("docDate") String docDate,
                    @RequestParam("docType") String docType,
                    @RequestParam("related") String related,
                    @RequestParam("deptCode") String deptCode,
                    @RequestParam("shareUserById") String shareUserById,
                    @RequestParam("markerId") String markerId,
                    @RequestParam("sharingType") String sharingType,
                    @RequestParam("details") String details,
                    @RequestParam(value = "old_image1", required = false) String  old_image1,
                    @RequestParam(value = "old_image2", required = false) String  old_image2
    ){
        log.info("====================================================>SaveDoc controller<=========================");
        log.info("show fileEn:"+filesEn);
        log.info("show fileLA:"+filesLao);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyss");
        String namefile = formatter.format(date);
        ReponeRes result = new ReponeRes();
        try{
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
            String fileNameEn = "";
            String fileNameLa = "";
            List<String> fileNamesEn = new ArrayList<>();
            List<String> fileNamesLa = new ArrayList<>();
            //==========================ກວດສອບ ໄຟທີ 1==================================
            if(filesEn == null){
                log.warn("************* file EN is null ****************");
                data.setDocPath(old_image1);
            }
            else if(filesEn != null) {
                Arrays.asList(filesEn).stream().forEach(file -> {
                    fileNamesEn.add(mediaUploadService.uploadDirectoryDocEn(file));
                });
                log.info("Uploaded the files successfully: "+ fileNamesEn);
                fileNameEn = StringUtils.join(fileNamesEn, ',');
                data.setDocPath(fileNameEn);
            }
         //   ==========================ກວດສອບ ໄຟທີ 2==================================
            if(filesLao == null ){
                log.warn("************* file LAO is null ****************");
                data.setDocPathLa(old_image2);
            }else if(filesLao != null){
                log.warn("************* file LAO no null ****************");
                Arrays.asList(filesLao).stream().forEach(file -> {
                    fileNamesLa.add(mediaUploadService.uploadDirectoryDocLa(file));
                });
                fileNameLa = StringUtils.join(fileNamesLa, ',');
                data.setDocPathLa(fileNameLa);
            }
//                List<String> fileNamesEns = new ArrayList<>();
//                if(filesLao == null){
//                    log.warn("************* file name is null ****************");
//                    data.setDocPathLa(old_image2);
//                }else {
//                    Arrays.asList(filesLao).stream().forEach(file -> {
//                        //filesStorageService.save(file);
//                        fileNamesEns.add(mediaUploadService.uploadDirectoryDocLa(file));
//                    });
//                    log.info("Uploaded the files successfully: " + fileNamesEns );
//                    fileNamesEns.add(old_image2);
//
//                    fileNameEn = StringUtils.join(fileNamesEns, ',');
//                    data.setDocPathLa(fileNameEn);
//                }
//            List<String> fileNamesLas = new ArrayList<>();
//            if(filesLao == null){
//                log.warn("************* file name is null ****************");
//                data.setDocPathLa(old_image2);
//            }else {
//                Arrays.asList(filesLao).stream().forEach(file -> {
//                    //filesStorageService.save(file);
//                    fileNamesLas.add(mediaUploadService.uploadDirectoryDocLa(file));
//                });
//                log.info("Uploaded the files successfully: " + fileNamesLas );
//                fileNamesLas.add(old_image2);
//
//                fileNameLa = StringUtils.join(fileNamesLas, ',');
//                data.setDocPathLa(fileNameLa);
//            }
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
    @PostMapping("/Share/getShareDocumentDoctype")
    public DocumentAuditRes getShareDocumentDoctype(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getShareDocumentDoctype controller<=========================");
        System.out.println("getMarkerId:"+documentReq.getMarkerId());
        System.out.println("getDocType:"+documentReq.getDocType());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getShareDocumentDoctype(documentReq);
        return result;
    }
}
