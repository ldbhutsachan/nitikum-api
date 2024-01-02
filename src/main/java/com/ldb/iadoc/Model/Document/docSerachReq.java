package com.ldb.iadoc.Model.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class docSerachReq {
    private String userId;
    private String idYear;
    private String idqter;
    private String iddocType;
    private String textSearch;

}
