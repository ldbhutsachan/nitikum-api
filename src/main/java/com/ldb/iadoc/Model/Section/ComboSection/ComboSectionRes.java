package com.ldb.iadoc.Model.Section.ComboSection;

import com.ldb.iadoc.Mesage.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComboSectionRes {
private Message message;
private List<ComboSection> resData;
}
