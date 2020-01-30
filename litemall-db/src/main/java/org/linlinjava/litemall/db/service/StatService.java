package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallOrderMapper;
import org.linlinjava.litemall.db.dao.StatMapper;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatService {
    @Resource
    private StatMapper statMapper;

    @Resource
    private LitemallOrderMapper litemallOrderMapper;

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

    public List<Map> goodsSalesSummary(LocalDateTime beginTime, LocalDateTime endTime, String goodsName) {
        return statMapper.goodsSalesSummary(beginTime, endTime, goodsName);
    }

    public List<Map> goodsSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String goodsName) {
        return statMapper.goodsSalesDetail(beginTime, endTime, goodsName);
    }

    public List<Map> sailerSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String sailer) {
        return statMapper.sailerSalesDetail(beginTime, endTime, sailer);
    }

    public List<LitemallOrder> unorderedUser(LocalDateTime beginTime, LocalDateTime endTime) {
        List<String> userIds = statMapper.unorderedUser(beginTime, endTime);
        List<LitemallOrder> result = new ArrayList<>();
        for(String userId : userIds){
            LitemallOrder order = litemallOrderMapper.latestOrder(userId);
            result.add(order);
        }
        return result;
    }

}
