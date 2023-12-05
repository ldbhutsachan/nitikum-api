package com.ldb.iadoc.Model.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String id;
    private String docNo;
    private String docType;
    private String subjectName;
    private String related;
    private String docStatus;
    private String docPath;
    private String createDate;
    private String markerId;
    private String approveId;
    private String deleteId;
    private String sharingType;

    private String  docPathLa;
    private String  docDate;
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

}
