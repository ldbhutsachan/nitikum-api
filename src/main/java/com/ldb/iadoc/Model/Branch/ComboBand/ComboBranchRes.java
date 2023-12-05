package com.ldb.iadoc.Model.Branch.ComboBand;

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
public class ComboBranchRes {
    private Message message;
    public List<ComboBranch> resData;
}
