package com.ldb.iadoc.Model.Department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private String deptId;
    private String deptDesc;
    private String deptLao;

    private String branchCode;
    private String branchNameEn;
    private String branchNameLa;
}
