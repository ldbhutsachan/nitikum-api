package com.ldb.iadoc.Model.Document;

import com.ldb.iadoc.Model.Relation.Related;
import com.ldb.iadoc.Model.Relation.RelatedShow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private String ses_status;

    private List<Related> relatedList;
    private List<RelatedShow> relatedShowList02;


    @Override
    public String toString() {
        return "YourClassName response : {" +
                "typeStatus='" + typeStatus + '\'' +
                ", connectKanang='" + connectKanang + '\'' +
                ", connectKanangAll='" + connectKanangAll + '\'' +
                ", connects='" + connects + '\'' +
                ", taiMard='" + taiMard + '\'' +
                ", yearIn='" + yearIn + '\'' +
                ", taiMardDes='" + taiMardDes + '\'' +
                ", yearInDes='" + yearInDes + '\'' +
                ", id='" + id + '\'' +
                ", docNo='" + docNo + '\'' +
                ", docType='" + docType + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", related='" + related + '\'' +
                ", related_No='" + related_No + '\'' +
                ", related_Name='" + related_Name + '\'' +
                ", docStatus='" + docStatus + '\'' +
                ", docPath='" + docPath + '\'' +
                ", createDate='" + createDate + '\'' +
                ", markerId='" + markerId + '\'' +
                ", approveId='" + approveId + '\'' +
                ", deleteId='" + deleteId + '\'' +
                ", sharingType='" + sharingType + '\'' +
                ", docPathLa='" + docPathLa + '\'' +
                ", docDate='" + docDate + '\'' +
                ", details='" + details + '\'' +
                ", approveDate='" + approveDate + '\'' +
                ", depDescEN='" + depDescEN + '\'' +
                ", depDescLAO='" + depDescLAO + '\'' +
                ", userName='" + userName + '\'' +
                ", userNameApprove='" + userNameApprove + '\'' +
                ", userNameUpdate='" + userNameUpdate + '\'' +
                ", docDescEn='" + docDescEn + '\'' +
                ", docDescLao='" + docDescLao + '\'' +
                ", createBy='" + createBy + '\'' +
                ", typeAMT='" + typeAMT + '\'' +
                ", status='" + status + '\'' +
                ", ses_status='" + ses_status + '\'' +
                ", relatedList=" + relatedList +
                ", relatedShowList02=" + relatedShowList02 +
                '}';
    }


}
