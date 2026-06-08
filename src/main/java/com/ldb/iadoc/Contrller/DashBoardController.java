package com.ldb.iadoc.Contrller;

import com.ldb.iadoc.Model.dashboard.dashboardReq;
import com.ldb.iadoc.Model.dashboard.dashboardResp;
import com.ldb.iadoc.Service.DashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("${base_url}")
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @CrossOrigin(origins = "*")
    @PostMapping("/dashboard")
    public dashboardResp dashboard(@RequestBody dashboardReq dashboardReq) {
        log.info("====================================================>dashboard controller<=========================");
        dashboardResp result =new dashboardResp();
        result = dashBoardService.dashboard(dashboardReq);
        return result;
    }
}
