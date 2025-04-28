package com.ldb.iadoc.Model.Relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RelatedShow {
private String docKey;
private String RelatedShowId;
private String RelatedShowName;
private String RelatedShowDocNo;
    @Override
    public String toString() {
        return "RelatedShow{" +
                "RelatedShowId='" + RelatedShowId + '\'' +
                ", RelatedShowName='" + RelatedShowName + '\'' +
                ", RelatedShowDocNo='" + RelatedShowDocNo + '\'' +
                '}';
    }
}
