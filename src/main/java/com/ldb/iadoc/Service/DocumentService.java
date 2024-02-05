package com.ldb.iadoc.Service;

import com.ldb.iadoc.Dao.DocumentDao.DocumentImpl;
import com.ldb.iadoc.Dao.Login.LoginImpl;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.Document.*;
import com.ldb.iadoc.Model.Document.Report.DocumentReportRes;
import com.ldb.iadoc.Model.GroupHeaderReq;
import com.ldb.iadoc.Model.GroupHeaderRes;
import com.ldb.iadoc.Model.Login.Login;
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
    public DocumentAuditRes getAuditListCheck(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getAuditDocument(documentReq);
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
    public DocumentAuditRes getWaitListCheckByUser(DocumentReq documentReq){
        Message message = new Message();
        DocumentAuditRes result = new DocumentAuditRes();
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        listData = documentImpl.getWaitListCheckByUser(documentReq);
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
        List<DocumentAudit> listData = new ArrayList<>();
        List<DocumentAudit> listData2 = new ArrayList<>();
        List<Login> getCheckUserList = loginService.CheckUser(documentReq);
        log.info("show:"+getCheckUserList.size());
        if(getCheckUserList.size() < 0 ){
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgUserError);
            result.setMessage(message);
            return result;
        }else {
            documentReq.setUserType(getCheckUserList.get(0).getUserStatus());
        }
        listData = documentImpl.getShareDocument(documentReq);
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
