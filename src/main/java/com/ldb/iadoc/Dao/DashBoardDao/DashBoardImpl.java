package com.ldb.iadoc.Dao.DashBoardDao;

import com.ldb.iadoc.Model.dashboard.dashboardReq;
import com.ldb.iadoc.Model.dashboard.dashboardResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DashBoardImpl implements DashBoardDao {

    @Autowired
    @Qualifier("IADOCJdbcTemplate")
    private JdbcTemplate IADOCJdbcTemplate;

    @Override
    public dashboardResp.caltotal dashboard(dashboardReq dashboardReq) {
        String startDate = dashboardReq.getStartDate();
        String endDate = dashboardReq.getEndDate();

        // Convert yyyy-MM-dd → yyyyMMdd
        String conStartDate = (startDate != null && !startDate.isEmpty())
                ? startDate.replace("-", "") : null;
        String conEndDate = (endDate != null && !endDate.isEmpty())
                ? endDate.replace("-", "") : null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("    COUNT(*) AS total_amt, ")
                .append("    COUNT(CASE WHEN STATUS_SHOW = 'O' THEN 1 END) AS total_amt_old, ")
                .append("    COUNT(CASE WHEN STATUS_SHOW = 'N' THEN 1 END) AS total_amt_new, ")
                .append("    COUNT(CASE WHEN TYPE_DOC = 'ຮ່າງໃໝ່' THEN 1 END) AS total_amt_doc_new, ")
                .append("    COUNT(CASE WHEN TYPE_DOC = 'ປັບປຸງ' THEN 1 END) AS total_amt_doc_old ")
                .append("FROM doc_create a ")
                .append("INNER JOIN doc_type b ON b.DOC_TYPE = a.DOC_TYPE ")
                .append("WHERE a.TYPE='1' and 1=1 ");

        // Dynamic conditions
        if (conStartDate != null && conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') BETWEEN ? AND ? ");
        } else if (conStartDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
        } else if (conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
        }

        String sql = sb.toString();

        // Build params dynamically
        Object[] params;
        if (conStartDate != null && conEndDate != null) {
            params = new Object[]{conStartDate, conEndDate};
        } else if (conStartDate != null) {
            params = new Object[]{conStartDate};
        } else if (conEndDate != null) {
            params = new Object[]{conEndDate};
        } else {
            params = new Object[]{};
        }
        log.info("sql dashboard:"+sql);

        return IADOCJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            dashboardResp resp = new dashboardResp();
            dashboardResp.caltotal ct = resp.new caltotal();
            ct.setTotalAmt(rs.getString("total_amt"));
            ct.setTotalAmtOld(rs.getString("total_amt_old"));
            ct.setTotalAmtNew(rs.getString("total_amt_new"));
            ct.setTotalAmtDocNew(rs.getString("total_amt_doc_new"));
            ct.setTotalAmtDocOld(rs.getString("total_amt_doc_old"));
            return ct;
        });
    }


    @Override
    public List<dashboardResp.transaction> transaction(dashboardReq dashboardReq) {
        String startDate = dashboardReq.getStartDate();
        String endDate = dashboardReq.getEndDate();

        String conStartDate = (startDate != null && !startDate.isEmpty())
                ? startDate.replace("-", "") : null;
        String conEndDate = (endDate != null && !endDate.isEmpty())
                ? endDate.replace("-", "") : null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DOC_DESC_LAO AS type_document_name, COUNT(*) AS total_amt ")
                .append("FROM doc_create a ")
                .append("INNER JOIN doc_type b ON b.DOC_TYPE = a.DOC_TYPE ")
                .append("WHERE a.TYPE='1' and 1=1 ");

        Object[] params;
        if (conStartDate != null && conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') BETWEEN ? AND ? ");
            params = new Object[]{conStartDate, conEndDate};
        } else if (conStartDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conStartDate};
        } else if (conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conEndDate};
        } else {
            params = new Object[]{};
        }

        sb.append(" GROUP BY DOC_DESC_LAO ORDER BY COUNT(*) DESC");

       log.info("dashboard :"+sb.toString());

        return IADOCJdbcTemplate.query(sb.toString(), params, (rs, rowNum) -> {
            dashboardResp resp = new dashboardResp();
            dashboardResp.transaction tr = resp.new transaction();
            tr.setTypeDocumentName(rs.getString("type_document_name"));
            tr.setTotalAmt(rs.getString("total_amt"));
            return tr;
        });
    }

    @Override
    public List<dashboardResp.section> section(dashboardReq dashboardReq) {
        String startDate = dashboardReq.getStartDate();
        String endDate = dashboardReq.getEndDate();

        String conStartDate = (startDate != null && !startDate.isEmpty())
                ? startDate.replace("-", "") : null;
        String conEndDate = (endDate != null && !endDate.isEmpty())
                ? endDate.replace("-", "") : null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT BRANCH_NAME_LAO AS sec_name, COUNT(*) AS total_amt ")
                .append("FROM doc_create a ")
                .append("INNER JOIN branch d ON ',' || a.RELETED_NAME || ',' LIKE '%,' || d.BRANCH_CODE || ',%' ")
                .append("WHERE  a.TYPE='1' and  1=1 ");

        Object[] params;
        if (conStartDate != null && conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') BETWEEN ? AND ? ");
            params = new Object[]{conStartDate, conEndDate};
        } else if (conStartDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conStartDate};
        } else if (conEndDate != null) {
            sb.append(" AND TO_CHAR(DOC_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conEndDate};
        } else {
            params = new Object[]{};
        }

        sb.append(" GROUP BY BRANCH_NAME_LAO ORDER BY COUNT(*) DESC");

        return IADOCJdbcTemplate.query(sb.toString(), params, (rs, rowNum) -> {
            dashboardResp resp = new dashboardResp();
            dashboardResp.section sec = resp.new section();
            sec.setSecName(rs.getString("sec_name"));
            sec.setTotalAmt(rs.getString("total_amt"));
            return sec;
        });
    }

    @Override
    public List<dashboardResp.daily> daily(dashboardReq dashboardReq) {
        String startDate = dashboardReq.getStartDate();
        String endDate = dashboardReq.getEndDate();

        // Convert yyyy-MM-dd → yyyyMMdd
        String conStartDate = (startDate != null && !startDate.isEmpty())
                ? startDate.replace("-", "") : null;
        String conEndDate = (endDate != null && !endDate.isEmpty())
                ? endDate.replace("-", "") : null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  ")
                .append("TO_CHAR(CREATED_DATE, 'MM') AS txn_date, ")
                .append("COUNT(*) AS total_amt ")
                .append("FROM doc_create a ")
                .append("INNER JOIN doc_type b ON b.DOC_TYPE = a.DOC_TYPE ")
                .append("WHERE  a.TYPE='1' and  1=1 ");

        Object[] params;
        if (conStartDate != null && conEndDate != null) {
            sb.append(" AND TO_CHAR(CREATED_DATE, 'YYYYMMDD') BETWEEN ? AND ? ");
            params = new Object[]{conStartDate, conEndDate};
        } else if (conStartDate != null) {
            sb.append(" AND TO_CHAR(CREATED_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conStartDate};
        } else if (conEndDate != null) {
            sb.append(" AND TO_CHAR(CREATED_DATE, 'YYYYMMDD') = ? ");
            params = new Object[]{conEndDate};
        } else {
            params = new Object[]{};
        }

        sb.append(" GROUP BY  TO_CHAR(CREATED_DATE, 'MM') ")
                .append("ORDER BY TO_CHAR(CREATED_DATE, 'MM') ASC");

        return IADOCJdbcTemplate.query(sb.toString(), params, (rs, rowNum) -> {
            dashboardResp resp = new dashboardResp();
            dashboardResp.daily dl = resp.new daily();
            dl.setTxnDate(rs.getString("txn_date"));
            dl.setTotalAmt(rs.getString("total_amt"));
            dl.setName("-");
            return dl;
        });
    }

}
