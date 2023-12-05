package com.ldb.iadoc.Dao.DocTypeDao;

import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.Document.Document;
import com.ldb.iadoc.Model.Document.DocumentReq;

import java.util.List;

public interface DocTypeDao {
    public int SaveDocType(DocTypeReq docTypeReq);
    public int UpdateDocType(DocTypeReq docTypeReq);
    public int DeleteDocType(DocTypeReq docTypeReq);
    public List<DocType> getDocumentType(DocTypeReq docTypeReq);


}
