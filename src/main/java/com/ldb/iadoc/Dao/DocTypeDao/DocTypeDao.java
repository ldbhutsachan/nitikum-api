package com.ldb.iadoc.Dao.DocTypeDao;

import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.Document.Document;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Qter.Qter;
import com.ldb.iadoc.Model.Years.Years;

import java.util.List;

public interface DocTypeDao {
    public int SaveDocType(DocTypeReq docTypeReq);
    public int UpdateDocType(DocTypeReq docTypeReq);
    public int DeleteDocType(DocTypeReq docTypeReq);
    public List<DocType> getDocumentType(DocTypeReq docTypeReq);
    public List<Years> getComboYear();
    public List<Qter> getQter();
    public List<Years> getChooseYearAll();
    public List<Qter> getQterAll();
    public List<DocType> getDocumentTypeAll();


}
