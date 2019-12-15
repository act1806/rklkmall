package org.linlinjava.litemall.admin.dto;

import org.linlinjava.litemall.db.domain.*;

import java.util.List;

public class OrderDetail {
    LitemallOrder order;
    List<LitemallOrderGoods> orderGoods;

    public LitemallOrder getOrder() {
        return order;
    }

    public void setOrder(LitemallOrder order) {
        this.order = order;
    }

    public List<LitemallOrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<LitemallOrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }
}
