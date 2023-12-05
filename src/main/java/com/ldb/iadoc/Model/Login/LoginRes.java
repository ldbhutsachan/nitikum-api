package com.ldb.iadoc.Model.Login;


import com.ldb.iadoc.Mesage.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {
public Message message;
public List<Login> resData;
}
