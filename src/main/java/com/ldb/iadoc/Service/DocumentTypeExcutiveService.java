package com.ldb.iadoc.Service;

import com.ldb.iadoc.Dao.DocTypeDao.DocTypeExcutiveImpl;
import com.ldb.iadoc.Dao.DocTypeDao.DocTypeImpl;
import com.ldb.iadoc.Dao.DocumentDao.DocumentImpl;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Qter.Qter;
import com.ldb.iadoc.Model.Qter.QterRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Years.Years;
import com.ldb.iadoc.Model.Years.YearsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentTypeExcutiveService {
    @Autowired
    DocTypeExcutiveImpl docImpl;
    @Autowired
    DocumentImpl doc;
public DocTypeRes getDocumentTypeExcutive(DocTypeReq docTypeReq){
    Message message = new Message();
    DocTypeRes result= new DocTypeRes();
    List<DocType> listData =  docImpl.getDocumentTypeExcutive(docTypeReq);
    List<DocType> listData2 = new ArrayList<>();
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

    public ReponeRes SaveDocumentTypeExcutive(DocTypeReq docTypeReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= docImpl.SaveDocTypeExcutive(docTypeReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFail);
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
    public ReponeRes UpdatesDocumentTypeExcutive(DocTypeReq docTypeReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= docImpl.UpdateDocTypeExcutive(docTypeReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDoneUpdate);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailUpdate);
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
    public ReponeRes delDocumentTypeExcutive(DocTypeReq docTypeReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= docImpl.DeleteDocTypeExcutive(docTypeReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDoneDelete);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailDelete);
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

}
