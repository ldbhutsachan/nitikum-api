package com.ldb.iadoc.Model.Login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private Long  iD;
    private String UserId;
    private String userName;
    private String passWord;
    private String oldPassWord;
    private String newPassWord;
    private String toKen;
    private String gender;
    private String dob;
    private String userStatus;
    private String typeDesc;
    private String typeDesLa;

    private String secCode;
    private String secDescEn;
    private String secDescLa;

    private String fullNameEn;
    private String fullNameLa;

    private String email;
    private String tel;
    private String idtype;
}
