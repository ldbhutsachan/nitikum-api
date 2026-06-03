package com.ldb.iadoc.Model.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupReq {
    private String id;
    private String userId;
    private String passWord;
    private String userName;
    private String gender;
    private String dob;
    private String userType;
    private String userStatus;
    private String tel;
    private String email;
    private String loginStatus;
    private String fullNameEn;
    private String fullNameLa;
    private String toKen;
    private String brCode;
    private String secCode;

}
