package com.ldb.iadoc.Model.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class login_log {
    private String id;
    private String userId;
    private String fullName;
    private String tel;
    private String email;
    private String secCode;
    private String secName;
    private String docNo;
    private String createDate;
    private String type;
}
