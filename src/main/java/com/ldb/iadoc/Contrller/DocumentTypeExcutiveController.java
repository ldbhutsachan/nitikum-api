package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.DocType.DocTypeRes;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Service.DocumentTypeExcutiveService;
import com.ldb.iadoc.Service.DocumentTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class DocumentTypeExcutiveController {
    public static final Logger log = LogManager.getLogger(LoginController.class);
    @Autowired
    private DocumentTypeExcutiveService documentTypeService;
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/getDocumentTypeExcutive")
    public DocTypeRes getDocumentType(@RequestBody DocTypeReq docTypeReq){
        log.info("====================================================>getDocumentTypeExcutive controller<=========================");
        DocTypeRes result =new DocTypeRes();
        result = documentTypeService.getDocumentTypeExcutive(docTypeReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/DocumentType/SaveDocTypeExcutive")
    public ReponeRes SaveDocType(@RequestBody DocTypeReq docTypeReq) throws ParseException {
        log.info("====================================================>SaveDocTypeExcutive controller<=========================");
        ReponeRes result =new ReponeRes();
        result = documentTypeService.SaveDocumentTypeExcutive(docTypeReq);
        return  result;
    }
}
