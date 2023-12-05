package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.Login.SignupReq;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Service.DocumentTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class DocumentTypeController {
    public static final Logger log = LogManager.getLogger(LoginController.class);
    @Autowired
    private DocumentTypeService documentTypeService;
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/getDocumentType")
    public DocTypeRes getDocumentType(@RequestBody DocTypeReq docTypeReq){
        log.info("====================================================>getDocumentType controller<=========================");
        DocTypeRes result =new DocTypeRes();
        result = documentTypeService.getDocumentType(docTypeReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/SaveDocType")
    public ReponeRes SaveDocType(@RequestBody DocTypeReq docTypeReq) throws ParseException {
        log.info("====================================================>SaveDocType controller<=========================");
        ReponeRes result =new ReponeRes();
        result = documentTypeService.SaveDocumentType(docTypeReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/UpdateDocType")
    public ReponeRes UpdateDocType(@RequestBody DocTypeReq docTypeReq) throws ParseException {
        log.info("====================================================>UpdateDocType controller<=========================");
        ReponeRes result =new ReponeRes();
        result = documentTypeService.UpdatesDocumentType(docTypeReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/DeleteDocType")
    public ReponeRes DeleteDocType(@RequestBody DocTypeReq docTypeReq) throws ParseException {
        log.info("====================================================>UpdateDocType controller<=========================");
        ReponeRes result =new ReponeRes();
        result = documentTypeService.delDocumentType(docTypeReq);
        return  result;
    }
}

