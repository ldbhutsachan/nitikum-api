package com.ldb.iadoc.Contrller;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.BranchRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.Section.SectionRes;
import com.ldb.iadoc.Service.ExcutiveBandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class ExcutiveBranchController {
    public static final Logger log = LogManager.getLogger(ExcutiveBranchController.class);
    @Autowired
    private ExcutiveBandService loginService;
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/saveBranchExcutive")
    public ReponeRes saveBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("====================================================>saveBranch controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.saveBranchExcutive(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getBranchListExcutive")
    public BranchRes getBranchList(@RequestBody BranchReq branchReq){
        log.info("====================================================>getBranchListExcutive controller<=========================");
        BranchRes result = new BranchRes();
        result = loginService.getBranchListExcutive(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/SaveSectionExcutive")
    public ReponeRes SaveSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("====================================================>SaveSectionExcutive controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.saveSectionExcutive(sectionReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/getSectionsExcutive")
    public SectionRes getSections(@RequestBody SectionReq sectionReq){
        log.info("====================================================>getSectionsExcutive controller<=========================");
        SectionRes result =new SectionRes();
        result = loginService.getSectionsExcutive(sectionReq);
        return result;
    }

}
