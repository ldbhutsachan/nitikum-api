package com.ldb.iadoc.Service;

import com.ldb.iadoc.Dao.DocumentDao.DocumentImpl;
import com.ldb.iadoc.Dao.Login.LoginImpl;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.Branch.Branch;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Document.*;
import com.ldb.iadoc.Model.Document.Report.DocumentReportRes;
import com.ldb.iadoc.Model.GroupHeaderReq;
import com.ldb.iadoc.Model.GroupHeaderRes;
import com.ldb.iadoc.Model.Login.Login;
import com.ldb.iadoc.Model.Relation.Related;
import com.ldb.iadoc.Model.Relation.RelatedShow;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.GroupHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    public static final Logger log = LogManager.getLogger(DocumentService.class);
    @Autowired
    DocumentImpl documentImpl;
    @Autowired
    LoginImpl loginService;
    public ReponeRes SaveDocument(DocumentReq documentReq) throws ParseException {
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        int checkSharing = 0;
        if(documentReq.getDocDate().equals("")){
            documentReq.setDocDate(documentReq.getDocDate());
        }else {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date inputDate = inputDateFormat.parse(documentReq.getDocDate());
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            String outputDateStr = outputDateFormat.format(inputDate);
            documentReq.setDocDate(outputDateStr);
        }
         if(documentReq.getSharingType().equals("V")){
            log.info("User:"+documentReq.getSharingType());
             check= documentImpl.SaveDocument(documentReq);
            checkSharing= documentImpl.saveSharingDoBranch(documentReq);
            //********************insert section for share by array data *******************
            documentImpl.saveRedNo(documentReq);
            documentImpl.DOC_CREATE_TEMP(documentReq);
        }else {
             check= documentImpl.SaveDocument(documentReq);
             checkSharing = documentImpl.saveSharingDoBranchNoarray(documentReq);
             //********************insert section for share by array data *******************
             documentImpl.saveRedNo(documentReq);
             documentImpl.DOC_CREATE_TEMP(documentReq);
             log.info("*********************** :no array: ************************");
         }
        try {
            if (check > 0 && checkSharing > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFail);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    //***************************************update document ***********************************************************
    public ReponeRes updateDocument(DocumentReq documentReq) throws ParseException {
        log.info("show relatedName: {}"+documentReq.getRelated_Name());
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        int checkSharing = 0;
        if(documentReq.getDocDate().equals("")){
            documentReq.setDocDate(documentReq.getDocDate());
        }else {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date inputDate = inputDateFormat.parse(documentReq.getDocDate());
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            String outputDateStr = outputDateFormat.format(inputDate);
            documentReq.setDocDate(outputDateStr);
        }
        if(documentReq.getSharingType().equals("V")){
            //************clear share data frist ********************************delete DOC_SHARING from where DOC_TYPE=?
            documentImpl.clearSharingDataFrist(documentReq);
            //************then let to update document  ********************************
            check= documentImpl.upDateDocument(documentReq);
            checkSharing= documentImpl.saveSharingDoBranch(documentReq);
            log.info("update data sone case 01");
        }else {
            //************clear share data frist ********************************delete DOC_SHARING from where DOC_TYPE=?
            documentImpl.clearSharingDataFrist(documentReq);

            check= documentImpl.upDateDocument(documentReq);
            checkSharing = documentImpl.saveSharingDoBranchNoarray(documentReq);
            log.info("update data sone case 02");
        }
        try {
            if (check > 0 && checkSharing > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFail);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public ReponeRes updateDocumentStatusShow(StatusShowReq documentReq) throws ParseException {
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        try {
            check = documentImpl.updateStatusShow(documentReq);
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFail);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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

    public ReponeRes SaveDocExcutive(DocumentReq documentReq) throws ParseException {
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        check= documentImpl.SaveDocumentExcutive(documentReq);
        try {
            log.info("check:"+check);
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFail);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public ReponeRes updateDocExcutive(DocumentReq documentReq) throws ParseException {
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        check= documentImpl.updateDocExcutive(documentReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgUpdate);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFailUpdate);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public ReponeRes updateDocExcutiveNoFile(DocumentReq documentReq) throws ParseException {
        ReponeRes result = new ReponeRes();
        Message message = new Message();
        int check = 0;
        check= documentImpl.updateDocExcutiveNofile(documentReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgUpdate);
                result.setMessage(message);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgFailUpdate);
                result.setMessage(message);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getAuditListCheck(DocumentReq documentReq) {
        DocumentAuditRes result = new DocumentAuditRes();
        Message message = new Message();

        try {
            // Retrieve the list of DocumentAudit and Related entities
            List<DocumentAudit> auditList = documentImpl.getAuditDocument(documentReq);
            List<Related> relatedList = documentImpl.getRsplistRelated();
            List<RelatedShow> relatedShowList = documentImpl.getRsplistRelatedShow();
            // Loop through the auditList to add related items
            for (DocumentAudit audit : auditList) {
                // Filter related items that match the docNo of the current audit
                List<Related> matchingRelatedItems = relatedList.stream()
                        .filter(r -> r.getDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());

                // Set the relatedList for the current audit
                audit.setRelatedList(matchingRelatedItems);

                List<RelatedShow> relatedShows = relatedShowList.stream()
                        .filter(r -> r.getRelatedShowDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedShowList02(relatedShows);
            }

            // Populate the result
            result.setResData(auditList);
            message.setResMgs("ສໍາເລັດ");
            message.setResCode("00");
            result.setMessage(message);

        } catch (Exception e) {
            log.error("Error in getAuditListCheck: ", e);
            message.setResMgs("Error: " + e.getMessage());
            message.setResCode("99");
            result.setMessage(message);
        }

        return result;
    }
    public DocumentAuditRes getWaitListCheckByUser(DocumentReq documentReq) {
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();

        try {
            // Retrieve the lists, with null checks
            List<DocumentAudit> listData = documentImpl.getWaitListCheckByUser(documentReq);
            List<Related> relatedList = documentImpl.getRsplistRelated();
            List<RelatedShow> relatedShowList = documentImpl.getRsplistRelatedShow();
            // Map related items to each audit
            for (DocumentAudit audit : listData) {
                List<Related> matchingRelatedItems = relatedList.stream()
                        .filter(r -> r.getDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedList(matchingRelatedItems);

                // Check if relatedShowList is empty or null
                List<RelatedShow> relatedShows = relatedShowList.stream()
                        .filter(r -> r.getRelatedShowDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedShowList02(relatedShows);
            }
            // Populate the result
            result.setResData(listData);
            message.setResMgs("ສໍາເລັດ");
            message.setResCode("00");
            result.setMessage(message);
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage(), e);
            message.setResMgs("An error occurred while processing the request.");
            message.setResCode("99");
            result.setMessage(message);
        }

        return result;
    }

    private void handleException(Exception e) {
        if (e instanceof NullPointerException) {
            log.error("NullPointerException occurred", e);
        } else if (e instanceof IllegalArgumentException) {
            log.error("IllegalArgumentException occurred", e);
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            log.error("ArrayIndexOutOfBoundsException occurred", e);
        } else {
            log.error("An unexpected exception occurred: {}", e.getClass().getSimpleName(), e);
        }
        e.printStackTrace();
    }


    public DocumentAuditRes getWaitListCheckExcutive(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getWaitListCheckExcutive(documentReq);
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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

    public GroupHeaderRes getShareDocumentReport(GroupHeaderReq documentReq){
        Message message = new Message();
        GroupHeaderRes result = new GroupHeaderRes();
        List<DocumentAudit> listData = new ArrayList<>();
            listData = documentImpl.getShareDocumentReport(documentReq);
        List<Related> relatedList = documentImpl.getRsplistBranCh();

        List<DocumentAudit> resDataItems = new ArrayList<>();
        List<String> refIds = listData.stream()
                .map(DocumentAudit::getRelated_Name)
                .distinct()
                .collect(Collectors.toList());

        List<GroupHeader> headers = new ArrayList<>();
        for (String reNo : refIds) {
            GroupHeader groupHeader = new GroupHeader();
            String matchingRelatedName = relatedList.stream()
                    .filter(r -> r.getRelatedId() != null && r.getRelatedId().equals(reNo)) // compare the `RelatedId` with `reNo`
                    .map(Related::getRelatedName) // Assuming `Related` has a method `getRelatedName()`
                    .findFirst()
                    .orElse("Unknown Branch"); // Default to "Unknown Branch" if no match is found
            groupHeader.setRelated_Name(matchingRelatedName);
            headers.add(groupHeader);
            resDataItems = new ArrayList<>();
            for (DocumentAudit rspList : listData) {
                if(rspList.getRelated_Name().equals(reNo)) {
                    DocumentAudit rsShow = new DocumentAudit();
                    rsShow.setRelated_Name(rspList.getRelated_Name());
                    rsShow.setConnects(rspList.getConnects());
                    rsShow.setTaiMard(rspList.getTaiMard());
                    rsShow.setYearIn(rspList.getYearIn());
                    rsShow.setTaiMardDes(rspList.getTaiMardDes());
                    rsShow.setYearInDes(rspList.getYearInDes());
                    rsShow.setId(rspList.getId());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setApproveDate(rspList.getApproveDate());
                    rsShow.setDocNo(rspList.getDocNo());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setRelated(rspList.getRelated());
                    rsShow.setDepDescEN(rspList.getDepDescEN());
                    rsShow.setDepDescLAO(rspList.getDepDescLAO());
                    rsShow.setDocPath(rspList.getDocPath());
                    rsShow.setCreateDate(rspList.getCreateDate());
                    rsShow.setMarkerId(rspList.getMarkerId());
                    rsShow.setUserName(rspList.getUserName());
                    rsShow.setDocType(rspList.getDocType());
                    rsShow.setDocDescEn(rspList.getDocDescEn());
                    rsShow.setDocDescLao(rspList.getDocDescLao());
                    rsShow.setDocStatus(rspList.getDocStatus());
                    rsShow.setSharingType(rspList.getSharingType());
                    rsShow.setDocPathLa(rspList.getDocPathLa());
                    rsShow.setDocDate(rspList.getDocDate());
                    rsShow.setCreateBy(rspList.getCreateBy());
                    resDataItems.add(rsShow);
                }
            }
            groupHeader.setDetails(resDataItems);
        }
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public GroupHeaderRes getShareDocumentReportText(GroupHeaderReq documentReq){
        Message message = new Message();
        GroupHeaderRes result = new GroupHeaderRes();
        List<DocumentAudit> listData = new ArrayList<>();
        listData = documentImpl.getShareDocumentReport(documentReq);
        List<String> refIds = listData.stream().map(DocumentAudit::getRelated_Name).distinct().collect(Collectors.toList());
        GroupHeader groupHeader = new GroupHeader();
        List<GroupHeader> headers = new ArrayList<>();
        List<DocumentAudit> resDataItems = new ArrayList<>();
        for (String reNo : refIds){
            groupHeader = new GroupHeader();
            groupHeader.setRelated_Name(listData.stream().filter(p -> p.getRelated_Name().equals(reNo)).map(DocumentAudit::getRelated_Name).findFirst().orElse(""));
            headers.add(groupHeader);
            resDataItems = new ArrayList<>();
            for (DocumentAudit rspList : listData) {
                if(rspList.getRelated_Name().equals(reNo)) {
                    DocumentAudit rsShow = new DocumentAudit();
                    rsShow.setRelated_Name(rspList.getRelated_Name());
                    rsShow.setConnects(rspList.getConnects());
                    rsShow.setTaiMard(rspList.getTaiMard());
                    rsShow.setYearIn(rspList.getYearIn());
                    rsShow.setTaiMardDes(rspList.getTaiMardDes());
                    rsShow.setYearInDes(rspList.getYearInDes());
                    rsShow.setId(rspList.getId());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setApproveDate(rspList.getApproveDate());
                    rsShow.setDocNo(rspList.getDocNo());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setRelated(rspList.getRelated());
                    rsShow.setDepDescEN(rspList.getDepDescEN());
                    rsShow.setDepDescLAO(rspList.getDepDescLAO());
                    rsShow.setDocPath(rspList.getDocPath());
                    rsShow.setCreateDate(rspList.getCreateDate());
                    rsShow.setMarkerId(rspList.getMarkerId());
                    rsShow.setUserName(rspList.getUserName());
                    rsShow.setDocType(rspList.getDocType());
                    rsShow.setDocDescEn(rspList.getDocDescEn());
                    rsShow.setDocDescLao(rspList.getDocDescLao());
                    rsShow.setDocStatus(rspList.getDocStatus());
                    rsShow.setSharingType(rspList.getSharingType());
                    rsShow.setDocPathLa(rspList.getDocPathLa());
                    rsShow.setDocDate(rspList.getDocDate());
                    rsShow.setCreateBy(rspList.getCreateBy());
                    resDataItems.add(rsShow);
                }
            }
            groupHeader.setDetails(resDataItems);
        }
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    //****************
    public GroupHeaderRes getShareDocumentReportText02(GroupHeaderReq documentReq){
        Message message = new Message();
        GroupHeaderRes result = new GroupHeaderRes();
        List<DocumentAudit> listData = new ArrayList<>();
        listData = documentImpl.getShareDocumentReportByText(documentReq);
        List<String> refIds = listData.stream().map(DocumentAudit::getRelated_Name).distinct().collect(Collectors.toList());
        GroupHeader groupHeader = new GroupHeader();
        List<GroupHeader> headers = new ArrayList<>();
        List<DocumentAudit> resDataItems = new ArrayList<>();
        for (String reNo : refIds){
            groupHeader = new GroupHeader();
            groupHeader.setRelated_Name(listData.stream().filter(p -> p.getRelated_Name().equals(reNo)).map(DocumentAudit::getRelated_Name).findFirst().orElse(""));
            headers.add(groupHeader);
            resDataItems = new ArrayList<>();
            for (DocumentAudit rspList : listData) {
                if(rspList.getRelated_Name().equals(reNo)) {
                    DocumentAudit rsShow = new DocumentAudit();
                    rsShow.setRelated_Name(rspList.getRelated_Name());
                    rsShow.setConnects(rspList.getConnects());
                    rsShow.setTaiMard(rspList.getTaiMard());
                    rsShow.setYearIn(rspList.getYearIn());
                    rsShow.setTaiMardDes(rspList.getTaiMardDes());
                    rsShow.setYearInDes(rspList.getYearInDes());
                    rsShow.setId(rspList.getId());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setApproveDate(rspList.getApproveDate());
                    rsShow.setDocNo(rspList.getDocNo());
                    rsShow.setSubjectName(rspList.getSubjectName());
                    rsShow.setRelated(rspList.getRelated());
                    rsShow.setDepDescEN(rspList.getDepDescEN());
                    rsShow.setDepDescLAO(rspList.getDepDescLAO());
                    rsShow.setDocPath(rspList.getDocPath());
                    rsShow.setCreateDate(rspList.getCreateDate());
                    rsShow.setMarkerId(rspList.getMarkerId());
                    rsShow.setUserName(rspList.getUserName());
                    rsShow.setDocType(rspList.getDocType());
                    rsShow.setDocDescEn(rspList.getDocDescEn());
                    rsShow.setDocDescLao(rspList.getDocDescLao());
                    rsShow.setDocStatus(rspList.getDocStatus());
                    rsShow.setSharingType(rspList.getSharingType());
                    rsShow.setDocPathLa(rspList.getDocPathLa());
                    rsShow.setDocDate(rspList.getDocDate());
                    rsShow.setCreateBy(rspList.getCreateBy());
                    resDataItems.add(rsShow);
                }
            }
            groupHeader.setDetails(resDataItems);
        }
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setGroupHeader(headers);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getShareDocument(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        try {
            List<DocumentAudit> listData = new ArrayList<>();
            List<Login> getCheckUserList = loginService.CheckUser(documentReq);
            if(getCheckUserList.size() < 0 ){
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgUserError);
                result.setMessage(message);
                return result;
            }else {
                documentReq.setUserType(getCheckUserList.get(0).getUserStatus());
            }
            listData = documentImpl.getShareDocument(documentReq);
            List<Related> relatedList = documentImpl.getRsplistRelated();
            List<RelatedShow> relatedShowList = documentImpl.getRsplistRelatedShow();
            // Map related items to each audit
            for (DocumentAudit audit : listData) {
                List<Related> matchingRelatedItems = relatedList.stream()
                        .filter(r -> r.getDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedList(matchingRelatedItems);

                // Check if relatedShowList is empty or null
                List<RelatedShow> relatedShows = relatedShowList.stream()
                        .filter(r -> r.getRelatedShowDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedShowList02(relatedShows);
            }
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(null);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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

    public DocumentAuditRes getShareDocumentSubject(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        List<Login> getCheckUserList = loginService.CheckUser(documentReq);
        documentReq.setUserType(getCheckUserList.get(0).getUserStatus());
        listData = documentImpl.getShareDocumentSubject(documentReq);

        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getShareDocumentKanang(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        List<Login> getCheckUserList = loginService.CheckUser(documentReq);
        documentReq.setUserType(getCheckUserList.get(0).getUserStatus());
        listData = documentImpl.getShareDocumentKanang(documentReq);
        List<Related> relatedList = documentImpl.getRsplistRelated();
        List<RelatedShow> relatedShowList = documentImpl.getRsplistRelatedShow();
        try {
            for (DocumentAudit audit : listData) {
                List<Related> matchingRelatedItems = relatedList.stream()
                        .filter(r -> r.getDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedList(matchingRelatedItems);

                // Check if relatedShowList is empty or null
                List<RelatedShow> relatedShows = relatedShowList.stream()
                        .filter(r -> r.getRelatedShowDocNo().equals(audit.getDocNo()))
                        .collect(Collectors.toList());
                audit.setRelatedShowList02(relatedShows);
            }
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getShareDocumentDoctype(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        List<Login> getCheckUserList = loginService.CheckUser(documentReq);
        documentReq.setUserType(getCheckUserList.get(0).getUserStatus());
        listData = documentImpl.getShareDocumentDoctype(documentReq);
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getShareDocumentByCondition(docSerachReq docSerachReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getShareDocumentByCondition(docSerachReq);

        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public DocumentAuditRes getShareDocumentByConditionText(docSerachReq docSerachReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getShareDocumentByConditionText(docSerachReq);
        try {
            if (listData.size() > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
    public ReponeRes ReadDoc(DocumentReq documentReq){
        Message message = new Message();
        ReponeRes result = new ReponeRes();
        int checkData = documentImpl.ReadData(documentReq);
        try {
            if(checkData > 0 ){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgSave);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailSave);
            result.setMessage(message);
            return result;
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
    //=====================================audit document=================
    public ReponeRes rejectDocument(DocumentReq documentReq){
        Message message = new Message();
        ReponeRes result = new ReponeRes();
        int checkData = documentImpl.rejectDocument(documentReq);
        try {
            if(checkData > 0 ){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgReject);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailReject);
            result.setMessage(message);
            return result;
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
    public ReponeRes audit_doc(DocumentReq documentReq){
        Message message = new Message();
        ReponeRes result = new ReponeRes();
        int checkData = documentImpl.AuditDocument(documentReq);
        try {
            if(checkData > 0 ){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgAudit);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailAudit);
            result.setMessage(message);
            return result;
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
    //************************************
    public DocumentReportRes getReportDocument(DocumentReq documentReq) throws ParseException {
        Message message = new Message();
        DocumentReportRes result = new DocumentReportRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getReportDocument(documentReq);
        try {
            List<DocumentAudit>listGroupHead = new ArrayList<>();
            if (listData.size() > 0) {
                result.setResData(listData);
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);

                result.setResData(listData2);
                return result;
            }
        }catch (Exception e){
            if (e instanceof NullPointerException) {
                System.out.println("NullPointerException occurred");
            } else if (e instanceof IllegalArgumentException) {
                System.out.println("IllegalArgumentException occurred");
            } else if (e instanceof ArrayIndexOutOfBoundsException) {
                // Handle ArrayIndexOutOfBoundsException
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
}
