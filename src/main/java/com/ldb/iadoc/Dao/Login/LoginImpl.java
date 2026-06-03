package com.ldb.iadoc.Dao.Login;

import com.ldb.iadoc.Model.Branch.Branch;
import com.ldb.iadoc.Model.Branch.BranchReq;
import com.ldb.iadoc.Model.Branch.ComboBand.ComboBranch;
import com.ldb.iadoc.Model.Department.Dept;
import com.ldb.iadoc.Model.Department.DeptReq;
import com.ldb.iadoc.Model.Document.DocumentReq;
import com.ldb.iadoc.Model.Login.*;
import com.ldb.iadoc.Model.Login.LoginInfo.LoginChangPwd;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSection;
import com.ldb.iadoc.Model.Section.ComboSection.ComboSectionReq;
import com.ldb.iadoc.Model.Section.ExcusiveSection.ComboSectionExReq;
import com.ldb.iadoc.Model.Section.Section;
import com.ldb.iadoc.Model.Section.SectionReq;
import com.ldb.iadoc.Model.UserType.UserType;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUser;
import com.ldb.iadoc.Model.Users.ComboUser.ComboUserReq;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class LoginImpl implements LoginDao {
    @Autowired
    @Qualifier("IADOCJdbcTemplate")
    private JdbcTemplate IADOCJdbcTemplate;
    String SQL = "";

    @Override
    public List<Login> login(LoginReq loginReq) {
        SQL = "SELECT * FROM IADOC.V_LOGIN where USER_NAME='" + loginReq.getUserName() + "' and USER_PWD='" + loginReq.getPassWord() + "'";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Login>() {
            @Override
            public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
                Login tr = new Login();
                tr.setID(rs.getLong("ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setPassWord(rs.getString("USERCHAR"));
                tr.setToKen(rs.getString("token"));
                tr.setUserId(rs.getString("USER_ID"));
                tr.setGender(rs.getString("GENDER"));
                tr.setDob(rs.getString("DOB"));
                tr.setTypeDesc(rs.getString("TYPE_DESC"));
                tr.setTypeDesLa(rs.getString("TYPE_DESC_LAO"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescEn(rs.getString("SEC_DESC"));
                tr.setSecDescLa(rs.getString("SEC_DESC_LAO"));
                tr.setUserStatus(rs.getString("USER_STATUS"));
                tr.setFullNameEn(rs.getString("FULLNAME_EN"));
                tr.setFullNameLa(rs.getString("FULLNAME_LA"));
                return tr;
            }
        });
        //return -1;
    }

    @Override
    public List<Login> getShowUserInfo(LoginReq loginReq) {
        if (loginReq.getUserType().equals("A")) {
            SQL = "SELECT * FROM IADOC.V_LOGIN";
        } else {
            SQL = "SELECT * FROM IADOC.V_LOGIN where USER_NAME='" + loginReq.getUserName() + "'";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Login>() {
            @Override
            public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
                Login tr = new Login();
                tr.setID(rs.getLong("ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setPassWord(rs.getString("USER_PWD"));
                tr.setToKen(rs.getString("token"));
                tr.setUserId(rs.getString("USER_ID"));
                tr.setGender(rs.getString("GENDER"));
                tr.setDob(rs.getString("DOB"));
                tr.setTypeDesc(rs.getString("TYPE_DESC"));
                tr.setTypeDesLa(rs.getString("TYPE_DESC_LAO"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescEn(rs.getString("SEC_DESC"));
                tr.setSecDescLa(rs.getString("SEC_DESC_LAO"));
                tr.setUserStatus(rs.getString("USER_STATUS"));
                tr.setFullNameEn(rs.getString("FULLNAME_EN"));
                tr.setFullNameLa(rs.getString("FULLNAME_LA"));
                tr.setEmail(rs.getString("email"));
                tr.setTel(rs.getString("tel"));
                tr.setIdtype(rs.getString("idType"));
                tr.setBrCode(rs.getString("brCode"));
                tr.setBrName(rs.getString("brName"));
                return tr;
            }
        });
        //return -1;
    }

    @Override
    public List<ComboUser> getComboxUser(ComboUserReq loginReq) {
        String sec[] = loginReq.getSecCode();
        String ListUser = Arrays.toString(sec).replace("[", "('").replace(",", "','").replace("]", "')");
        String checkType = ListUser.replace("('", "").replace("')", "");

        if (checkType.equals("A")) {
            SQL = "SELECT * FROM V_COMBOUSER";
        } else {
            SQL = "SELECT * FROM V_COMBOUSER where SEC_CODE in " + ListUser + "";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboUser>() {
            @Override
            public ComboUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboUser tr = new ComboUser();
                tr.setUserName(rs.getString("FULLNAME_LA"));
                tr.setUserId(rs.getString("USER_ID"));
                return tr;
            }
        });
        //return -1;
    }

    @Override
    public List<UserType> getUserType() {
        SQL = "select * from usertype order by ID asc";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<UserType>() {
            @Override
            public UserType mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserType tr = new UserType();
                tr.setId(rs.getString("ID"));
                tr.setUserType(rs.getString("USER_TYPE"));
                tr.setTypeDesc(rs.getString("TYPE_DESC"));
                tr.setTypeDescLao(rs.getString("TYPE_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public List<Section> getSection(SectionReq sectionReq) {
        if (sectionReq.getSecCode() == "" || sectionReq.getSecCode() == null) {
            SQL = "select * from V_SECTIONINFO where type='1' order by SEC_CODE asc";
        } else {
            SQL = "select * from V_SECTIONINFO where type='1' and DEPT_CODE = '" + sectionReq.getSecCode() + "' order by SEC_CODE asc";

        }
        log.info("sql:"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Section>() {
            @Override
            public Section mapRow(ResultSet rs, int rowNum) throws SQLException {
                Section tr = new Section();
                tr.setSecId(rs.getString("ID"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDesc(rs.getString("SEC_DESC"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                tr.setDeptCode(rs.getString("DEPT_CODE"));
                tr.setDeptEN(rs.getString("DEPT_DESC"));
                tr.setDeptLa(rs.getString("DEPT_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }
    public List<Section> getSectionData(SectionReq sectionReq) {

        SQL = "select * from V_SECTIONINFO where type='1' and DEPT_CODE = '" + sectionReq.getBrCode() + "' order by SEC_CODE asc";
        log.info("sql:"+SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Section>() {
            @Override
            public Section mapRow(ResultSet rs, int rowNum) throws SQLException {
                Section tr = new Section();
                tr.setSecId(rs.getString("ID"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDesc(rs.getString("SEC_DESC"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                tr.setDeptCode(rs.getString("DEPT_CODE"));
                tr.setDeptEN(rs.getString("DEPT_DESC"));
                tr.setDeptLa(rs.getString("DEPT_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }
    public List<Section> getSectionsExcutive(SectionReq sectionReq) {
        if (sectionReq.getSecCode() == "" || sectionReq.getSecCode() == null) {
            SQL = "select * from V_SECTIONINFO where type='2' order by SEC_CODE asc";
        } else {
            SQL = "select * from V_SECTIONINFO where type='2'  and SEC_CODE='" + sectionReq.getSecCode() + "' order by SEC_CODE asc";

        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Section>() {
            @Override
            public Section mapRow(ResultSet rs, int rowNum) throws SQLException {
                Section tr = new Section();
                tr.setSecId(rs.getString("ID"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDesc(rs.getString("SEC_DESC"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                tr.setDeptCode(rs.getString("DEPT_CODE"));
                tr.setDeptEN(rs.getString("DEPT_DESC"));
                tr.setDeptLa(rs.getString("DEPT_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public List<ComboSection> getComboxSections(ComboSectionReq sectionReq) {
        String sec[] = sectionReq.getBranchCode();
        String ListUser = Arrays.toString(sec).replace("[", "('").replace(",", "','").replace("]", "')");
        String checkType = ListUser.replace("('", "").replace("')", "");

        if (checkType.equals("A")) {
            SQL = "select * from V_COMBOSECTION order by SEC_CODE asc";
        } else {

            SQL = "select * from V_COMBOSECTION where BRANCHCODE in " + ListUser + " order by SEC_CODE asc";
            System.out.println("s:" + SQL);
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboSection>() {
            @Override
            public ComboSection mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboSection tr = new ComboSection();
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public List<ComboSection> getComboxSectionsExcutive(ComboSectionExReq sectionReq) {
        if (sectionReq.getBranchCode().equals("A")) {
            SQL = "select * from V_COMBOSECTION_EXCUTIVE order by SEC_CODE asc";
        } else {
            SQL = "select * from V_COMBOSECTION_EXCUTIVE where BRANCHCODE ='" + sectionReq.getBranchCode() + "' order by SEC_CODE asc";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboSection>() {
            @Override
            public ComboSection mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboSection tr = new ComboSection();
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public List<ComboSection> getComboxDeptExcutive(ComboSectionExReq sectionReq) {
        SQL = "select * from V_COMBOSECTION_EXCUTIVE_TP_21 where SEC_CODE_IN ='" + sectionReq.getSecCode() + "' order by SEC_CODE asc";
        System.out.println("SQL:" + SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboSection>() {
            @Override
            public ComboSection mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboSection tr = new ComboSection();
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescLao(rs.getString("SEC_DESC_LAO"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public List<LoginChangPwd> checkOldPwd(LoginReq loginReq) {
        SQL = "select USER_PWD,USER_ID from USERS where USER_PWD='" + loginReq.getOldPassword() + "' and  USER_ID='" + loginReq.getOldUserId() + "'";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<LoginChangPwd>() {
            @Override
            public LoginChangPwd mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoginChangPwd tr = new LoginChangPwd();
                tr.setUserId(rs.getString("USER_ID"));
                tr.setOldPassWord(rs.getString("USER_PWD"));
                return tr;
            }
        });
        // return null;
    }

    @Override
    public int chagePassword(LoginReq signupReq) throws ParseException {
        SQL = "update USERS set USER_PWD=? where USER_ID=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                signupReq.getNewPwd(),
                signupReq.getOldUserId()
        });
    }

    @Override
    public int signup(SignupReq signupReq) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = sdf.parse(signupReq.getDob());
        java.sql.Date DobStartDate = new java.sql.Date(dob.getTime());
        try {
            SQL = "INSERT INTO USERS (USER_ID,USER_PWD,USER_NAME,GENDER,DOB,SEC_CODE,USER_TYPE,USER_STATUS," +
                    "TEL,EMAIL,LOGIN_STATUS,TOKEN,CREATED_DT,FULLNAME_EN,FULLNAME_LA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?) ";
            List<Object> paraList = new ArrayList<Object>();
            paraList.add(signupReq.getUserId());
            paraList.add(signupReq.getPassWord());
            paraList.add(signupReq.getUserName());
            paraList.add(signupReq.getGender());
            paraList.add(DobStartDate);
            paraList.add(signupReq.getSecCode());
            paraList.add(signupReq.getUserType());
            paraList.add(signupReq.getUserStatus());
            paraList.add(signupReq.getTel());
            paraList.add(signupReq.getEmail());
            paraList.add(signupReq.getLoginStatus());
            paraList.add(signupReq.getToKen());
            paraList.add(signupReq.getFullNameEn());
            paraList.add(signupReq.getFullNameLa());
            return IADOCJdbcTemplate.update(SQL, paraList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int UpdatesSignUp(SignupReq signupReq) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = sdf.parse(signupReq.getDob());
        java.sql.Date DobStartDate = new java.sql.Date(dob.getTime());
        try {
            SQL = "UPDATE USERS SET USER_ID=?,USER_PWD=?,USER_NAME=?,GENDER=?,DOB=?,SEC_CODE=?,USER_TYPE=?,USER_STATUS=?," +
                    "TEL=?,EMAIL=?,LOGIN_STATUS=?,FULLNAME_EN=?,FULLNAME_LA=? WHERE ID=?";
            List<Object> paraList = new ArrayList<Object>();
            paraList.add(signupReq.getUserId());
            paraList.add(signupReq.getPassWord());
            paraList.add(signupReq.getUserName());
            paraList.add(signupReq.getGender());
            paraList.add(DobStartDate);
            paraList.add(signupReq.getSecCode());
            paraList.add(signupReq.getUserType());
            paraList.add(signupReq.getUserStatus());
            paraList.add(signupReq.getTel());
            paraList.add(signupReq.getEmail());
            paraList.add(signupReq.getLoginStatus());
            paraList.add(signupReq.getFullNameEn());
            paraList.add(signupReq.getFullNameLa());
            paraList.add(signupReq.getId());
            return IADOCJdbcTemplate.update(SQL, paraList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int DelSignUp(SignupReq signupReq) throws ParseException {
        SQL = "delete from users where id='" + signupReq.getId() + "'";
        return IADOCJdbcTemplate.update(SQL);
    }

    @Override
    public int saveDept(DeptReq deptReq) {
        SQL = "insert into DEPTS (DEPT_DESC,DEPT_DESC_LAO,BRANCHCODE) values (?,?,?)";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                deptReq.getDeptDesc(),
                deptReq.getDeptLao(),
                deptReq.getBranchCode()
        });
    }

    @Override
    public int updateDept(DeptReq deptReq) {
        SQL = "update DEPTS set DEPT_DESC=?,DEPT_DESC_LAO=?,BRANCHCODE=? where DEPT_CODE=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                deptReq.getDeptDesc(),
                deptReq.getDeptLao(),
                deptReq.getBranchCode(),
                deptReq.getDeptId()
        });
    }

    @Override
    public int deleteDept(DeptReq deptReq) {
        SQL = "delete from  DEPTS  where DEPT_CODE=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                deptReq.getDeptId()
        });
    }

    @Override
    public List<Dept> getDeptList(DeptReq deptReq) {
        if (deptReq.getDeptId() == null || deptReq.getDeptId() == "") {
            SQL = "select * from V_DEPTINFO order by DEPT_CODE asc";
        } else {
            SQL = "select * from V_DEPTINFO where DEPT_CODE='" + deptReq.getDeptId() + "' order by DEPT_CODE asc";
        }
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Dept>() {
            @Override
            public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dept tr = new Dept();
                tr.setDeptId(rs.getString("DEPT_CODE"));
                tr.setDeptDesc(rs.getString("DEPT_DESC"));
                tr.setDeptLao(rs.getString("DEPT_DESC_LAO"));
                tr.setBranchCode(rs.getString("BRANCHCODE"));
                tr.setBranchNameEn("-");
                tr.setBranchNameLa(rs.getString("BRANCH_NAME_LAO"));
                return tr;
            }
        });

    }

    @Override
    public int saveBranch(BranchReq branchReq) {
        SQL = "insert into BRANCH (BRANCH_CODE,BRANCH_NAME_LAO,LOCATION,BRANCH_TYPE,type,status) values  (?,?,?,?,'1',?)";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                branchReq.getBranchCode(),
                // branchReq.getBrName(),
                branchReq.getBrNameLa(),
                branchReq.getLocation(),
                branchReq.getBrType(),
                branchReq.getStatus()

        });
    }

    @Override
    public int saveBranchExcutive(BranchReq branchReq) {
        SQL = "insert into BRANCH (BRANCH_CODE,LOCATION,BRANCH_TYPE,type) values  (?,?,?,'2')";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                branchReq.getBranchCode(),
                // branchReq.getBrName(),
                branchReq.getBrNameLa(),
                branchReq.getLocation(),
                branchReq.getBrType()
        });
    }

    @Override
    public int updateBranch(BranchReq branchReq) {
        SQL = "update BRANCH set BRANCH_CODE=?,BRANCH_NAME_LAO=?,LOCATION=?,BRANCH_TYPE=?,status=? where ID=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                branchReq.getBranchCode(),
                // branchReq.getBrName(),
                branchReq.getBrNameLa(),
                branchReq.getLocation(),
                branchReq.getBrType(),
                branchReq.getStatus(),
                branchReq.getID()
        });
    }

    @Override
    public int delBranch(BranchReq branchReq) {
        SQL = "delete from BRANCH where ID=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                branchReq.getID()
        });
    }

    @Override
    public List<Branch> getBranch(BranchReq branchReq) {
        if (branchReq.getBranchCode() == null || branchReq.getBranchCode() == "") {
            SQL = "select * from BRANCH where  type='1'  order by TYPE_ORDER asc";
            System.out.println("SQL:" + SQL);
        } else {
            SQL = "select * from BRANCH where BRANCH_CODE='" + branchReq.getBranchCode() + "' and  type='1' order by TYPE_ORDER asc";
            System.out.println("SQL:" + SQL);
        }
        return IADOCJdbcTemplate.query(SQL, (rs, rowNum) -> {
            Branch tr = new Branch();
            tr.setID(rs.getString("ID")); // Replace with rs.getString if ID is VARCHAR
            tr.setBranchCode(rs.getString("BRANCH_CODE"));
            tr.setBrName("-");
            tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
            tr.setLocation(rs.getString("LOCATION"));
            tr.setBrType(rs.getString("BRANCH_TYPE"));
            tr.setStatus(rs.getString("STATUS"));
            return tr;
        });
    }
    public List<Branch> getBranchAll( ) {
            SQL = "select * from BRANCH where  type='1'  order by TYPE_ORDER asc";
            System.out.println("SQL:" + SQL);
        return IADOCJdbcTemplate.query(SQL, (rs, rowNum) -> {
            Branch tr = new Branch();
            tr.setID(rs.getString("ID")); // Replace with rs.getString if ID is VARCHAR
            tr.setBranchCode(rs.getString("BRANCH_CODE"));
            tr.setBrName("-");
            tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
            tr.setLocation(rs.getString("LOCATION"));
            tr.setBrType(rs.getString("BRANCH_TYPE"));
            tr.setStatus(rs.getString("STATUS"));
            return tr;
        });
    }

    @Override
    public List<Branch> getBranchExcutive(BranchReq branchReq) {
        if (branchReq.getBranchCode() == null || branchReq.getBranchCode() == "") {
            SQL = "select * from BRANCH where type='2' order by ID asc";
            System.out.println("SQL:" + SQL);
        } else {
            SQL = "select * from BRANCH where type='2' and BRANCH_CODE='" + branchReq.getBranchCode() + "' order by ID asc";
            System.out.println("SQL:" + SQL);
        }

        return IADOCJdbcTemplate.query(SQL, new RowMapper<Branch>() {
            @Override
            public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
                Branch tr = new Branch();
                tr.setID(rs.getString("ID"));
                tr.setBranchCode(rs.getString("BRANCH_CODE"));
                tr.setBrName("-");
                tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
                tr.setLocation(rs.getString("LOCATION"));
                tr.setBrType(rs.getString("BRANCH_TYPE"));
                return tr;
            }
        });
    }

    //==============================combox branch======================================
    @Override
    public List<ComboBranch> getComboxBranch() {
        SQL = "select * from V_COMBOBRANCH order by ORDERBY asc";
        System.out.println("SQL:" + SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboBranch>() {
            @Override
            public ComboBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboBranch tr = new ComboBranch();
                tr.setBranchCode(rs.getString("BRANCH_CODE"));
                tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
                return tr;
            }
        });
    }


    @Override
    public List<ComboBranch> getComboxBranchStatus() {
        SQL = "select * from V_COMBOBRANCH where STATUS !='Disabled' order by ORDERBY asc";
        System.out.println("SQL:" + SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboBranch>() {
            @Override
            public ComboBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboBranch tr = new ComboBranch();
                tr.setBranchCode(rs.getString("BRANCH_CODE"));
                tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
                return tr;
            }
        });
    }

    @Override
    public List<ComboBranch> getComboxBranchExcutive() {
        SQL = "select  ORDERBY,BRANCH_CODE,BRANCH_NAME_LAO from BRANCH where type='2' order by BRANCH_CODE desc";
        System.out.println("SQL:" + SQL);
        return IADOCJdbcTemplate.query(SQL, new RowMapper<ComboBranch>() {
            @Override
            public ComboBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
                ComboBranch tr = new ComboBranch();
                tr.setBranchCode(rs.getString("BRANCH_CODE"));
                tr.setBrNameLa(rs.getString("BRANCH_NAME_LAO"));
                return tr;
            }
        });
    }

    //==============================combox branch======================================
//=======================================section service
    @Override
    public int saveSection(SectionReq sectionReq) {
        SQL = "insert into sections (SEC_CODE,SEC_DESC,SEC_DESC_LAO,DEPT_CODE,type) values (?,?,?,?,'1')";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                sectionReq.getSecCode(),
                sectionReq.getSecDesc(),
                sectionReq.getSecDescLao(),
                sectionReq.getDeptCode()
        });
    }

    public int saveSectionExcutive(SectionReq sectionReq) {
        String secCode = "90901";
        SQL = "insert into sections (SEC_CODE,SEC_DESC,SEC_DESC_LAO,DEPT_CODE,type) values (?,?,?,?,'2')";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                secCode,
                sectionReq.getSecDesc(),
                sectionReq.getSecDescLao(),
                sectionReq.getDeptCode()
        });
    }

    @Override
    public int updateSection(SectionReq sectionReq) {
        SQL = "update sections set SEC_CODE=?,SEC_DESC=?,SEC_DESC_LAO=?,DEPT_CODE=? where  id=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                sectionReq.getSecCode(),
                sectionReq.getSecDesc(),
                sectionReq.getSecDescLao(),
                sectionReq.getDeptCode(),
                sectionReq.getSecId()

        });
    }

    @Override
    public int delSection(SectionReq sectionReq) {
        SQL = "delete from sections where  id=?";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                sectionReq.getSecId()
        });
    }

    @Override
    public int saveLoginLog(List<Login> logReq) {
        login_log loginLog = new login_log();
        loginLog.setUserId(logReq.get(0).getUserName());
        loginLog.setFullName(logReq.get(0).getFullNameLa());
        loginLog.setTel(logReq.get(0).getTel());
        loginLog.setEmail(logReq.get(0).getEmail());
        loginLog.setSecCode(logReq.get(0).getSecCode());
        loginLog.setSecName(logReq.get(0).getSecDescLa());
        SQL = "insert into login_log(USER_ID,FULLNAME_LA,TEL,EMAIL,SEC_CODE,SEC_DESC_LAO,CREATEDATE,TYPE) values(?,?,?,?,?,?,sysdate,'login') ";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                loginLog.getUserId(),
                loginLog.getFullName(),
                loginLog.getTel(),
                loginLog.getEmail(),
                loginLog.getSecCode(),
                loginLog.getSecName()

        });
    }

    @Override
    public int saveDoLog(login_log logReq) {
        login_log loginLog = new login_log();
        loginLog.setUserId(logReq.getUserId());
        loginLog.setFullName(logReq.getFullName());
        loginLog.setTel(logReq.getTel());
        loginLog.setEmail(logReq.getEmail());
        loginLog.setSecCode(logReq.getSecCode());
        loginLog.setSecName(logReq.getSecName());
        loginLog.setDocNo(logReq.getDocNo());
        loginLog.setSubjectName(logReq.getSubjectName());
        SQL = "insert into login_log(USER_ID,FULLNAME_LA,TEL,EMAIL,SEC_CODE,SEC_DESC_LAO," +
                "CREATEDATE,TYPE,DOCNO,SUBJECT_NAME) values(?,?,?,?,?,?,sysdate,'doc',?,?) ";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                loginLog.getUserId(),
                loginLog.getFullName(),
                loginLog.getTel(),
                loginLog.getEmail(),
                loginLog.getSecCode(),
                loginLog.getSecName(),
                loginLog.getDocNo(),
                loginLog.getSubjectName()

        });
    }

    @Override
    public List<VWStatistic_login> getStatisticLogin(VWStatisticReq vwStatisticReq) {

        String userType = vwStatisticReq.getUserType();
        String userTypConSql = "";
        String orderBy = " order by AMT desc";

        if ("A".equals(userType)) {
            userTypConSql = " AND USER_ID is not null";
        } else {
            userTypConSql = " AND USER_ID is null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select * from V_STATISTIC_LOGIN where  1=1 ");
        sb.append(userTypConSql);
        sb.append(orderBy);
        String sql = sb.toString();
        return IADOCJdbcTemplate.query(sql, new RowMapper<VWStatistic_login>() {
            @Override
            public VWStatistic_login mapRow(ResultSet rs, int rowNum) throws SQLException {
                VWStatistic_login tr = new VWStatistic_login();
                tr.setUserId(rs.getString("USER_ID"));
                tr.setFullName(rs.getString("FULLNAME_LA"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecName(rs.getString("SEC_DESC_LAO"));
                tr.setAmt(rs.getString("AMT"));
               //tr.setDocNo(rs.getString("DOCNO"));
               // tr.setSubName(rs.getString("SUBJECT_NAME"));
                return tr;
            }
        });
    }

    @Override
    public List<VWStatistic_login> dologStatistic(VWStatisticReq vwStatisticReq) {
        String userType = vwStatisticReq.getUserType();
        String userTypConSql = "";
        String orderBy = " order by AMT desc";
        if ("A".equals(userType)) {
            userTypConSql = " AND USER_ID is not null";
        } else {
            userTypConSql = " AND USER_ID is null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select * from V_STATISTIC_LOGIN where type = 'doc' and  1=1 ");
        sb.append(userTypConSql);
        sb.append(orderBy);
        String sql = sb.toString();
        return IADOCJdbcTemplate.query(sql, new RowMapper<VWStatistic_login>() {
            @Override
            public VWStatistic_login mapRow(ResultSet rs, int rowNum) throws SQLException {
                VWStatistic_login tr = new VWStatistic_login();
                tr.setUserId(rs.getString("USER_ID"));
                tr.setFullName(rs.getString("FULLNAME_LA"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecName(rs.getString("SEC_DESC_LAO"));
                tr.setAmt(rs.getString("AMT"));
                return tr;
            }
        });
    }

    @Override
    public List<login_log> dologStatisticDetailsDoc(VWStatisticReq vwStatisticReq) {

        String userType = vwStatisticReq.getUserType();
        String userTypConSql = "";
        String orderBy = " order by a.ID desc";
        if ("A".equals(userType)) {
            userTypConSql = " AND a.USER_ID is not null";
        } else {
            userTypConSql = " AND a.USER_ID is null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select  a.ID, a.USER_ID, b.FULLNAME_LA, b.TEL, b.EMAIL, a.SEC_CODE, a.SEC_DESC_LAO,\n" +
                " to_char(a.CREATEDATE,'DD/MM/YYYY') CREATEDATE, a.TYPE,a.DOCNO,a.SUBJECT_NAME\n" +
                " from LOGIN_LOG a inner join USERS  b on a.USER_ID=b.USER_ID");
        sb.append("\n where  a.USER_ID='" + vwStatisticReq.getUserName() + "' and 1=1 ");

        sb.append(userTypConSql);
        sb.append(orderBy);
        String sql = sb.toString();
        return IADOCJdbcTemplate.query(sql, new RowMapper<login_log>() {
            @Override
            public login_log mapRow(ResultSet rs, int rowNum) throws SQLException {
                login_log tr = new login_log();
                tr.setUserId(rs.getString("USER_ID"));
                tr.setFullName(rs.getString("FULLNAME_LA"));
                tr.setTel(rs.getString("TEL"));
                tr.setEmail(rs.getString("EMAIL"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecName(rs.getString("SEC_DESC_LAO"));
                tr.setCreateDate(rs.getString("CREATEDATE"));
                return tr;
            }
        });
    }

    @Override
    public List<login_log> dologStatisticDetailsLogin(VWStatisticReq vwStatisticReq) {

        String userType = vwStatisticReq.getUserType();
        String userTypConSql = "";
        String orderBy = " order by a.ID desc";

        if ("A".equals(userType)) {
            userTypConSql = " AND a.USER_ID is not null";
        } else {
            userTypConSql = " AND a.USER_ID is null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select  a.ID, a.USER_ID, b.FULLNAME_LA, b.TEL, b.EMAIL, a.SEC_CODE, a.SEC_DESC_LAO,\n" +
                " to_char(a.CREATEDATE,'DD/MM/YYYY') CREATEDATE, a.TYPE,a.DOCNO,a.SUBJECT_NAME\n" +
                " from LOGIN_LOG a inner join USERS  b on a.USER_ID=b.USER_ID");
        sb.append("\n where  a.USER_ID='" + vwStatisticReq.getUserName() + "' and 1=1 ");

        sb.append(userTypConSql);
        sb.append("\n  and SUBJECT_NAME is not null ");
        sb.append(orderBy);
        String sql = sb.toString();
        log.info("sql:"+sql);
        return IADOCJdbcTemplate.query(sql, new RowMapper<login_log>() {
            @Override
            public login_log mapRow(ResultSet rs, int rowNum) throws SQLException {
                login_log tr = new login_log();
                tr.setUserId(rs.getString("USER_ID"));
                tr.setFullName(rs.getString("FULLNAME_LA"));
                tr.setTel(rs.getString("TEL"));
                tr.setEmail(rs.getString("EMAIL"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecName(rs.getString("SEC_DESC_LAO"));
                tr.setCreateDate(rs.getString("CREATEDATE"));

                tr.setDocNo(rs.getString("DOCNO"));
                tr.setSubjectName(rs.getString("SUBJECT_NAME"));
                return tr;
            }
        });
    }

    @Override
    public List<Login> CheckUser(DocumentReq documentReq) {
        SQL = "select * from IADOC.V_LOGIN where USER_ID= '" + documentReq.getMarkerId() + "'";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Login>() {
            @Override
            public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
                Login tr = new Login();
                tr.setID(rs.getLong("ID"));
                tr.setUserName(rs.getString("USER_NAME"));
                tr.setPassWord(rs.getString("USERCHAR"));
                tr.setToKen(rs.getString("token"));
                tr.setUserId(rs.getString("USER_ID"));
                tr.setGender(rs.getString("GENDER"));
                tr.setDob(rs.getString("DOB"));
                tr.setTypeDesc(rs.getString("TYPE_DESC"));
                tr.setTypeDesLa(rs.getString("TYPE_DESC_LAO"));
                tr.setSecCode(rs.getString("SEC_CODE"));
                tr.setSecDescEn(rs.getString("SEC_DESC"));
                tr.setSecDescLa(rs.getString("SEC_DESC_LAO"));
                tr.setUserStatus(rs.getString("USER_STATUS"));
                tr.setFullNameEn(rs.getString("FULLNAME_EN"));
                tr.setFullNameLa(rs.getString("FULLNAME_LA"));
                return tr;
            }
        });
        //  return null;
    }

    @Override
    public int saveLoginLogbyType(List<Login> logReq) {
        login_log loginLog = new login_log();
        loginLog.setUserId(logReq.get(0).getUserName());
        loginLog.setFullName(logReq.get(0).getFullNameLa());
        loginLog.setTel(logReq.get(0).getTel());
        loginLog.setEmail(logReq.get(0).getEmail());
        loginLog.setSecCode(logReq.get(0).getSecCode());
        loginLog.setSecName(logReq.get(0).getSecDescLa());

        SQL = "insert into login_log(USER_ID,FULLNAME_LA,TEL,EMAIL,SEC_CODE,SEC_DESC_LAO,CREATEDATE,TYPE,SUBJECT_NAME,NAME_DESC) values(?,?,?,?,?,?,sysdate,'login',?,?) ";
        return IADOCJdbcTemplate.update(SQL, new Object[]{
                loginLog.getUserId(),
                loginLog.getFullName(),
                loginLog.getTel(),
                loginLog.getEmail(),
                loginLog.getSecCode(),
                loginLog.getSecName(),
                loginLog.getSubjectName(),
                loginLog.getNameDesc(),

        });

    }
}
