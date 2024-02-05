package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranchRes;
import com.ldb.iadoc.Model.Document.DocumentAuditRes;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionRes;
import com.ldb.iadoc.Model.Section.ExcusiveSection.ComboSectionExReq;
import com.ldb.iadoc.Service.DocumentService;
import com.ldb.iadoc.Service.LoginService;
import com.ldb.iadoc.Service.MediaUploadServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class DocumentExtiveController {
    public static final Logger log = LogManager.getLogger(DocumentExtiveController.class);
    @Autowired
    private DocumentService documentService;
    @Autowired
    MediaUploadServiceImpl mediaUploadService;
    @Autowired
    private LoginService loginService;
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getComboxBranchExcutive")
    public ComboBranchRes getComboxBranchExcutive(){
        log.info("====================================================>getComboxBranchExcutive controller<=========================");
        ComboBranchRes result = new ComboBranchRes();
        result = loginService.getComboxBranchExcutive();
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/getComboxSectionsExcutive")
    public ComboSectionRes getComboxSectionsExcutive(@RequestBody ComboSectionExReq sectionReq){
        log.info("====================================================>getComboxSectionsExcutive controller<=========================");
        ComboSectionRes result =new ComboSectionRes();
        result = loginService.getComboxSectionsExcutive(sectionReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/getComboxDeptExcutive")
    public ComboSectionRes getComboxDeptExcutive(@RequestBody ComboSectionExReq sectionReq){
        log.info("====================================================>getComboxDeptExcutive controller<=========================");
        ComboSectionRes result =new ComboSectionRes();
        result = loginService.getComboxDeptExcutive(sectionReq);
        return result;
    }
    //******************************save document
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/Document/SaveDocExcutive" , consumes = {"multipart/form-data"})
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
            @RequestParam("taiMard") String taiMard,
            @RequestParam("yearIn") String yearIn,
            @RequestParam("conName") String conName,
            @RequestParam("conName2") String conName2,
            @RequestParam(value = "old_image1", required = false) String  old_image1,
            @RequestParam(value = "old_image2", required = false) String  old_image2
    ){
        log.info("====================================================>SaveDoc controller<=========================");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyss");
        String namefile = formatter.format(date);
        ReponeRes result = new ReponeRes();
        try{
            DocumentReq data = new DocumentReq();
            data.setConName2(conName2);
            data.setTaiMard(taiMard);
            data.setYearIn(yearIn);
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
            data.setConName(conName);
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
            result = documentService.SaveDocExcutive(data);
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
    @PostMapping(value = "/Document/updateDocExcutive" , consumes = {"multipart/form-data"})
    public ReponeRes updateDoc(

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
            @RequestParam("taiMard") String taiMard,
            @RequestParam("yearIn") String yearIn,
            @RequestParam("conName") String conName,
            @RequestParam("id") String id,
            @RequestParam(value = "old_image1", required = false) String  old_image1,
            @RequestParam(value = "old_image2", required = false) String  old_image2
    ){
        log.info("====================================================>UPDATE controller<=========================");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyyss");
        String namefile = formatter.format(date);
        ReponeRes result = new ReponeRes();
        try{
            log.info("lo:"+filesLao);
            DocumentReq data = new DocumentReq();
            // data.setConnects(connects);
            data.setTaiMard(taiMard);
            data.setYearIn(yearIn);
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
            data.setConName(conName);
            data.setId(id);
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
            result = documentService.updateDocExcutive(data);
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
    @PostMapping("/Audit/getWaitListCheckExcutive")
    public DocumentAuditRes getWaitListCheckExcutive(@RequestBody DocumentReq documentReq){
        log.info("====================================================>getWaitListCheckExcutive controller<=========================");
        System.out.println("markerId:"+documentReq.getMarkerId());
        DocumentAuditRes result =new DocumentAuditRes();
        result = documentService.getWaitListCheckExcutive(documentReq);
        return result;
    }
}
