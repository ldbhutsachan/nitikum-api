package com.ldb.iadoc.Service;

import com.ldb.iadoc.Dao.DashBoardDao.DashBoardDao;
import com.ldb.iadoc.Mesage.Message;
import com.ldb.iadoc.Model.dashboard.dashboardReq;
import com.ldb.iadoc.Model.dashboard.dashboardResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashBoardService {

    private final DashBoardDao dashBoardDao;
    public dashboardResp dashboard(dashboardReq dashboardReq) {
        Message message = new Message();
        dashboardResp resp = new dashboardResp();

        try {
            // ດຶງຂໍ້ມູນຈາກ DAO
            dashboardResp.caltotal caltotal = dashBoardDao.dashboard(dashboardReq);
            List<dashboardResp.transaction> transactions = dashBoardDao.transaction(dashboardReq);
            List<dashboardResp.section> sections = dashBoardDao.section(dashboardReq);
            List<dashboardResp.daily> daily = dashBoardDao.daily(dashboardReq);

            // ຕັ້ງຄ່າໃສ່ response
            resp.setCaltotalGroup(caltotal);
            resp.setTransactionsGroup(transactions);
            resp.setSectionGroup(sections);
            resp.setDailyGroup(daily);

            message.setResCode("00");
            message.setResMgs("success");
        } catch (Exception e) {
            log.error("Error building dashboard", e);
            message.setResCode("99");
            message.setResMgs("failed: " + e.getMessage());
        }

        resp.setMessage(message);
        return resp;
    }
}
