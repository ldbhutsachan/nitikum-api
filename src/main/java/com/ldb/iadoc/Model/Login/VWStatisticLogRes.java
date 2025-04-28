package com.ldb.iadoc.Model.Login;

import com.ldb.iadoc.Mesage.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VWStatisticLogRes {
    public Message message;
    public List<login_log> resData;
}
