package org.linlinjava.litemall.db.util;

import org.linlinjava.litemall.db.domain.LitemallOrder;

import java.util.ArrayList;
import java.util.List;

/*
 * 订单流程：下单成功－》支付订单－》发货－》收货
 * 订单状态：
 * 101 订单生成，未支付；102，下单未支付用户取消；103，下单未支付超期系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，用户申请退款；203，管理员执行退款操作，确认退款成功；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货，订单结束； 402 用户没有确认收货，但是快递反馈已收货后，超过一定时间，系统自动确认收货，订单结束。
 *
 * 当101用户未付款时，此时用户可以进行的操作是取消或者付款
 * 当201支付完成而商家未发货时，此时用户可以退款
 * 当301商家已发货时，此时用户可以有确认收货
 * 当401用户确认收货以后，此时用户可以进行的操作是退货、删除、去评价或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除、去评价、或者再次购买
 */
public class OrderUtil {

    public static final Short STATUS_CREATE = 101;
    public static final Short STATUS_PAY = 201;
    public static final Short STATUS_SHIP = 302;
    public static final Short STATUS_CONFIRM = 401;
    public static final Short STATUS_AUTO_CONFIRM = 402;
    public static final Short STATUS_CANCEL = 102;

    public static String orderStatusText(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();

        if (status == 101) {
            return "待销售经理确认";
        }

        if (status == 201) {
            return "待市场部确认";
        }

        if (status == 301) {
            return "待发货";
        }

        if (status == 302) {
            return "已发货";
        }

        if (status == 401) {
            return "已收货";
        }

        if (status == 402) {
            return "已收货(系统)";
        }

        throw new IllegalStateException("orderStatus不支持");
    }

    public static Short orderStatusNext(Short orderStatus) {
        int status = orderStatus.intValue();

        if (status == 101) {
            return 201;
        }

        if (status == 201) {
            return 301;
        }

        if (status == 301) {
            return 302;
        }

        if (status == 302) {
            return 401;
        }

        throw new IllegalStateException("orderStatus不支持");
    }

    public static OrderHandleOption build(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();
        OrderHandleOption handleOption = new OrderHandleOption();

        if (status == 101) {
            // 如果订单没有被取消，且没有支付，则可支付，可取消
            handleOption.setCancel(true);
        } else if (status == 201 || status == 301) {
            // 如果订单已经确认，则不能取消
            handleOption.setCancel(false);
        } else if (status == 302) {
            // 如果订单已经发货，没有收货，则可收货操作,
            // 此时不能取消订单
            handleOption.setConfirm(true);
        } else if (status == 401 || status == 402) {
            // 如果订单已经支付，且已经收货，则可删除、去评论和再次购买
            handleOption.setDelete(true);
            handleOption.setComment(true);
            handleOption.setRebuy(true);
        } else {
            throw new IllegalStateException("status不支持");
        }

        return handleOption;
    }

    public static List<Short> orderStatus(Integer showType) {
        // 全部订单
        if (showType == 0) {
            return null;
        }

        List<Short> status = new ArrayList<Short>(2);

        if (showType.equals(1)) {
            // 待确认订单
            status.add((short) 101);
            status.add((short) 201);
        } else if (showType.equals(2)) {
            // 待发货订单
            status.add((short) 301);
        } else if (showType.equals(3)) {
            // 待收货订单
            status.add((short) 302);
        } else if (showType.equals(4)) {
            // 待评价订单
            status.add((short) 401);
            status.add((short) 402);
        } else {
            return null;
        }

        return status;
    }


    public static boolean isCreateStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CREATE == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isPayStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_PAY == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isShipStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_SHIP == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isConfirmStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CONFIRM == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isAutoConfirmStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_AUTO_CONFIRM == litemallOrder.getOrderStatus().shortValue();
    }
}
