package com.ldb.iadoc.Model.Users;
import com.ldb.iadoc.Model.UserType.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ldb.iadoc.Mesage.Message;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private Message message;
    public List<UserType> resData;
}
