package com.ldb.iadoc.Dao.DocTypeDao;

import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.Qter.Qter;
import com.ldb.iadoc.Model.Years.Years;

import java.util.List;

public interface DocTypeExcutvieDao {
    public int SaveDocTypeExcutive(DocTypeReq docTypeReq);
    public int UpdateDocTypeExcutive(DocTypeReq docTypeReq);
    public int DeleteDocTypeExcutive(DocTypeReq docTypeReq);
    public List<DocType> getDocumentTypeExcutive(DocTypeReq docTypeReq);
}
