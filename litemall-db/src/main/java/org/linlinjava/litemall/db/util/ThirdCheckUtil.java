package org.linlinjava.litemall.db.util;

import org.linlinjava.litemall.db.domain.LitemallThirdCheck;

import java.util.ArrayList;
import java.util.List;

public class ThirdCheckUtil {

    public static final String STATUS_CREATE = "0";
    public static final String STATUS_PAY = "1";
    public static final String STATUS_SHIP = "2";
    public static final String STATUS_CONFIRM = "3";

    public static String statusText(LitemallThirdCheck check) {
        String status = check.getStatus();

        if ("0".equals(status)) {
            return "已收到样本";
        }

        if ("1".equals(status)) {
            return "样本检测中，，";
        }

        if ("2".equals(status)) {
            return "样本检测完毕";
        }

        if ("3".equals(status)) {
            return "检测结果已发送至预留邮箱";
        }


        throw new IllegalStateException("status不支持");
    }

    /**
     * 得到下一状态
     * @param status
     * @return
     */
    public static String statusNext(String status) {
        if ("0".equals(status)) {
            return "1";
        }

        if ("1".equals(status)) {
            return "2";
        }

        if ("2".equals(status)) {
            return "3";
        }

        throw new IllegalStateException("status不支持");
    }
}
