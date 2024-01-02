package com.ldb.iadoc.Model.Qter;

import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.Years.Years;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QterRes {
    private Message message;
    private List<Qter> resData;
}
