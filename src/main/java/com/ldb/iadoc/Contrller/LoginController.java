package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.BranchRes;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranchRes;
import com.ldb.iadoc.Model.Department.DeptReq;
import com.ldb.iadoc.Model.Department.DeptRes;
import com.ldb.iadoc.Model.Login.*;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwdRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionRes;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.Section.SectionRes;
import com.ldb.iadoc.Model.UserType.UserTypeRes;
//import com.ldb.iadoc.Security.JwtUtils;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserReq;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserRes;
import com.ldb.iadoc.Service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${base_url}")
public class LoginController {
    public static final Logger log = LogManager.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;
    @CrossOrigin(origins = "*")
    @GetMapping("/test")
    public String test() throws Exception{
        return "hello";
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Auth/login")
    public LoginRes login(@RequestBody LoginReq loginReq){
        log.info("====================================================>Login controller<=========================");
    LoginRes result =new LoginRes();
        result = loginService.LoginByUser(loginReq);
    return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/log/doLogByUser")
    public LoginRes doLogByUser(@RequestBody login_log loginReq){
        log.info("====================================================>Login controller<=========================");
    LoginRes result =new LoginRes();
        result = loginService.doLog(loginReq);
    return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/User/getShowUserInfo")
    public LoginRes getShowUserInfo(@RequestBody LoginReq loginReq){
        log.info("====================================================>getShowUserInfo controller<=========================");
        LoginRes result =new LoginRes();
        result = loginService.getShowUserInfo(loginReq);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/log/getStatisticLogin")
    public VWStatisticRes getStatisticLogin(@RequestBody VWStatisticReq loginReq){
        log.info("====================================================>VWStatisticRes controller<=========================");
        VWStatisticRes result =new VWStatisticRes();
        result = loginService.getStatistic(loginReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/log/dologStatistic")
    public VWStatisticRes dologStatistic(@RequestBody VWStatisticReq loginReq){
        log.info("====================================================>VWStatisticRes controller<=========================");
        VWStatisticRes result =new VWStatisticRes();
        result = loginService.dologStatistic(loginReq);
        return result;
    }


  @CrossOrigin(origins = "*")
    @PostMapping("/log/dologStatisticDetailsDoc")
    public VWStatisticLogRes dologStatisticDetailsDoc(@RequestBody VWStatisticReq loginReq){
        log.info("====================================================>VWStatisticRes controller<=========================");
      VWStatisticLogRes result =new VWStatisticLogRes();
        result = loginService.dologStatisticDetailsDoc(loginReq);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/log/dologStatisticDetailsLogin")
    public VWStatisticLogRes dologStatisticDetailsLogin(@RequestBody VWStatisticReq loginReq){
        log.info("====================================================>ທົດລອງ controller<=========================");
      VWStatisticLogRes result =new VWStatisticLogRes();
        result = loginService.dologStatisticDetailsLog(loginReq);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/UserType/getUserType")
    public UserTypeRes getUserType(){
        log.info("====================================================>getUserType controller<=========================");
        UserTypeRes result = loginService.getUserType();
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/getSections")
    public SectionRes getSections(@RequestBody SectionReq sectionReq){
        log.info("====================================================>getSections controller<=========================");
        SectionRes result =new SectionRes();
        result = loginService.getSections(sectionReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Auth/Signup")
    public ReponeRes Signup(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("====================================================>Signup controller<=========================");
        log.info("sig user:"+signupReq.getUserName());
        ReponeRes result =new ReponeRes();
        result = loginService.Signup(signupReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Auth/UpdatesSignUp")
    public ReponeRes UpdatesSignUp(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("====================================================>Signup controller<=========================");
        log.info("sig user:"+signupReq.getUserName());
        ReponeRes result =new ReponeRes();
        result = loginService.UpdatesSignUp(signupReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Auth/DelSignUp")
    public ReponeRes DelSignUp(@RequestBody SignupReq signupReq) throws ParseException {
        log.info("====================================================>Signup controller<=========================");
        log.info("sig user:"+signupReq.getUserName());
        ReponeRes result =new ReponeRes();
        result = loginService.DelSignUp(signupReq);
        return  result;
    }
    //chagePassword
    @CrossOrigin(origins = "*")
    @PostMapping("/Auth/chagePassword")
    public LoginChangPwdRes chagePassword(@RequestBody LoginReq loginReq){
        log.info("====================================================>getShowUserInfo controller<=========================");
        System.out.println("newPassword:"+loginReq.getNewPwd());
        LoginChangPwdRes result =new LoginChangPwdRes();
        result = loginService.getOldInfoOldUser(loginReq);
        return result;
    }
    //========================================Dept
    @CrossOrigin(origins = "*")
    @PostMapping("/Dept/saveDept")
    public ReponeRes saveDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("====================================================>saveDept controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.saveDept(deptReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Dept/updateDept")
    public ReponeRes updateDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("====================================================>updateDept controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.updateDept(deptReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Dept/delDept")
    public ReponeRes delDept(@RequestBody DeptReq deptReq) throws ParseException {
        log.info("====================================================>delDept controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.delDept(deptReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Dept/getDeptList")
    public DeptRes getDeptList(@RequestBody DeptReq deptReq){
        log.info("====================================================>getDeptList controller<=========================");
        DeptRes result = new DeptRes();
        result = loginService.getDeptList(deptReq);
        return  result;
    }
    //========================================Branch
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/saveBranch")
    public ReponeRes saveBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("====================================================>saveBranch controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.saveBranch(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/updateBranch")
    public ReponeRes updateBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("====================================================>updateBranch controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.updateBranch(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/delBranch")
    public ReponeRes delBranch(@RequestBody BranchReq branchReq) throws ParseException {
        log.info("====================================================>delBranch controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.delBranch(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getBranchList")
    public BranchRes getBranchList(@RequestBody BranchReq branchReq){
        log.info("====================================================>getBranchList controller<=========================");
        BranchRes result = new BranchRes();
        result = loginService.getBranchList(branchReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getBranchListStatus")
    public BranchRes getBranchListStatus(@RequestBody BranchReq branchReq){
        log.info("====================================================>getBranchList controller<=========================");
        BranchRes result = new BranchRes();
        result = loginService.getBranchListStatus(branchReq);
        return  result;
    }
    //=========================section =========
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/SaveSection")
    public ReponeRes SaveSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("====================================================>SaveSection controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.saveSection(sectionReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/updateSection")
    public ReponeRes updateSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("====================================================>updateSection controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.upDateSection(sectionReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/delSection")
    public ReponeRes delSection(@RequestBody SectionReq sectionReq) throws ParseException {
        log.info("====================================================>delSection controller<=========================");
        ReponeRes result =new ReponeRes();
        result = loginService.delSection(sectionReq);
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getComboxBranch")
    public ComboBranchRes getComboxBranch(){
        log.info("====================================================>getComboxBranch controller<=========================");
        ComboBranchRes result = new ComboBranchRes();
        result = loginService.getComboxBranch();
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Branch/getComboxBranchStatus")
    public ComboBranchRes getComboxBranchStatus(){
        log.info("====================================================>getComboxBranch controller<=========================");
        ComboBranchRes result = new ComboBranchRes();
        result = loginService.getComboxBranchStatus();
        return  result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/Section/getComboxSections")
    public ComboSectionRes getComboxSections(@RequestBody ComboSectionReq sectionReq){
        log.info("====================================================>getComboxSections controller<=========================");
        log.info("bb:"+ Arrays.toString(sectionReq.getBranchCode()));
        ComboSectionRes result =new ComboSectionRes();
        result = loginService.getComboxSections(sectionReq);
        return result;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/User/getComboxUser")
    public ComboUserRes getComboxUser(@RequestBody ComboUserReq loginReq){
        log.info("====================================================>getComboxUser controller<=========================");
        ComboUserRes result =new ComboUserRes();
        result = loginService.getComboxUser(loginReq);
        return result;
    }
}
