package com.ldb.iadoc.Dao.DashBoardDao;

import com.ldb.iadoc.Model.dashboard.dashboardReq;
import com.ldb.iadoc.Model.dashboard.dashboardResp;

import java.util.List;

public interface DashBoardDao {
    public dashboardResp.caltotal dashboard(dashboardReq dashboardReq);
    public List<dashboardResp.transaction> transaction(dashboardReq dashboardReq);
    public List<dashboardResp.section> section(dashboardReq dashboardReq);
    public List<dashboardResp.daily> daily(dashboardReq dashboardReq);
}
