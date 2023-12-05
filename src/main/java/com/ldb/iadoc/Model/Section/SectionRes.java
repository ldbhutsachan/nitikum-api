package com.ldb.iadoc.Model.Section;

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
public class SectionRes {
private Message message;
private List<Section> resData;
}
