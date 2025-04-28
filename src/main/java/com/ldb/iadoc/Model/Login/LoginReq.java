package com.ldb.iadoc.Model.Login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    private String userName;
    private String passWord;
    private String oldUserId;
    private String oldUserName;
    private String oldPassword;
    private String newPwd;
    private String userType;
}
