package com.ldb.iadoc.Model.Document.Report;

import com.ldb.iadoc.Mesage.Message;
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
public class DocumentReportRes {
private Message message;
List<DocumentAudit> resData;
}
