package com.ldb.iadoc.Model;

import com.ldb.iadoc.Model.Document.DocumentAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupHeader {

    private String related_Name;
    private String related_amt;
    private List<DocumentAudit> details;
}
