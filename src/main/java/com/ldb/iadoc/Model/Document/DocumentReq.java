package com.ldb.iadoc.Model.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentReq {

    private String id;
    private String conName;
    private String conName2;
    private String taiMard;
    private String yearIn;
    private String docNo;
    private String related_No;
    private String related_Name;
    private String subjectName;
    private String docDate;
    private String docType;
    private String related;
    private String deptCode;
    private String shareUserById;
    private String secCode;
    private String docStatus;
    private String docPath;
    private String docPathLa;
    private String createDate;
    private String markerId;
    private String updateId;
    private String deleteId;
    private String approveId;
    private String sharingType;
    private String details;

    private String rejectBy;
    private String readBy;
    private String rejectDate;

    private String userType;

    private String startDate;
    private String endDate;


    private String docConnect;

}
