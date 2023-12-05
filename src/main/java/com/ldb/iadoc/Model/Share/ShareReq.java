package com.ldb.iadoc.Model.Share;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShareReq {
    private int shareNo;
    private String docTypeNo;
    private String userAllow;
    private String sessionType;

}

