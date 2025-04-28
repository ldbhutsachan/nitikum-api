package com.ldb.iadoc.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupHeaderReq {
    private String startDate;
    private String endDate;
    private String related_Name;
    private String status;
    private String textSearch;
    private String docId;
}
