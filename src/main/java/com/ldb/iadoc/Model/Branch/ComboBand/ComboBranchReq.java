package com.ldb.iadoc.Model.Branch.ComboBand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComboBranchReq {

    private String branchCode;
    private String brName;
    private String brNameLa;
    private String location;
    private String brType;
    private String underBr;
    private String iD;
}
