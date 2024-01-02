package com.ldb.iadoc.Service;


import com.ldb.iadoc.Dao.Login.LoginImpl;
import com.ldb.iadoc.Mesage.Constant;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.Branch.Branch;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.BranchRes;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranch;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranchRes;
import com.ldb.iadoc.Model.Department.Dept;
import com.ldb.iadoc.Model.Department.DeptReq;
import com.ldb.iadoc.Model.Department.DeptRes;
import com.ldb.iadoc.Model.Login.Login;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwd;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwdRes;
import com.ldb.iadoc.Model.Login.LoginReq;
import com.ldb.iadoc.Model.Login.LoginRes;
import com.ldb.iadoc.Model.Login.SignupReq;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSection;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionRes;
import com.ldb.iadoc.Model.Section.Section;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.Section.SectionRes;
import com.ldb.iadoc.Model.UserType.UserType;
import com.ldb.iadoc.Model.UserType.UserTypeRes;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUser;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserReq;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserRes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcutiveBandService {
    public static final Logger log = LogManager.getLogger(ExcutiveBandService.class);
    @Autowired
    private LoginImpl loginImpls;
    //===================================Branch===============
    public ReponeRes saveBranchExcutive(BranchReq branchReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.saveBranchExcutive(branchReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgSave);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailSave);
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
    public ReponeRes updateBranchExcutive(BranchReq branchReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.updateBranch(branchReq);
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
    public ReponeRes delBranchExcutive(BranchReq branchReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.delBranch(branchReq);
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
    public BranchRes getBranchListExcutive(BranchReq deptReq){
        Message message = new Message();
        BranchRes result =new BranchRes();
        List<Branch> listData = loginImpls.getBranchExcutive(deptReq);
        try{
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData);
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

    public ReponeRes saveSectionExcutive(SectionReq sectionReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.saveSectionExcutive(sectionReq);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgSave);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailSave);
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

    public SectionRes getSectionsExcutive(SectionReq sectionReq){
        Message message = new Message();
        SectionRes result =new SectionRes();
        List<Section> listData = loginImpls.getSectionsExcutive(sectionReq);
        try{
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setMessage(message);
                result.setResData(listData);
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
}
