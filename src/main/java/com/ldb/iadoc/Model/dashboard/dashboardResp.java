package com.ldb.iadoc.Model.dashboard;

import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.DocType.DocType;
import lombok.Data;

import java.util.List;

@Data
public class dashboardResp {
    private Message message;

    private caltotal caltotalGroup;
    private List<transaction> transactionsGroup;
    private List<section> sectionGroup;

    @Data
    public class caltotal{
        private String totalAmt;
        private String totalAmtOld;
        private String totalAmtNew;
        private String totalAmtDocNew;
        private String totalAmtDocOld;
    }

    @Data
    public class transaction{
        private String typeDocumentName;
        private String totalAmt;
    }

    @Data
    public class section{
        private String secName;
        private String totalAmt;
    }
}


