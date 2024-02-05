package com.ldb.iadoc.Model.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAudit {
    private String typeStatus;
    private String connectKanang;
    private String connectKanangAll;
    private String connects;
    private String taiMard;
    private String yearIn;
    private String taiMardDes;
    private String yearInDes;
    private String id;
    private String docNo;
    private String docType;
    private String subjectName;
    private String related;
    private String related_No;
    private String related_Name;
    private String docStatus;
    private String docPath;
    private String createDate;
    private String markerId;
    private String approveId;
    private String deleteId;
    private String sharingType;
    private String  docPathLa;
    private String  docDate;
    private String details;
    private String approveDate;
    //-----------------------dept
    private String depDescEN;
    private String depDescLAO;
    //-----------------------User
    private String userName;
    private String userNameApprove;
    private String userNameUpdate;
    //----------------------doc type desc
    private String docDescEn;
    private String docDescLao;
    private String createBy;
    private String typeAMT;
    private String status;



}
