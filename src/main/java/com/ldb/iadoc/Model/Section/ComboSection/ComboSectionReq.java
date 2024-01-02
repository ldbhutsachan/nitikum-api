package com.ldb.iadoc.Model.Section.ComboSection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComboSectionReq {
    private String[] branchCode;
    private String secCode;
}
