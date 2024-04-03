package com.ldb.iadoc.Dao.DocumentDao;

import com.ldb.iadoc.Contrller.LoginController;
import com.ldb.iadoc.Model.Document.Document;
import com.ldb.iadoc.Model.Document.DocumentAudit;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Document.docSerachReq;
import com.ldb.iadoc.Model.GroupHeaderReq;
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
    public int saveSharingDoBranch(DocumentReq documentReq) {
        log.info("==========share data to User==========");
        String ListBranch = documentReq.getRelated();
        String str = ListBranch;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] bandArray = trimmedStr.split(",");
        System.out.println("show array:"+Arrays.toString(bandArray));
        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYBRANCH,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
        for (String branch : bandArray) {
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getDocNo(),
                    branch,
                    documentReq.getSharingType());
        }
        return 1;
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
        log.info("==========share data to User==========");
        String ListBranch = documentReq.getRelated_No();
        String str = ListBranch;
        String trimmedStr = str.substring(0, str.length() - 0);
        String[] bandArray = trimmedStr.split(",");
        System.out.println("show array 999:"+Arrays.toString(bandArray));
        SQL="insert into RELATED (SECTION_CODE) values(?)";
        for (String branch : bandArray) {
            IADOCJdbcTemplate.update(SQL,
                    branch
            );
        }
        return 1;
    }
    public int saveSharingDoBranchNoarray(DocumentReq documentReq) {
        log.info("==========share data to User==========");
        String ListBranch = documentReq.getRelated();
        SQL="insert into DOC_SHARING (DOC_TYPE,SHAREBYBRANCH,CREATE_DATE,SESSION_TYPE,SES_STATUS) values(?,?,sysdate,?,'U')";
            IADOCJdbcTemplate.update(SQL,
                    documentReq.getDocNo(),
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
    public int SaveDocument(DocumentReq documentReq) throws ParseException {
     log.info("show:"+documentReq.getDocDate());
        SQL="insert into DOC_CREATE (SUBJECTNAME,DOC_NO,DOC_TYPE,DOC_DATE,RELATED,DOC_STATUS,DOC_PATH,DOC_PATH_LA,CREATED_DATE,MAKER_ID,SHARING_TYPE,DETAILS,type,RELETED_NAME) " +
                "values (?,?,?,?,?,'W',?,?,sysdate,?,?,?,'1',?)";
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
                documentReq.getRelated_Name()
        });
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
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date doDate = sdf.parse(documentReq.getDocDate());
//        java.sql.Date docDate = new java.sql.Date(doDate.getTime());
//        log.info("shiw:"+documentReq.getDocPathLa());
        log.info("lo:"+documentReq.getDocPathLa());
        if(documentReq.getDocPathLa() == null || documentReq.getDocPathLa() == ""){
            SQL="update DOC_CREATE set SUBJECTNAME=?,DOC_NO=?,DOC_TYPE=?,DOC_DATE=TO_DATE('"+documentReq.getDocDate()+"', 'DD/MM/YYYY'),RELATED=?,CREATED_DATE=sysdate," +
                    "MAKER_ID=?,SHARING_TYPE=?,DETAILS=?,taimard=?,years=?,CONNECT_NAME=? where ID=?";
            log.info("SQL1:"+SQL);
        }else {
            SQL="update DOC_CREATE set SUBJECTNAME=?,DOC_NO=?,DOC_TYPE=?,DOC_DATE=TO_DATE('"+documentReq.getDocDate()+"', 'DD/MM/YYYY'),RELATED=?,DOC_PATH_LA=?,CREATED_DATE=sysdate," +
                    "MAKER_ID=?,SHARING_TYPE=?,DETAILS=?,taimard=?,years=?,CONNECT_NAME=? where ID=?";
            log.info("SQL2:"+SQL);
        }
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
                return tr;
            }
        });
    }
    @Override
    public List<DocumentAudit> getWaitListCheckByUser(DocumentReq documentReq) {
        SQL="select * from V_WAIT_DOCUMENT_LAW where type='1' and MAKER_ID='"+documentReq.getMarkerId()+"' order by ID asc";
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
        String userType= documentReq.getUserType();
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN order by ID asc";
            log.info("SQL:"+SQL);
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
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
    public List<DocumentAudit> getShareDocumentReport(GroupHeaderReq documentReq) {
        if(documentReq.getStartDate() == null  && documentReq.getRelated_Name().equals("0")){
            SQL="select * from V_DOCUMENT_FOR_ADMIN order by ID asc";
            log.info("SQL 01:"+SQL);
        }
        else if(documentReq.getStartDate()== null   && !documentReq.getRelated_Name().equals("0")){
            SQL="select * from V_DOCUMENT_FOR_ADMIN where RELETED_NAME='"+documentReq.getRelated_Name()+"' order by ID asc";
            log.info("SQL 02:"+SQL);
        }
        else if(documentReq.getStartDate()!= null  && !documentReq.getRelated_Name().equals("0") ){
            SQL="select * from V_DOCUMENT_FOR_ADMIN where RELETED_NAME='"+documentReq.getRelated_Name()+"' and DOC_DATESREACH between '"+documentReq.getStartDate()+"' and  '"+documentReq.getEndDate()+"'  order by ID asc";
            log.info("SQL 03:"+SQL);
        }
        else if(documentReq.getStartDate() != null  && documentReq.getRelated_Name().equals("0")){
            SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_DATESREACH between '"+documentReq.getStartDate()+"' and  '"+documentReq.getEndDate()+"' order by ID asc";
            log.info("SQL 04:"+SQL);
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
    public List<DocumentAudit> getShareDocumentReportByText(GroupHeaderReq documentReq) {
            SQL="select * from V_DOCUMENT_FOR_ADMIN  where DOC_NO like '%"+documentReq.getTextSearch()+"%' \n" +
                    "or SUBJECTNAME like '%"+documentReq.getTextSearch()+"%'  \n" +
                    "or DOC_DESC_LAO like '%"+documentReq.getTextSearch()+"%'  order by ID asc";
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
            SQL="select * from V_DOCUMENT_FOR_ADMIN order by ID asc";
            log.info("SQL:"+SQL);
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_NAME='"+documentReq.getMarkerId()+"' OR SHARING_TYPE='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
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
            SQL="select * from V_DOCUMENT_FOR_ADMIN where SubjectName like'%"+documentReq.getSubjectName()+"%' or SubjectName like'%"+documentReq.getDocNo()+"%'  order by ID asc";
            log.info("SQL1:"+SQL);
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or SubjectName like'%"+documentReq.getDocNo()+"%'  order by ID asc";
            log.info("SQL2:"+SQL);
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or SubjectName like'%"+documentReq.getDocNo()+"%'  order by ID asc";
            log.info("SQL3:"+SQL);
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where USER_ALLOW ='"+documentReq.getMarkerId()+"' " +
                    "OR SubjectName like'%"+documentReq.getSubjectName()+"%' or SubjectName like'%"+documentReq.getDocNo()+"%'  order by ID asc";
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
               // tr.setStatus(rs.getString("status"));
                return tr;
            }
        });
    }
    public List<DocumentAudit> getShareDocumentKanang(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT_FOR_ADMIN where RELETED_NAME like '%"+documentReq.getSecCode()+"%'  order by ID asc";
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where RELETED_NAME like '%"+documentReq.getSecCode()+"%' and USER_ALLOW ='"+documentReq.getMarkerId()+"' order by ID asc";
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where RELETED_NAME like '%"+documentReq.getSecCode()+"%' and USER_ALLOW ='"+documentReq.getMarkerId()+"' order by ID asc";
        }
        else {
            log.info("userType:"+documentReq.getUserType());
            SQL="select * from V_DOCUMENT where RELETED_NAME like '%"+documentReq.getSecCode()+"%' and USER_ALLOW ='"+documentReq.getMarkerId()+"' order by ID asc";
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
    public List<DocumentAudit> getShareDocumentDoctype(DocumentReq documentReq) {
        String userType= documentReq.getUserType();
        String related = documentReq.getSecCode();
       // RELETED_NAME like '%"+related+"%'
        if(userType.equals("A")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  order by ID asc";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"' and RELETED_NAME like '%"+related+"%'  order by ID asc";
            }
        }else if(userType.equals("M")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  order by ID asc";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  and RELETED_NAME like '%"+related+"%' order by ID asc";
            }
        }
        else if(userType.equals("U")){
            log.info("userType:"+documentReq.getUserType());
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"' a order by ID asc";
            }else {
                SQL="select * from V_DOCUMENT_FOR_ADMIN where  RELETED_NAME like '%"+related+"%' and DOC_TYPE ='"+documentReq.getDocType()+"'  order by ID asc";
            }
        }
        else {
            if(related.equals("") || related == null || related ==""){
                SQL="select * from V_DOCUMENT_FOR_ADMIN where DOC_TYPE ='"+documentReq.getDocType()+"'  order by ID asc";
            }else {
                log.info("userType:" + documentReq.getUserType());
                SQL = "select * from V_DOCUMENT_FOR_ADMIN where RELETED_NAME like '%"+related+"%' and DOC_TYPE ='" + documentReq.getDocType() + "'  order by ID asc";
            }
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
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' or SHARING_TYPE ='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ'  order by ID asc";
        }else if(documentReq.getStartDate() != null){
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' and  " +
                    "CREATED_DATE between '"+docDate+"' and '"+endDate+"' or SHARING_TYPE ='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
        }else {
            SQL="select * from V_REPORT_DOCUMENT where USER_ALLOW='"+documentReq.getMarkerId()+"' or SHARING_TYPE ='Normal-ອະນຸຍາດໃຫ້ທຸກຄົນເຫັນຂໍ້ມູນ' order by ID asc";
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
