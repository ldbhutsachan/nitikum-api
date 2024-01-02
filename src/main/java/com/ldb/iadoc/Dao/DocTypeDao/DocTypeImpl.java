package com.ldb.iadoc.Dao.DocTypeDao;

import com.ldb.iadoc.Model.DocType.DocType;
import com.ldb.iadoc.Model.DocType.DocTypeReq;
import com.ldb.iadoc.Model.Qter.Qter;
import com.ldb.iadoc.Model.Years.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class DocTypeImpl implements DocTypeDao {
    @Autowired
    @Qualifier("IADOCJdbcTemplate")
    private JdbcTemplate IADOCJdbcTemplate;
    String SQL="";
    @Override
    public int SaveDocType(DocTypeReq docTypeReq) {
       SQL="insert into doc_type (DOC_DESC,DOC_DESC_LAO,TYPE) values (?,?,'1')";
       return IADOCJdbcTemplate.update(SQL,new Object[]{
              // docTypeReq.getDocType(),
               docTypeReq.getDocDesc(),
               docTypeReq.getDocDescLao()
       });
    }
    @Override
    public int UpdateDocType(DocTypeReq docTypeReq) {
        SQL="update  doc_type set DOC_DESC=?,DOC_DESC_LAO=? where DOC_TYPE=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                docTypeReq.getDocDesc(),
                docTypeReq.getDocDescLao(),
                docTypeReq.getDocType()
        });
    }

    @Override
    public int DeleteDocType(DocTypeReq docTypeReq) {
        SQL="delete from   doc_type  where DOC_TYPE=?";
        return IADOCJdbcTemplate.update(SQL,new Object[]{
                docTypeReq.getDocType()
        });
    }
    @Override
    public List<DocType> getDocumentType(DocTypeReq docTypeReq) {
        if(docTypeReq.getDocType() == null || docTypeReq.getDocType() == ""){
            SQL="select * from doc_type where type='1' order by DOC_TYPE asc";
        }else {
            SQL="select * from doc_type where  type='1' and DOC_TYPE='"+docTypeReq.getDocType()+"' order by DOC_TYPE asc";
        }
      return IADOCJdbcTemplate.query(SQL, new RowMapper<DocType>() {
          @Override
          public DocType mapRow(ResultSet rs, int rowNum) throws SQLException {
              DocType tr = new DocType();
              tr.setDocType(rs.getString("DOC_TYPE"));
              tr.setDocDesc(rs.getString("DOC_DESC"));
              tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
              return tr;
          }
      });
    }
    @Override
    public List<DocType> getDocumentTypeAll() {
            SQL=" select * from V_CHOOSE_DOCTYPEALL order by ORDERBY asc ";
      return IADOCJdbcTemplate.query(SQL, new RowMapper<DocType>() {
          @Override
          public DocType mapRow(ResultSet rs, int rowNum) throws SQLException {
              DocType tr = new DocType();
              tr.setDocType(rs.getString("DOC_TYPE"));
              tr.setDocDesc(rs.getString("DOC_DESC"));
              tr.setDocDescLao(rs.getString("DOC_DESC_LAO"));
              return tr;
          }
      });
    }
    @Override
    public List<Years> getComboYear() {
            SQL="select * from TB_YEAR order by id ASC";
      return IADOCJdbcTemplate.query(SQL, new RowMapper<Years>() {
          @Override
          public Years mapRow(ResultSet rs, int rowNum) throws SQLException {
              Years tr = new Years();
              tr.setID(rs.getString("id"));
              tr.setYearIn(rs.getString("YEARIN"));
              return tr;
          }
      });
    }
    @Override
    public List<Years> getChooseYearAll() {
            SQL="select * from V_CHOOSE_YEAR order by id ASC";
      return IADOCJdbcTemplate.query(SQL, new RowMapper<Years>() {
          @Override
          public Years mapRow(ResultSet rs, int rowNum) throws SQLException {
              Years tr = new Years();
              tr.setID(rs.getString("id"));
              tr.setYearIn(rs.getString("YEARIN"));
              return tr;
          }
      });
    }
    @Override
    public List<Qter> getQter() {
        SQL="select * from TB_QTER order by id ASC";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Qter>() {
            @Override
            public Qter mapRow(ResultSet rs, int rowNum) throws SQLException {
                Qter tr = new Qter();
                tr.setID(rs.getString("id"));
                tr.setQTer(rs.getString("QTER"));
                return tr;
            }
        });
    }
    @Override
    public List<Qter> getQterAll() {
        SQL="select * from V_CHOOSE_QTER order by id ASC";
        return IADOCJdbcTemplate.query(SQL, new RowMapper<Qter>() {
            @Override
            public Qter mapRow(ResultSet rs, int rowNum) throws SQLException {
                Qter tr = new Qter();
                tr.setID(rs.getString("id"));
                tr.setQTer(rs.getString("QTER"));
                return tr;
            }
        });
    }
}
