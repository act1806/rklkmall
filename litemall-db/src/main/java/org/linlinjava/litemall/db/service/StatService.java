package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.StatMapper;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatService {
    @Resource
    private StatMapper statMapper;

    public List<Map> statUser() {
        return statMapper.statUser();
    }

    public List<Map> statOrder() {
        return statMapper.statOrder();
    }

    public List<Map> statGoods() {
        return statMapper.statGoods();
    }

    public List<Map> userSalesSummary(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer) {
        return statMapper.userSalesSummary(beginTime, endTime, userName, sailer);
    }

    public List<Map> userSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer) {
        return statMapper.userSalesDetail(beginTime, endTime, userName, sailer);
    }

    public List<Map> goodsSalesSummary(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer) {
        return statMapper.goodsSalesSummary(beginTime, endTime, userName, sailer);
    }

    public List<Map> goodsSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer) {
        return statMapper.goodsSalesDetail(beginTime, endTime, userName, sailer);
    }

    public List<Map> sailerSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer) {
        return statMapper.sailerSalesDetail(beginTime, endTime, userName, sailer);
    }

    public List<Map> unorderedUser(LocalDateTime beginTime, LocalDateTime endTime) {
        return statMapper.unorderedUser(beginTime, endTime);
    }

}
