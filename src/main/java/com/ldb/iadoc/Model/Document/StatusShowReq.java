package com.ldb.iadoc.Model.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusShowReq {
private String statusShow;
private String userStatusShow;
private String id;
}
