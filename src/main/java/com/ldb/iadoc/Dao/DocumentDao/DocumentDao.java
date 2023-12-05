package com.ldb.iadoc.Dao.DocumentDao;

import com.ldb.iadoc.Model.Document.Document;
import com.ldb.iadoc.Model.Document.DocumentAudit;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Share.ShareReq;

import java.text.ParseException;
import java.util.List;

public interface DocumentDao {
    public int rejectDocument(DocumentReq documentReq);
    public int saveSharingDo(DocumentReq documentReq);
    public int ReadData(DocumentReq documentReq);
    public int SaveDocument(DocumentReq documentReq) throws ParseException;
    public int UpdateDocument(DocumentReq documentReq);
    public int DelDocument(DocumentReq documentReq);
    List<Document> getDocument(DocumentReq documentReq);

    public int AuditDocument(DocumentReq documentReq);
    List<DocumentAudit> getAuditDocument(DocumentReq documentReq);
    public List<DocumentAudit> getWaitListCheckByUser(DocumentReq documentReq);
    public List<DocumentAudit> getShareDocument(DocumentReq documentReq);
    public List<DocumentAudit> getReportDocument(DocumentReq documentReq) throws ParseException;

}
