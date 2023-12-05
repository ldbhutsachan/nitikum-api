package com.ldb.iadoc.Model.Section;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    private String secId;
    private String secCode;
    private String secDesc;
    private String secDescLao;
    private String deptCode;
    private String deptEN;
    private String deptLa;
}
