package com.ldb.iadoc.Model.Users.ComboUser;

import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.UserType.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComboUserRes {
    private Message message;
    public List<ComboUser> resData;
}
