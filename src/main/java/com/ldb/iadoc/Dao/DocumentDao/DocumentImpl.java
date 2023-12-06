package com.ldb.iadoc.Dao.DocumentDao;

import com.ldb.iadoc.Contrller.LoginController;
import com.ldb.iadoc.Model.Document.Document;
import com.ldb.iadoc.Model.Document.DocumentAudit;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Share.ShareReq;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;

import java.util.List;
@Service
public class DocumentImpl implements DocumentDao {
    public static final Logger log = LogManager.getLogger(DocumentDao.class);

    @Autowired
    @Qualifier("IADOCJdbcTemplate")
    private JdbcTemplate IADOCJdbcTemplate;
    String SQL="";
    @Override
    public int rejectDocument(DocumentReq documentReq) {
        log.info("==================Reject By===================="+documentReq.getRejectBy());
        SQL= "update doc_create set DOC_STATUS='RE',REJECT_BY=?,REJECT_DATE=sysdate \n" +
                "where ID=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
              documentReq.getRejectBy(),
              documentReq.getId()
        });
    }
    @Override
    public int saveSharingDo(DocumentReq documentReq) {
        log.info("==========share data to User==========");
        String ListUser = documentReq.getShareUserById();
        String str = ListUser;
        String trimmedStr = str.substring(1, str.length() - 1);
        String[] userArray = trimmedStr.split("\\],\\[");
        System.out.println("show array:"+Arrays.toString(userArray));
        SQL="insert into DOC_SHARING (DOC_TYPE,USER_ALLOW,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        for (String user : userArray) {
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getDocNo(),
                    user,
                    documentReq.getSharingType());
        }
        return 0;
    }
    @Override
    public int ReadData(DocumentReq documentReq) {
        SQL="update DOC_SHARING set SES_STATUS='R',READ_DATE=sysdate where DOC_TYPE=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getDocNo()
        });
    }
    @Override
    public int SaveDocument(DocumentReq documentReq) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date doDate = sdf.parse(documentReq.getDocDate());
        java.sql.Date docDate = new java.sql.Date(doDate.getTime());
        SQL="insert into DOC_CREATE (SUBJECTNAME,DOC_NO,DOC_TYPE,DOC_DATE,RELATED,DOC_STATUS,DOC_PATH,DOC_PATH_LA,CREATED_DATE,MAKER_ID,SHARING_TYPE,DETAILS) " +
                "values (?,?,?,?,?,'W',?,?,sysdate,?,?,?)";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
               documentReq.getSubjectName(),//ຫົວຂໍ້ເອກະສານ
                documentReq.getDocNo(), //ລະຫັດເອກະສານ
                documentReq.getDocType(), //ປະເພດເອກະສານ
                docDate, //ເອກະສານລົງວັນທີ່
                documentReq.getRelated(),//ເອກະສານຕິດພັນກັບສາຂາ/ຝ່າຍ
                //documentReq.getDocStatus(),//ສະຖານະເອກະສານ W = Waiting for doc  U = Uploaded
                documentReq.getDocPath(), //path ເກັບ ເອກະສານພາສາອັງກິດ
                documentReq.getDocPathLa(),//path ເກັບ ເອກະສານພາສາລາວ
                documentReq.getMarkerId(),//ຜູ້ສ້າງ
                documentReq.getSharingType(),//ປະເພດການແບ່ງປັນເອກະສານ
                documentReq.getDetails()//ລາຍລະອຽດເອກະສານ
        });
    }
    @Override
    public int UpdateDocument(DocumentReq documentReq) {
        SQL="update  DOC_CREATE set doc_no=?,doc_type=?,related=?,doc_path=?,UPDATE_BY=?,LAST_UPDATEDATE=sysdate,sharing_type=? where id=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getDocNo(),
                documentReq.getDocType(),
                documentReq.getRelated(),
                documentReq.getDocPath(),
                documentReq.getUpdateId(),
                // documentReq.getDocStatus(),
                documentReq.getSharingType(),
                documentReq.getId()
        });
    }

    @Override
    public int DelDocument(DocumentReq documentReq) {
        SQL="update  DOC_CREATE set DELETE_BY=?,DELETE_DATE=sysdate,DOC_STATUS='D'  where id=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getDeleteId(),
                documentReq.getId()
        });
    }
    @Override
    public List<Document> getDocument(DocumentReq documentReq) {
        return null;
    }

    @Override
    public int AuditDocument(DocumentReq documentReq) {
        SQL="update  DOC_CREATE set APPROVE_ID=?,APPROVE_DATE=sysdate,DOC_STATUS='U'  where id=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getApproveId(),
                documentReq.getId()
        });
    }
    @Override
    public List<DocumentAudit> getAuditDocument(DocumentReq documentReq) {
        SQL="select * from V_AUDIT_CHECK order by ID asc";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setId(rs.getString("ID"));
                tr.setDocNo(rs.getString("DOC_NO"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setApproveDate(rs.getString("APPROVE_DATE"));
                tr.setRelated(rs.getString("RELATED"));
                tr.setDepDescEN(rs.getString("DEPT_DESC_EN"));
                tr.setDepDescLAO(rs.getString("DEPT_DESC_LAO"));
                tr.setDocPath(rs.getString("DOC_PATH"));
                tr.setCreateDate(rs.getString("CREATED_DATE"));
                tr.setMarkerId(rs.getString("MAKER_ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setDocType(rs.getString("DOC_TYPE"));
                tr.setDocDescEn(rs.getString("DOC_DESC"));
                tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
                tr.setDocStatus(rs.getString("DOC_STATUS"));
                tr.setSharingType(rs.getString("SHARING_TYPE"));
                tr.setDocPathLa(rs.getString("DOC_PATH_LA"));
                tr.setDocDate(rs.getString("DOC_DATE"));
                return tr;
            }
        });
    }
    @Override
    public List<DocumentAudit> getWaitListCheckByUser(DocumentReq documentReq) {
        SQL="select * from V_AUDIT_CHECK where MAKER_ID='"+documentReq.getMarkerId()+"' order by ID asc";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setId(rs.getString("ID"));
                tr.setDocNo(rs.getString("DOC_NO"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setApproveDate(rs.getString("APPROVE_DATE"));
                tr.setRelated(rs.getString("RELATED"));
                tr.setDepDescEN(rs.getString("DEPT_DESC_EN"));
                tr.setDepDescLAO(rs.getString("DEPT_DESC_LAO"));
                tr.setDocPath(rs.getString("DOC_PATH"));
                tr.setCreateDate(rs.getString("CREATED_DATE"));
                tr.setMarkerId(rs.getString("MAKER_ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setDocType(rs.getString("DOC_TYPE"));
                tr.setDocDescEn(rs.getString("DOC_DESC"));
                tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
                tr.setDocStatus(rs.getString("DOC_STATUS"));
                tr.setSharingType(rs.getString("SHARING_TYPE"));
                tr.setDocPathLa(rs.getString("DOC_PATH_LA"));
                tr.setDocDate(rs.getString("DOC_DATE"));
                return tr;
            }
        });
    }
    @Override
    public List<DocumentAudit> getShareDocument(DocumentReq documentReq) {
        SQL="select * from V_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' order by ID asc";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setId(rs.getString("ID"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setApproveDate(rs.getString("APPROVE_DATE"));
                tr.setDocNo(rs.getString("DOC_NO"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setRelated(rs.getString("RELATED"));
                tr.setDepDescEN(rs.getString("DEPT_DESC_EN"));
                tr.setDepDescLAO(rs.getString("DEPT_DESC_LAO"));
                tr.setDocPath(rs.getString("DOC_PATH"));
                tr.setCreateDate(rs.getString("CREATED_DATE"));
                tr.setMarkerId(rs.getString("MAKER_ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setDocType(rs.getString("DOC_TYPE"));
                tr.setDocDescEn(rs.getString("DOC_DESC"));
                tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
                tr.setDocStatus(rs.getString("SES_STATUS"));
                tr.setSharingType(rs.getString("SHARING_TYPE"));
                tr.setDocPathLa(rs.getString("DOC_PATH_LA"));
                tr.setDocDate(rs.getString("DOC_DATE"));
                tr.setCreateBy(rs.getString("createBy"));
                return tr;
            }
        });
    }

    @Override
    public List<DocumentAudit> getReportDocument(DocumentReq documentReq) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date doDate = sdf.parse(documentReq.getStartDate());
        java.sql.Date docDate = new java.sql.Date(doDate.getTime());
        Date endDate01 = sdf.parse(documentReq.getEndDate());
        java.sql.Date endDate = new java.sql.Date(endDate01.getTime());
        log.info("docDate:"+docDate);
        log.info("endDate:"+endDate);
        if(documentReq.getStartDate() == null){
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"'  order by ID asc";
        }else if(documentReq.getStartDate() != null){
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' and  " +
                    "CREATED_DATE between '"+docDate+"' and '"+endDate+"' order by ID asc";
        }else {
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' order by ID asc";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setId(rs.getString("ID"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setApproveDate(rs.getString("APPROVE_DATE"));
                tr.setDocNo(rs.getString("DOC_NO"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setRelated(rs.getString("RELATED"));
                tr.setDepDescEN(rs.getString("DEPT_DESC_EN"));
                tr.setDepDescLAO(rs.getString("DEPT_DESC_LAO"));
                tr.setDocPath(rs.getString("DOC_PATH"));
                tr.setCreateDate(rs.getString("CREATED_DATE"));
                tr.setMarkerId(rs.getString("MAKER_ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setDocType(rs.getString("DOC_TYPE"));
                tr.setDocDescEn(rs.getString("DOC_DESC"));
                tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
                tr.setDocStatus(rs.getString("SES_STATUS"));
                tr.setSharingType(rs.getString("SHARING_TYPE"));
                tr.setDocPathLa(rs.getString("DOC_PATH_LA"));
                tr.setDocDate(rs.getString("DOC_DATE"));
                tr.setCreateBy(rs.getString("createBy"));
                return tr;
            }
        });
    }
}
