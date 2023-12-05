package com.ldb.iadoc.Model.Login.LoginInfo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginChangPwd {

    private String UserId;
    private String oldPassWord;
    private String newPassWord;

}
