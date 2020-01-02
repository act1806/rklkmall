package org.linlinjava.litemall.admin.service;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallThirdCheck;
import org.linlinjava.litemall.db.service.LitemallThirdCheckService;
import org.linlinjava.litemall.db.util.ThirdCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service

public class AdminThirdCheckService {
    @Autowired
    LitemallThirdCheckService checkService;
    @Autowired
    private LogHelper logHelper;

    public Object operation(LitemallThirdCheck litemallThirdCheck) {
        String status = litemallThirdCheck.getStatus();
        litemallThirdCheck.setStatus(ThirdCheckUtil.statusNext(status));
        litemallThirdCheck.setUpdateTime(LocalDateTime.now());
        checkService.update(litemallThirdCheck);

        logHelper.logOrderSucceed(ThirdCheckUtil.statusText(litemallThirdCheck), "订单编号 " + litemallThirdCheck.getId());
        return ResponseUtil.ok();
    }
}
