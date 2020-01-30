package org.linlinjava.litemall.db.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatMapper {
    List<Map> statUser();

    List<Map> statOrder();

    List<Map> statGoods();

    List<Map> userSalesSummary(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer);

    List<Map> userSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer);

    List<Map> goodsSalesSummary(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer);

    List<Map> goodsSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer);

    List<Map> sailerSalesDetail(LocalDateTime beginTime, LocalDateTime endTime, String userName, String sailer);

    List<Map> unorderedUser(LocalDateTime beginTime, LocalDateTime endTime);
}