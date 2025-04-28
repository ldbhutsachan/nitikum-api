package com.ldb.iadoc.Model;

import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.Document.GroupHeaderReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupHeaderRes {
    private Message message;
    private List<GroupHeader> groupHeader;
    private List<GroupHeaderReport> groupHeaderTotal;
}
