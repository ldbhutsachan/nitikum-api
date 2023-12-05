package com.ldb.iadoc.Model.Branch;

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
public class BranchRes {
    private Message message;
    public List<Branch> resData;
}
