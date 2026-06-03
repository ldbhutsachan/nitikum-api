package com.ldb.iadoc.Service;


import com.ldb.iadoc.Contrller.LoginController;
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
import com.ldb.iadoc.Model.Login.*;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwd;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwdRes;
import com.ldb.iadoc.Model.ReponeRes;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSection;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionRes;
import com.ldb.iadoc.Model.Section.ExcusiveSection.ComboSectionExReq;
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
import java.util.Collections;
import java.util.List;

@Service
public class LoginService {
    public static final Logger log = LogManager.getLogger(LoginService.class);
    @Autowired
    private LoginImpl loginImpls;

    public VWStatisticRes getStatistic(VWStatisticReq vwStatisticReq){
        VWStatisticRes result = new VWStatisticRes();
        Message message = new Message();
        try{
            List<VWStatistic_login> listData = loginImpls.getStatisticLogin(vwStatisticReq);
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgUserError);
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

    public VWStatisticRes dologStatistic(VWStatisticReq vwStatisticReq){
        VWStatisticRes result = new VWStatisticRes();
        Message message = new Message();
        try{
            List<VWStatistic_login> listData = loginImpls.dologStatistic(vwStatisticReq);
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgUserError);
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

public VWStatisticLogRes dologStatisticDetailsDoc(VWStatisticReq vwStatisticReq){
    VWStatisticLogRes result = new VWStatisticLogRes();
        Message message = new Message();
        try{
            List<login_log> listData = loginImpls.dologStatisticDetailsDoc(vwStatisticReq);
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeError);
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
    public VWStatisticLogRes dologStatisticDetailsLog(VWStatisticReq vwStatisticReq){
    VWStatisticLogRes result = new VWStatisticLogRes();
        Message message = new Message();
        try{
            List<login_log> listData = loginImpls.dologStatisticDetailsLogin(vwStatisticReq);
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeError);
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

    public LoginRes LoginByUser(LoginReq loginReq){
        Message message = new Message();
        LoginRes result =new LoginRes();
        try{
            List<Login> listData02 = new ArrayList<>();

            List<Login> listData = loginImpls.login(loginReq);
             if(listData.size() > 0 ){
                String status= listData.get(0).getUserStatus();
                if(status.equals("Y")){
                    message.setResCode(Constant.codeErrorLogin);
                    message.setResMgs(Constant.msgUserErrorLogin);
                    result.setMessage(message);
                    result.setResData(listData02);
                    return result;
                }else {
                    //*****store log login in system
                    loginImpls.saveLoginLog(listData);
                    message.setResCode(Constant.codeDone);
                    message.setResMgs(Constant.msgDone);
                    result.setMessage(message);
                    result.setResData(listData);
                    return result;
                }
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgUserError);
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

    public LoginRes doLog(login_log loginReq){
        Message message = new Message();
        LoginRes result =new LoginRes();
        try{
            loginImpls.saveDoLog(loginReq);
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(null);
                return result;

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
    public LoginRes getShowUserInfo(LoginReq loginReq){
        Message message = new Message();
        LoginRes result =new LoginRes();
        try{
            List<Login> listData = loginImpls.getShowUserInfo(loginReq);
            if(listData.size() > 0){
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setMessage(message);
                result.setResData(listData);
                return result;
            }else {
                message.setResCode(Constant.codeError);
                message.setResMgs(Constant.msgUserError);
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
    public ComboUserRes getComboxUser(ComboUserReq loginReq){
        Message message = new Message();
        ComboUserRes result =new ComboUserRes();
        try{
            List<ComboUser> listData = loginImpls.getComboxUser(loginReq);
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
    public UserTypeRes getUserType(){
        Message message = new Message();
        UserTypeRes result =new UserTypeRes();
        List<UserType> listData = loginImpls.getUserType();
        try{
        if(listData.size() > 0){
            message.setResCode(Constant.codeDone);
            message.setResMgs(Constant.msgDone);
            result.setMessage(message);
            result.setResData(listData);
            return result;
        }else {
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgUserError);
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
    public SectionRes getSections(SectionReq sectionReq){
        Message message = new Message();
        SectionRes result =new SectionRes();
        List<Section> listData = loginImpls.getSection(sectionReq);
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
    public SectionRes getSectionData(SectionReq sectionReq){
        Message message = new Message();
        SectionRes result =new SectionRes();
        List<Section> listData = loginImpls.getSectionData(sectionReq);
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
    //===============================combo section======================
    public ComboSectionRes getComboxSections(ComboSectionReq sectionReq){
        Message message = new Message();
        ComboSectionRes result =new ComboSectionRes();
        List<ComboSection> listData = loginImpls.getComboxSections(sectionReq);
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
    public ComboSectionRes getComboxSectionsExcutive(ComboSectionExReq sectionReq){
        Message message = new Message();
        ComboSectionRes result =new ComboSectionRes();
        List<ComboSection> listData = loginImpls.getComboxSectionsExcutive(sectionReq);
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
    public ComboSectionRes getComboxDeptExcutive(ComboSectionExReq sectionReq){
        Message message = new Message();
        ComboSectionRes result =new ComboSectionRes();
        List<ComboSection> listData = loginImpls.getComboxDeptExcutive(sectionReq);
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
    public ReponeRes Signup(SignupReq signupReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.signup(signupReq);
        System.out.println("check:"+check);
        try {
            if (check > 0) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDoneRegister);
                result.setMessage(message);
                return result;
            }
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailRegister);
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
    public ReponeRes UpdatesSignUp(SignupReq signupReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        String userType = signupReq.getUserType();
        if (userType == null || userType.trim().isEmpty()) {
            message.setResCode(Constant.codeError);
            message.setResMgs(Constant.msgFailUpdate);
            result.setMessage(message);
        }
        else {

            check = loginImpls.UpdatesSignUp(signupReq);
            System.out.println("check:" + check);
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
            } catch (Exception e) {
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
        }
        return result;
    }
    public ReponeRes DelSignUp(SignupReq signupReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.DelSignUp(signupReq);
        System.out.println("check:"+check);
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
    //===============================CHANGE PASSSOWRD
    public LoginChangPwdRes getOldInfoOldUser(LoginReq loginReq){
        Message message = new Message();
        LoginChangPwdRes result =new LoginChangPwdRes();
        try{
            List<LoginChangPwd> listData = loginImpls.checkOldPwd(loginReq);
            if(listData.size() > 0){
                String oldUserIdCheck= listData.get(0).getUserId();
                String oldPwdCheck= listData.get(0).getOldPassWord();
                String oldUserId= loginReq.getOldUserId();
                String oldPwd= loginReq.getOldPassword();
                 if(oldUserIdCheck.equals(oldUserId) && oldPwdCheck.equals(oldPwdCheck)){
                    loginImpls.chagePassword(loginReq);
                    message.setResCode(Constant.codeDone);
                    message.setResMgs(Constant.msgDoneChangePwd);
                    result.setMessage(message);
                    return result;
                }
                else if(!oldUserIdCheck.equals(oldUserId)){
                    message.setResCode(Constant.codeErrorLogin);
                    message.setResMgs(Constant.msgUserIdFail);
                }else if(!oldPwdCheck.equals(oldPwd)){
                    message.setResCode(Constant.codeErrorLogin);
                    message.setResMgs(Constant.msgPasswordFail);
                }
                else {
                    message.setResCode(Constant.codeError);
                    message.setResMgs(Constant.msgError);
                    result.setMessage(message);
                    return result;
                }
            }else {
                message.setResCode(Constant.codeErrorLogin);
                message.setResMgs(Constant.msgPasswordFail);
                result.setMessage(message);
                return result;
            }
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
    //===================================Branch===============
    public ReponeRes saveBranch(BranchReq branchReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.saveBranch(branchReq);
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
    public ReponeRes updateBranch(BranchReq branchReq) throws ParseException {
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
    public ReponeRes delBranch(BranchReq branchReq) throws ParseException {
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
    //=======================dept
    public ReponeRes saveDept(DeptReq deptReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.saveDept(deptReq);
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
    public ReponeRes updateDept(DeptReq deptReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.updateDept(deptReq);
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
    public ReponeRes delDept(DeptReq deptReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.deleteDept(deptReq);
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
    public DeptRes getDeptList(DeptReq deptReq){
        Message message = new Message();
        DeptRes result =new DeptRes();
        List<Dept> listData = loginImpls.getDeptList(deptReq);
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
    public BranchRes getBranchList(BranchReq deptReq) {
        BranchRes result = new BranchRes();
        Message message = new Message();

        try {
            // Fetch branch data
            List<Branch> listData = loginImpls.getBranch(deptReq);
            System.out.println("Number of rows returned: " + listData.size());
            // Check if the list contains data
            if (listData != null && !listData.isEmpty()) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setResData(listData);
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setResData(Collections.emptyList()); // Ensure empty list is returned
            }

            // Set the response message
            result.setMessage(message);

        } catch (NullPointerException e) {
            log.error("NullPointerException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Null reference encountered during processing.");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Invalid argument provided.");
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "An unexpected error occurred. Please contact support.");
        }

        return result;
    }public BranchRes getBranchListAll( ) {
        BranchRes result = new BranchRes();
        Message message = new Message();

        try {
            // Fetch branch data
            List<Branch> listData = loginImpls.getBranchAll();
            System.out.println("Number of rows returned: " + listData.size());
            // Check if the list contains data
            if (listData != null && !listData.isEmpty()) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setResData(listData);
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setResData(Collections.emptyList()); // Ensure empty list is returned
            }

            // Set the response message
            result.setMessage(message);

        } catch (NullPointerException e) {
            log.error("NullPointerException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Null reference encountered during processing.");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Invalid argument provided.");
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "An unexpected error occurred. Please contact support.");
        }

        return result;
    }
    public BranchRes getBranchListStatus(BranchReq deptReq) {
        BranchRes result = new BranchRes();
        Message message = new Message();

        try {
            // Fetch branch data
            List<Branch> listData = loginImpls.getBranch(deptReq);
            System.out.println("Number of rows returned: " + listData.size());
            // Check if the list contains data
            if (listData != null && !listData.isEmpty()) {
                message.setResCode(Constant.codeDone);
                message.setResMgs(Constant.msgDone);
                result.setResData(listData);
            } else {
                message.setResCode(Constant.codeDataNotFound);
                message.setResMgs(Constant.msgDataNotFound);
                result.setResData(Collections.emptyList()); // Ensure empty list is returned
            }

            // Set the response message
            result.setMessage(message);

        } catch (NullPointerException e) {
            log.error("NullPointerException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Null reference encountered during processing.");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "Invalid argument provided.");
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            handleError(result, Constant.codeError, "An unexpected error occurred. Please contact support.");
        }

        return result;
    }

    // Utility method to handle error cases
    private void handleError(BranchRes result, String resCode, String resMsg) {
        Message errorMessage = new Message();
        errorMessage.setResCode(resCode);
        errorMessage.setResMgs(resMsg);
        result.setMessage(errorMessage);
        result.setResData(Collections.emptyList()); // Return an empty list to avoid null issues
    }

    //==========================combobox branch=============================================
    public ComboBranchRes getComboxBranch(){
        Message message = new Message();
        ComboBranchRes result =new ComboBranchRes();
        List<ComboBranch> listData = loginImpls.getComboxBranch();
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
    }  public ComboBranchRes getComboxBranchStatus(){
        Message message = new Message();
        ComboBranchRes result =new ComboBranchRes();
        List<ComboBranch> listData = loginImpls.getComboxBranchStatus();
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
    public ComboBranchRes getComboxBranchExcutive(){
        Message message = new Message();
        ComboBranchRes result =new ComboBranchRes();
        List<ComboBranch> listData = loginImpls.getComboxBranchExcutive();
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
    //==========================combobox branch=============================================
    //=================section
    public ReponeRes saveSection(SectionReq sectionReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.saveSection(sectionReq);
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
    public ReponeRes upDateSection(SectionReq sectionReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.updateSection(sectionReq);
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
    public ReponeRes delSection(SectionReq sectionReq) throws ParseException {
        Message message = new Message();
        ReponeRes  result =new ReponeRes();
        int check = 0;
        check= loginImpls.delSection(sectionReq);
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
