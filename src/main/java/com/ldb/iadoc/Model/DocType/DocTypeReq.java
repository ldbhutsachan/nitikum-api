package com.ldb.iadoc.Model.DocType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocTypeReq {
    private String docType;
    private String docDesc;
    private String docDescLao;
}
