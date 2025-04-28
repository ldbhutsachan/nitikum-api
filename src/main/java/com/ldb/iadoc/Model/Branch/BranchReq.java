package com.ldb.iadoc.Model.Branch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BranchReq {

    private String branchCode;
    private String brName;
    private String brNameLa;
    private String location;
    private String brType;
    private String status;
    private String underBr;
    private String iD;
}
