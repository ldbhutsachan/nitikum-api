package com.ldb.iadoc.Model.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String userId;
    private String userPwd;
    private String userName;
    private String gender;
    private String create_dt;
    private String expiry_dt;
    private String userStatus;
    private String tel;
    private String email;
    private String loginStatus;
}
