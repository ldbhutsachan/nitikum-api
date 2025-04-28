package com.ldb.iadoc.Model.Relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Related {
    private String relatedId;
    private String relatedName;
    private String docNo;
    private String docKey;
    @Override
    public String toString() {
        return "Related{" +
                "relatedId='" + relatedId + '\'' +
                ", relatedName='" + relatedName + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }

}
