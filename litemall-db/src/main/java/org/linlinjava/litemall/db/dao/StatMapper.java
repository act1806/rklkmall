package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatMapper {
    List<Map> statUser();

    List<Map> statOrder();

    List<Map> statGoods();

    List<Map> userSalesSummary(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("agentName") String agentName, @Param("sailer") String sailer);

    List<Map> userSalesDetail(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("agentName") String agentName, @Param("sailer") String sailer);

    List<Map> goodsSalesSummary(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("goodsName") String goodsName);

    List<Map> goodsSalesDetail(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("goodsName") String goodsName);

    List<Map> sailerSalesDetail(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime, @Param("sailer") String sailer);

    List<String> unorderedUser(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}