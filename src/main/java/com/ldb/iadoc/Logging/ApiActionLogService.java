package com.ldb.iadoc.Logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ApiActionLogService {
    private static final Logger log = LogManager.getLogger(ApiActionLogService.class);

    @Autowired
    @Qualifier("IADOCJdbcTemplate")
    private JdbcTemplate jdbc;

    public void logAction(String userKey, String type, String docNo, String subjectName, String nameDesc) {
        try {
            UserInfo userInfo = resolveUserInfo(userKey);

            String sql = "insert into login_log(" +
                    "USER_ID,FULLNAME_LA,TEL,EMAIL,SEC_CODE,SEC_DESC_LAO,CREATEDATE,TYPE,DOCNO,SUBJECT_NAME,NAME_DESC" +
                    ") values(?,?,?,?,?,?,sysdate,?,?,?,?)";

            jdbc.update(sql, new Object[]{
                    userInfo.userId,
                    userInfo.fullNameLa,
                    userInfo.tel,
                    userInfo.email,
                    userInfo.secCode,
                    userInfo.secDescLao,
                    type,
                    docNo,
                    subjectName,
                    nameDesc
            });
        } catch (Exception e) {
            // Never break API behavior because of log insert failures.
            log.warn("Failed to insert login_log. userKey={}, type={}, docNo={}, subjectName={}, nameDesc={}. Error={}",
                    userKey, type, docNo, subjectName, nameDesc, e.toString());
        }
    }

    private UserInfo resolveUserInfo(String userKey) {
        if (userKey == null || userKey.trim().isEmpty()) {
            return new UserInfo(null, null, null, null, null, null);
        }
        String key = userKey.trim();

        // Most APIs pass USER_ID as markerId; fallback to USER_NAME when needed.
        List<UserInfo> byUserId = queryUserInfo("select USER_NAME, FULLNAME_LA, TEL, EMAIL, SEC_CODE, SEC_DESC_LAO from IADOC.V_LOGIN where USER_ID = ?", key);
        if (!byUserId.isEmpty()) {
            return byUserId.get(0);
        }
        List<UserInfo> byUserName = queryUserInfo("select USER_NAME, FULLNAME_LA, TEL, EMAIL, SEC_CODE, SEC_DESC_LAO from IADOC.V_LOGIN where USER_NAME = ?", key);
        if (!byUserName.isEmpty()) {
            return byUserName.get(0);
        }

        // Unknown user: still write USER_ID so the action is traceable.
        return new UserInfo(key, null, null, null, null, null);
    }

    private List<UserInfo> queryUserInfo(String sql, String key) {
        return jdbc.query(sql, new Object[]{key}, new RowMapper<UserInfo>() {
            @Override
            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserInfo(
                        rs.getString("USER_NAME"),
                        rs.getString("FULLNAME_LA"),
                        rs.getString("TEL"),
                        rs.getString("EMAIL"),
                        rs.getString("SEC_CODE"),
                        rs.getString("SEC_DESC_LAO")
                );
            }
        });
    }

    private static final class UserInfo {
        final String userId;
        final String fullNameLa;
        final String tel;
        final String email;
        final String secCode;
        final String secDescLao;

        private UserInfo(String userId, String fullNameLa, String tel, String email, String secCode, String secDescLao) {
            this.userId = userId;
            this.fullNameLa = fullNameLa;
            this.tel = tel;
            this.email = email;
            this.secCode = secCode;
            this.secDescLao = secDescLao;
        }
    }
}

