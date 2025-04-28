package com.ldb.iadoc.Dao.Login;

import com.ldb.iadoc.Model.Branch.Branch;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranch;
import com.ldb.iadoc.Model.Department.Dept;
import com.ldb.iadoc.Model.Department.DeptReq;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Login.*;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwd;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSection;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ExcusiveSection.ComboSectionExReq;
import com.ldb.iadoc.Model.Section.Section;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.UserType.UserType;
import com.ldb.iadoc.Model.UserType.UserTypeReq;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUser;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserReq;

import java.text.ParseException;
import java.util.List;

public interface LoginDao {
    public List<login_log> dologStatisticDetailsDoc(VWStatisticReq vwStatisticReq);
    public List<login_log> dologStatisticDetailsLogin(VWStatisticReq vwStatisticReq);

    public List<VWStatistic_login> dologStatistic(VWStatisticReq vwStatisticReq) ;
    public int saveDoLog(login_log logReq);
    public List<ComboBranch> getComboxBranchStatus();
    public List<VWStatistic_login> getStatisticLogin (VWStatisticReq vwStatisticReq );
    public int saveLoginLog( List<Login> loginLog);
    public List<Login> CheckUser(DocumentReq documentReq);
    public List<Branch> getBranchExcutive(BranchReq branchReq);
    public int saveBranchExcutive(BranchReq branchReq);
    public List<ComboSection> getComboxDeptExcutive(ComboSectionExReq sectionReq);
    public List<ComboSection> getComboxSectionsExcutive(ComboSectionExReq sectionReq);
    public List<ComboBranch> getComboxBranchExcutive();
    public List<ComboUser> getComboxUser(ComboUserReq loginReq);
    public List<ComboSection> getComboxSections(ComboSectionReq sectionReq);
    List<LoginChangPwd> checkOldPwd(LoginReq loginReq);
    List<Login> login(LoginReq loginReq);
    public List<Login> getShowUserInfo(LoginReq loginReq);
    List<UserType> getUserType();
    List<Section> getSection(SectionReq sectionReq);
    public int chagePassword(LoginReq signupReq) throws ParseException;
    public int signup(SignupReq signupReq) throws ParseException;
    public int UpdatesSignUp(SignupReq signupReq) throws ParseException;
    public int DelSignUp(SignupReq signupReq) throws ParseException;
    //==============================dept=====================
    public int saveDept(DeptReq deptReq);
    public int updateDept(DeptReq deptReq);
    public int deleteDept(DeptReq deptReq);
    List<Dept> getDeptList(DeptReq deptReq);
    //==============================branch=====================
    public int saveBranch(BranchReq branchReq);
    public int updateBranch(BranchReq branchReq);
    public int delBranch(BranchReq branchReq);
    List<Branch> getBranch(BranchReq branchReq);
    List<ComboBranch> getComboxBranch();
    //============================section=====================
    public int saveSection(SectionReq sectionReq);
    public int updateSection(SectionReq sectionReq);
    public int delSection(SectionReq sectionReq);


}
