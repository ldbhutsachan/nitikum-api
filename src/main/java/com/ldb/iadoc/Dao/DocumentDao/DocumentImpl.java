package com.ldb.iadoc.Dao.DocumentDao;

import com.ldb.iadoc.Contrller.LoginController;
import com.ldb.iadoc.Model.Document.*;
import com.ldb.iadoc.Model.GroupHeaderReq;
import com.ldb.iadoc.Model.Relation.Related;
import com.ldb.iadoc.Model.Relation.RelatedShow;
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
import java.util.ArrayList;
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
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] userArray = trimmedStr.split(",");

        System.out.println("show array:"+Arrays.toString(userArray));
        SQL="insert into DOC_SHARING (DOC_TYPE,USER_ALLOW,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        for (String user : userArray) {
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getDocNo(),
                    user,
                    documentReq.getSharingType());
        }
        return 1;
    }
    public int saveSharingDoSection(DocumentReq documentReq) {
        log.info("==========share data to User==========");
        String ListSection = documentReq.getShareUserById();
        String str = ListSection;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] SectionArray = trimmedStr.split(",");

        System.out.println("show array:"+Arrays.toString(SectionArray));
        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYSECTION,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        for (String section : SectionArray) {
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getDocNo(),
                    section,
                    documentReq.getSharingType());
        }
        return 1;
    }
    public int saveSharingDoBranch(DocumentReq documentReq,String keyDocNo) {
        String ListBranch = documentReq.getRelated();
        String str = ListBranch;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] bandArray = trimmedStr.split(",");
        System.out.println("show array:"+Arrays.toString(bandArray));
        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYBRANCH,CREATE_DATE," +
                "SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        for (String branch : bandArray) {
            IADOCJdbcTemplate.update(SQL,
                    keyDocNo,
                    //documentReq.getDocNo(),
                    branch,
                    documentReq.getSharingType());
        }
        return 1;
    }
    public int clearSharingDataFrist(DocumentReq documentReq) {
        SQL="delete from DOC_SHARING  where DOC_TYPE=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getDocNo()
        });
    }

    public int DOC_CREATE_TEMP(DocumentReq documentReq){
        log.info("==========share data to User==========");
        String ListBranch = documentReq.getRelated_No();
        String str = ListBranch;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] bandArray = trimmedStr.split(",");
        System.out.println("show array 99960:"+Arrays.toString(bandArray));
        log.info("show:"+documentReq.getDocDate());
        SQL="insert into DOC_CREATE_TEMP (SUBJECTNAME,DOC_NO,DOC_TYPE,DOC_DATE,RELATED,DOC_STATUS,DOC_PATH,DOC_PATH_LA,CREATED_DATE,MAKER_ID,SHARING_TYPE,DETAILS,type,RELETED_NO) " +
                "values (?,?,?,?,?,'W',?,?,sysdate,?,?,?,'1',?)";
        for (String branch : bandArray) {
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getSubjectName(),//ຫົວຂໍ້ເອກະສານ
                    documentReq.getDocNo(), //ລະຫັດເອກະສານ
                    documentReq.getDocType(), //ປະເພດເອກະສານ
                    documentReq.getDocDate(), //ເອກະສານລົງວັນທີ່
                    documentReq.getRelated(),//ເອກະສານຕິດພັນກັບສາຂາ/ຝ່າຍ
                    //documentReq.getDocStatus(),//ສະຖານະເອກະສານ W = Waiting for doc  U = Uploaded
                    documentReq.getDocPath(), //path ເກັບ ເອກະສານພາສາອັງກິດ
                    documentReq.getDocPathLa(),//path ເກັບ ເອກະສານພາສາລາວ
                    documentReq.getMarkerId(),//ຜູ້ສ້າງ
                    documentReq.getSharingType(),//ປະເພດການແບ່ງປັນເອກະສານ
                    documentReq.getDetails(),//ລາຍລະອຽດເອກະສານ
                    branch
            );
        }
        return -1;
    }
    public int saveRedNo(DocumentReq documentReq){
        String docNo =  documentReq.getDocNo();
        log.info("==========share data to User==========");
        String ListBranch = documentReq.getRelated_No();
        String str = ListBranch;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] bandArray = trimmedStr.split(",");
        System.out.println("show array 999:"+Arrays.toString(bandArray));
        SQL="insert into RELATED (SECTION_CODE,DOC_NO) values(?,?)";
        for (String branch : bandArray) {
            IADOCJdbcTemplate.update(SQL,
                    branch,
                    docNo
            );
        }
        return 1;
    }
    public int saveSharingDoBranchNoarray(DocumentReq documentReq,String keyDocNo) {

        String ListBranch = documentReq.getRelated();
        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYBRANCH,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        log.info("show sql array:"+SQL);
            IADOCJdbcTemplate.update(SQL,
                    keyDocNo,
                    ListBranch,
                    documentReq.getSharingType());
        return 1;
    }
    @Override
    public int ReadData(DocumentReq documentReq) {
        SQL="update DOC_SHARING set SES_STATUS='R',READBYDATE=sysdate,READBY=? where id=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getReadBy(),
                documentReq.getId()

        });
    }
    public int ReadDataState(DocumentReq documentReq) {
        SQL="insert into TB_READ_STATE (USER_READ,SHARE_ID,DATE_READ) values (?,?,syadte)";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getReadBy(),
                documentReq.getId()

        });
    }
    @Override
    public int SaveDocument(DocumentReq documentReq,String keyDocNo) throws ParseException {
     log.info("show:"+documentReq.getDocDate());
        SQL="insert into DOC_CREATE (STATUS_SHOW,SUBJECTNAME,DOC_NO,DOC_TYPE,DOC_DATE," +
                "RELATED,DOC_STATUS,DOC_PATH,DOC_PATH_LA,CREATED_DATE,MAKER_ID," +
                "SHARING_TYPE,DETAILS,type,RELETED_NAME,DOC_KEY) " +
                "values ('N',?,?,?,?,?,'W',?,?,sysdate,?,?,?,'1',?,?)";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
               documentReq.getSubjectName(),//ຫົວຂໍ້ເອກະສານ
                documentReq.getDocNo(), //ລະຫັດເອກະສານ
                documentReq.getDocType(), //ປະເພດເອກະສານ
                documentReq.getDocDate(), //ເອກະສານລົງວັນທີ່
                documentReq.getRelated(),//ເອກະສານຕິດພັນກັບສາຂາ/ຝ່າຍ
                //documentReq.getDocStatus(),//ສະຖານະເອກະສານ W = Waiting for doc  U = Uploaded
                documentReq.getDocPath(), //path ເກັບ ເອກະສານພາສາອັງກິດ
                documentReq.getDocPathLa(),//path ເກັບ ເອກະສານພາສາລາວ
                documentReq.getMarkerId(),//ຜູ້ສ້າງ
                documentReq.getSharingType(),//ປະເພດການແບ່ງປັນເອກະສານ
                documentReq.getDetails(),//ລາຍລະອຽດເອກະສານ
                documentReq.getRelated_Name(),
                keyDocNo
        });
    }
    public int upDateDocument(DocumentReq documentReq,String filesLaoPDF,String  filesEnPDF) throws ParseException {
        log.info("filesLaoPDF: " + filesLaoPDF);
        log.info("filesEnPDF: " + filesEnPDF);
        String con = "";
        String con2 = "";

        if (!"lo".equals(filesLaoPDF)) {
            con = " ,DOC_PATH_LA ='" + documentReq.getDocPathLa() + "' ";
        }
        if (!"en".equals(filesEnPDF)) {
            con2 = " ,DOC_PATH ='" + documentReq.getDocPath() + "'";
        }

        // Construct SQL query with the optional con and con2 parts
        String SQL = "UPDATE DOC_CREATE SET STATUS_SHOW=?, WHO_C_STATUS_SHOW=?, SUBJECTNAME=?, DOC_NO=?, DOC_TYPE=?, DOC_DATE=?, " +
                "RELATED=?, CREATED_DATE=sysdate, SHARING_TYPE=?, DETAILS=?, RELETED_NAME=? " + con + con2 + " WHERE id=?";

        log.info("show SQL: " + SQL);

        // Execute the update with parameters
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                documentReq.getSes_status(),      // Document status
                documentReq.getW_status_show(),   // Document close status
                documentReq.getSubjectName(),     // Document subject
                documentReq.getDocNo(),           // Document code
                documentReq.getDocType(),         // Document type
                documentReq.getDocDate(),         // Document date
                documentReq.getRelated(),         // Related branch or department
                documentReq.getSharingType(),     // Document sharing type
                documentReq.getDetails(),         // Document details
                documentReq.getRelated_Name(),    // Related name
                documentReq.getId()               // Document ID
        });

    }
    //=======================update status to show document ============================================
    public int updateStatusShow (StatusShowReq statusShowReq){
        try {
              SQL="update DOC_CREATE set  STATUS_SHOW=?,WHO_C_STATUS_SHOW=? where id= ?";
            return IADOCJdbcTemplate.update(SQL,new Object[]{
                    statusShowReq.getStatusShow(),//ຫົວຂໍ້ເອກະສານ
                    statusShowReq.getUserStatusShow(), //ລະຫັດເອກະສານ
                    statusShowReq.getId(), //ລະຫັດເອກະສານ
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int SaveDocumentExcutive(DocumentReq documentReq) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date doDate = sdf.parse(documentReq.getDocDate());
        java.sql.Date docDate = new java.sql.Date(doDate.getTime());

        SQL="insert into DOC_CREATE (SUBJECTNAME,DOC_NO,DOC_TYPE,DOC_DATE,RELATED,DOC_STATUS,DOC_PATH,DOC_PATH_LA,CREATED_DATE,MAKER_ID,SHARING_TYPE,DETAILS,taimard,years,type,CONNECT_NAME,CONNNECT_NAME2) " +
                "values (?,?,?,?,?,'W',?,?,sysdate,?,?,?,?,?,'2',?,?)";
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
                documentReq.getDetails(),//ລາຍລະອຽດເອກະສານ
                documentReq.getTaiMard(),
                documentReq.getYearIn(),
                documentReq.getConName(),
                documentReq.getConName2()

        });
    }
    @Override
    public int updateDocExcutive(DocumentReq documentReq) throws ParseException {

        String poEnd = documentReq.getDocDate();
        // Define the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Define the output date format
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MMM-dd");
        Date date = inputFormat.parse(poEnd);
        // Format the Date object to the desired output format
        String outputDateEnd = outputFormat.format(date).toUpperCase();

        SQL="update DOC_CREATE set SUBJECTNAME=?,DOC_NO=?,DOC_TYPE=?,DOC_DATE='"+outputDateEnd+"',RELATED=?,DOC_PATH_LA=?,CREATED_DATE=sysdate," +
                    "MAKER_ID=?,SHARING_TYPE=?,DETAILS=?,taimard=?,years=?,CONNECT_NAME=? where ID=?";
            log.info("SQL2:"+SQL);

        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getSubjectName(),//ຫົວຂໍ້ເອກະສານ
                documentReq.getDocNo(), //ລະຫັດເອກະສານ
                documentReq.getDocType(), //ປະເພດເອກະສານ
               // docDate, //ເອກະສານລົງວັນທີ່
                documentReq.getRelated(),//ເອກະສານຕິດພັນກັບສາຂາ/ຝ່າຍ
                //documentReq.getDocStatus(),//ສະຖານະເອກະສານ W = Waiting for doc  U = Uploaded
               // documentReq.getDocPath(), //path ເກັບ ເອກະສານພາສາອັງກິດ
                documentReq.getDocPathLa(),//path ເກັບ ເອກະສານພາສາລາວ
                documentReq.getMarkerId(),//ຜູ້ສ້າງ
                documentReq.getSharingType(),//ປະເພດການແບ່ງປັນເອກະສານ
                documentReq.getDetails(),//ລາຍລະອຽດເອກະສານ
                documentReq.getTaiMard(),
                documentReq.getYearIn(),
                documentReq.getConName(),
                documentReq.getId()
        });
    }
    public int updateDocExcutiveNofile(DocumentReq documentReq) throws ParseException {
        String poEnd = documentReq.getDocDate();
        // Define the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Define the output date format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy");
        Date date = inputFormat.parse(poEnd);
        // Format the Date object to the desired output format
        String outputDateEnd = outputFormat.format(date).toUpperCase();
        String filesEn = documentReq.getDocPathLa();
            SQL="update DOC_CREATE set SUBJECTNAME=?,DOC_NO=?,DOC_TYPE=?,DOC_DATE='"+outputDateEnd+"',RELATED=?,CREATED_DATE=sysdate," +
                    "MAKER_ID=?,SHARING_TYPE=?,DETAILS=?,taimard=?,years=?,CONNECT_NAME=? where ID=?";
            log.info("SQL1:"+SQL);
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                documentReq.getSubjectName(),//ຫົວຂໍ້ເອກະສານ
                documentReq.getDocNo(), //ລະຫັດເອກະສານ
                documentReq.getDocType(), //ປະເພດເອກະສານ
               // docDate, //ເອກະສານລົງວັນທີ່
                documentReq.getRelated(),//ເອກະສານຕິດພັນກັບສາຂາ/ຝ່າຍ
                //documentReq.getDocStatus(),//ສະຖານະເອກະສານ W = Waiting for doc  U = Uploaded
               // documentReq.getDocPath(), //path ເກັບ ເອກະສານພາສາອັງກິດ
               // documentReq.getDocPathLa(),//path ເກັບ ເອກະສານພາສາລາວ
                documentReq.getMarkerId(),//ຜູ້ສ້າງ
                documentReq.getSharingType(),//ປະເພດການແບ່ງປັນເອກະສານ
                documentReq.getDetails(),//ລາຍລະອຽດເອກະສານ
                documentReq.getTaiMard(),
                documentReq.getYearIn(),
                documentReq.getConName(),
                documentReq.getId()

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
        SQL="delete from DOC_CREATE  where id=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
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
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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

                tr.setDocKey(rs.getString("DOC_KEY"));
                return tr;
            }
        });
    }

    @Override
    public List<KeyReq> getMaxKey(){
        SQL="select max(id+1) docKey from doc_create";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<KeyReq>() {
            @Override
            public KeyReq mapRow(ResultSet rs, int rowNum) throws SQLException {
                KeyReq tr = new KeyReq();
                tr.setKeyDocNo(rs.getString("docKey"));

                return tr;
            }
        });
    }
    public List<Related> getRsplistRelated() {
        List<Related> data = new ArrayList<>();
        try {

            String SQL = "select a.DOC_TYPE, a.DOC_NO, a.SHAREBYBRANCH,\n" +
                    "b.BRANCH_CODE, b.BRANCH_NAME_LAO\n" +
                    "from doc_sharing a inner join branch b on\n" +
                    "a.SHAREBYBRANCH = b.BRANCH_CODE where b.type = '1' order by DOC_TYPE desc";
            log.info("Executing SQL: {}", SQL);
            data = IADOCJdbcTemplate.query(SQL, new RowMapper<Related>() {
                @Override
                public Related mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Related tr = new Related();
                    tr.setRelatedId(rs.getString("SHAREBYBRANCH"));
                    tr.setRelatedName(rs.getString("BRANCH_NAME_LAO"));
                    tr.setDocNo(rs.getString("DOC_TYPE"));
                    return tr;
                }
            });
            // Log the retrieved data
            if (data != null && !data.isEmpty()) {
                log.info("Related Data Retrieved: {}", data.toString());
            } else {
                log.info("No Related Data found.");
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving related data", e);
        }
        return data;
    }
            public List<Related> getRsplistBranCh() {
                List<Related> data = new ArrayList<>();
                try {

                    String SQL = "SELECT  a.DOC_KEY,\n" +
                            "                    a.doc_no,b.BRANCH_CODE,b.BRANCH_NAME_LAO\n" +
                            "                    FROM DOC_CREATE a \n" +
                            "                    JOIN  branch b ON ',' || a.RELETED_NAME || ',' LIKE '%,' || b.BRANCH_CODE || ',%' \n" +
                            "                    WHERE  a.type = '1'  AND a.DOC_NO IS NOT NULL  ORDER BY  a.DOC_NO DESC";
                    log.info("Executing SQL: {}", SQL);
                    data = IADOCJdbcTemplate.query(SQL, new RowMapper<Related>() {
                        @Override
                        public Related mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Related tr = new Related();
                            tr.setDocKey(rs.getString("DOC_KEY"));
                            tr.setRelatedId(rs.getString("BRANCH_CODE"));
                            tr.setRelatedName(rs.getString("BRANCH_NAME_LAO"));
                           // tr.setDocNo(rs.getString("DOC_TYPE"));
                            return tr;
                        }
                    });
            // Log the retrieved data
            if (data != null && !data.isEmpty()) {
                log.info("Related Data Retrieved: {}", data.toString());
            } else {
                log.info("No Related Data found.");
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving related data", e);
        }
        return data;
    }
    public List<RelatedShow> getRsplistRelatedShow() {
        List<RelatedShow> data = new ArrayList<>();
        try {
            String SQL = "SELECT  a.DOC_KEY,\n" +
                    "a.doc_no,b.BRANCH_CODE,b.BRANCH_NAME_LAO\n" +
                    "FROM DOC_CREATE a JOIN  branch b ON ',' || a.RELETED_NAME || ',' LIKE '%,' || b.BRANCH_CODE || ',%' WHERE  a.type = '1'  AND a.DOC_NO IS NOT NULL  ORDER BY  a.DOC_NO DESC ";
            log.info("Executing SQL: {}", SQL);

            data = IADOCJdbcTemplate.query(SQL, new RowMapper<RelatedShow>() {
                @Override
                public RelatedShow mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RelatedShow tr = new RelatedShow();
                    tr.setDocKey(rs.getString("DOC_KEY"));
                    tr.setRelatedShowId(rs.getString("BRANCH_CODE"));
                    tr.setRelatedShowName(rs.getString("BRANCH_NAME_LAO"));
                    tr.setRelatedShowDocNo(rs.getString("doc_no"));
                    return tr;
                }
            });
            // Log the retrieved data
            if (data != null && !data.isEmpty()) {
                log.info("Related Data Retrieved: {}", data.toString());
            } else {
                log.info("No Related Data found.");
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving related data", e);
        }
        return data;
    }

    @Override
    public List<DocumentAudit> getWaitListCheckByUser(DocumentReq documentReq) {
        SQL="select * from V_WAIT_DOCUMENT_LAW where type='1'  order by ID asc";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
               // tr.setRelated_No(rs.getString("SECTION_CODE"));
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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
                tr.setSes_status(rs.getString("SES_STATUS"));

                tr.setDocKey(rs.getString("DOC_KEY"));
                return tr;
            }
        });
    }
    @Override
    public List<DocumentAudit> getWaitListCheckExcutive(DocumentReq documentReq) {
       // SQL="select * from V_AUDIT_CHECK_EXCUTIVE where MAKER_ID='"+documentReq.getMarkerId()+"' order by ID asc";
        SQL="select * from V_AUDIT_CHECK_EXCUTIVE  order by ID asc";
        System.out.println("SQL:"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                //======================================connects
                tr.setConnects(rs.getString("connects"));
                tr.setConnectKanang(rs.getString("CONNNECT_NAME2"));
                tr.setConnectKanangAll(rs.getString("CONNNECT_NAMEALL"));
                tr.setTypeStatus(rs.getString("typeStatus"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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
                tr.setDetails(rs.getString("DETAILS"));

                return tr;
            }
        });
    }
    @Override
    public List<DocumentAudit> getShareDocument(DocumentReq documentReq) {
        // Initialize SQL query
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM V_DOCUMENT_FOR_ADMIN WHERE 1=1");

        // Add conditional logic based on user type
        String userType = documentReq.getUserType();
        String makerId = documentReq.getMarkerId();
        if ("A".equals(userType)) {
            sb.append(" AND ID IS NOT NULL");
        } else if ("M".equals(userType) || "U".equals(userType)) {
            sb.append(" AND (USER_NAME = ? OR SHARING_TYPE = ?)");
        } else {
            sb.append(" AND (USER_NAME = ? OR SHARING_TYPE = ?)");
        }

        // Append ORDER BY clause
        sb.append(" ORDER BY ID desc");

        // Convert StringBuilder to String
        String sql = sb.toString();
        log.info("Executing SQL query: {}", sql);

        // Prepare query parameters based on user type
        Object[] queryParams = "A".equals(userType) ?
                new Object[] {} : // No parameters for user type "A"
                new Object[] { makerId, "ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ" };

        // Execute query using JdbcTemplate
        return IADOCJdbcTemplate.query(sql, queryParams, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
                tr.setId(rs.getString("ID"));
                tr.setSubjectName(rs.getString("SubjectName"));
                tr.setApproveDate(rs.getString("APPROVE_DATE"));
                tr.setDocNo(rs.getString("DOC_NO"));
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
                tr.setDocKey(rs.getString("DOC_KEY"));
                tr.setAmt(rs.getLong("amt"));
                return tr;
            }
        });
    }

    public List<DocumentAudit> getShareDocumentReport(GroupHeaderReq documentReq) {
        StringBuilder sb = new StringBuilder();
        String status= documentReq.getStatus();
        String condictionStatus="";
        String condit = "";
        String condit01 = "";
        String conditType = "";
        String conditOrder = " order by DOC_DESC_LAO,Y,M,D DESC";
        if("0".equals(documentReq.getDocId())){
            conditType=" AND DOC_TYPE is not null ";
        }else
        {
            conditType=" and DOC_TYPE ='"+documentReq.getDocId()+"' ";
        }
        if("1".equals(status)){
                condictionStatus = " AND SES_STATUS ='N'";
        }
        else if("2".equals(status)){
            condictionStatus = " AND SES_STATUS ='O'";
        }else {
            condictionStatus = " AND SES_STATUS is not null ";
        }

        if (documentReq.getStartDate().equals("0") && documentReq.getEndDate().equals("0") ) {
            condit = " AND RELETED_NAME is not null ";
        } else if(!documentReq.getStartDate().equals("0")  && !documentReq.getEndDate().equals("0") ){
            condit = " AND RELETED_NAME is not null AND  DOC_DATESREACH between '"+documentReq.getStartDate()+"' and  '"+documentReq.getEndDate()+"'";
        }
        if(documentReq.getRelated_Name().equals("0")){
            condit01 = " ";
        }else{
            //
            condit01 = " AND RELETED_NAME is not null AND ( ',' || RELETED_NAME || ',' LIKE '%,"+documentReq.getRelated_Name()+",%') ";
        }
        sb.append("select * from V_REPORT where 1=1")
                .append(condit)
                .append(condit01)
                .append(condictionStatus)
                .append(conditType)
                .append(conditOrder);
        String sql = sb.toString();
        log.info("show sql:"+sql);
        return IADOCJdbcTemplate.query(sql, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setAmt(rs.getLong("amt"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
                tr.setDocKey(rs.getString("DOC_KEY"));
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentReportByText(GroupHeaderReq documentReq) {
            SQL="select * from V_DOCUMENT_FOR_ADMIN  where DOC_NO like '%"+documentReq.getTextSearch()+"%' \n" +
                    "or SUBJECTNAME like '%"+documentReq.getTextSearch()+"%'  \n" +
                    "or DOC_DESC_LAO like '%"+documentReq.getTextSearch()+"%'  ";
            log.info("SQL 01:"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    } public List<DocumentAudit> getShareDocumentReport(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN ";
            log.info("SQL:"+SQL);
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ' ";
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ' ";
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ' ";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentSubject(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN " +
                    "where SubjectName like'%"+documentReq.getSubjectName()+"%' or " +
                    "DOC_NO like'%"+documentReq.getSubjectName()+"%' ";
            log.info("SQL1:"+SQL);
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or DOC_NO like'%"+documentReq.getSubjectName()+"%' ";
            log.info("SQL2:"+SQL);
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or DOC_NO like'%"+documentReq.getSubjectName()+"%' ";
            log.info("SQL3:"+SQL);
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or DOC_NO like'%"+documentReq.getSubjectName()+"%'  ";
            log.info("SQL4:"+SQL);
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
                tr.setDocKey(rs.getString("DOC_KEY"));
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentKanang(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        String secCode = documentReq.getSecCode();
     //   String orderbyData = "\n order by ID asc";
        String conSecCode = " ";
        String conUserType = " ";
//        if("A".equals(userType)){
//            conUserType = "\n ";
//        }else if("M".equals(userType)){
//            conUserType = "\n AND USER_ALLOW =' "+documentReq.getMarkerId()+"'";
//        }
//        else if("U".equals(userType)){
//            conUserType = "\n AND USER_ALLOW is not null";
//        }
//        else {
//            conUserType = "\n AND USER_ALLOW is not null";
//        }
        if(secCode != null || secCode.equals(null)){
            conSecCode = "  AND (',' || RELETED_NAME || ',' LIKE '%,"+documentReq.getSecCode()+",%' ) ";
        }else
        {
            conSecCode = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM V_DOCUMENT_FOR_ADMIN WHERE 1=1 ");
        sb.append(conSecCode);
        sb.append(conUserType);
       // sb.append(orderbyData);
        String sql = sb.toString();
        log.info("show sqoL:"+sql);
        return IADOCJdbcTemplate.query(sql, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setRelated_Name(rs.getString("RELETED_NAME"));
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
                tr.setDocKey(rs.getString("DOC_KEY"));
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentDoctype(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        String related = documentReq.getSecCode();
       // RELETED_NAME like '%"+related+"%'
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  ";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"' and RELETED_NAME = '"+related+"' ";
            }
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"' ";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  and RELETED_NAME = '"+related+"' ";
            }
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  ";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where  RELETED_NAME ='"+related+"' and DOC_TYPE ='"+documentReq.getDocType()+"'  ";
            }
        }
        else {
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  order by ID asc";
            }else {
                log.info("userType:" + documentReq.getUserType());
                SQL = "select * from V_DOCUMENT_FOR_ADMIN where RELETED_NAME = '"+related+"' and DOC_TYPE ='" + documentReq.getDocType() + "' ";
            }
        }
        log.info("SQL 999:"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("taimard"));
                tr.setYearInDes(rs.getString("years"));
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
                tr.setDocKey(rs.getString("DOC_KEY"));
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
 @Override
    public List<DocumentAudit> getShareDocumentByCondition(docSerachReq docSerachReq) {
        if(docSerachReq.getIdYear().equals("0") && docSerachReq.getIdqter().equals("0") && docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where  TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(!docSerachReq.getIdYear().equals("0") && docSerachReq.getIdqter().equals("0") && docSerachReq.getIddocType().equals("0")){
         SQL="select * from v_doc_metting where YEARS='"+docSerachReq.getIdYear()+"' and TAIMARD !='5' order by ID asc";
         log.info("SQL01"+SQL);
        }
        else if(!docSerachReq.getIdYear().equals("0") && !docSerachReq.getIdqter().equals("0") && docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where YEARS='"+docSerachReq.getIdYear()+"' and TAIMARD='"+docSerachReq.getIdqter()+"' and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(!docSerachReq.getIdYear().equals("0") && !docSerachReq.getIdqter().equals("0") && !docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where YEARS='"+docSerachReq.getIdYear()+"' and TAIMARD='"+docSerachReq.getIdqter()+"'" +
                    " and DOC_TYPE='"+docSerachReq.getIddocType()+"' and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(docSerachReq.getIdYear().equals("0") && !docSerachReq.getIdqter().equals("0") && !docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where TAIMARD='"+docSerachReq.getIdqter()+"' and DOC_TYPE='"+docSerachReq.getIddocType()+"'  " +
                    "and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(docSerachReq.getIdYear().equals("0") && docSerachReq.getIdqter().equals("0") && !docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where DOC_TYPE='"+docSerachReq.getIddocType()+"'  and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(docSerachReq.getIdYear().equals("0") && !docSerachReq.getIdqter().equals("0") && docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where TAIMARD='"+docSerachReq.getIdqter()+"'  and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        else if(!docSerachReq.getIdYear().equals("0") && docSerachReq.getIdqter().equals("0") && !docSerachReq.getIddocType().equals("0")){
            SQL="select * from v_doc_metting where YEARS='"+docSerachReq.getIdYear()+"'  and TAIMARD !='5' order by ID asc";
            log.info("SQL01"+SQL);
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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
                tr.setDetails(rs.getString("DETAILS"));
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentByConditionText(docSerachReq docSerachReq) {
            SQL="select * from v_doc_metting where  " +
                    "TAIMARD like '%"+docSerachReq.getTextSearch()+"%' " +
                    "OR QTER like '"+docSerachReq.getTextSearch()+"' OR\n" +
                    "DOC_DESC_LAO like '%"+docSerachReq.getTextSearch()+"%' OR \n" +
                    "CREATEBY like '%"+docSerachReq.getTextSearch()+"%' OR \n" +
                    "DOC_NO like '%"+docSerachReq.getTextSearch()+"%' OR \n" +
                    "SUBJECTNAME like '%"+docSerachReq.getTextSearch()+"%' \n" +
                    "order by ID asc";
            log.info("SQL01"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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
                tr.setDetails(rs.getString("DETAILS"));
               // tr.setStatus(rs.getString("status"));
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
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' or SHARING_TYPE ='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ'  order by ID asc";
        }else if(documentReq.getStartDate() != null){
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' and  " +
                    "CREATED_DATE between '"+docDate+"' and '"+endDate+"' or SHARING_TYPE ='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ' order by ID asc";
        }else {
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' or SHARING_TYPE ='ອະນຸຍາດໃຫ້ທຸກຄົນເຂົ້າເຖິງ' order by ID asc";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<DocumentAudit>() {
            @Override
            public DocumentAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
                DocumentAudit tr = new DocumentAudit();
                tr.setConnects(rs.getString("connects"));
                tr.setTaiMard(rs.getString("taimard"));
                tr.setYearIn(rs.getString("years"));
                tr.setTaiMardDes(rs.getString("QTER"));
                tr.setYearInDes(rs.getString("YEARIN"));
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
