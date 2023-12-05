package com.ldb.iadoc.Model.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserType {
    private String id;
    private String userType;
    private String typeDesc;
    private String typeDescLao;
}
