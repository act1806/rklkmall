package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminOrderService;
import org.linlinjava.litemall.admin.vo.StatVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.db.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/stat")
@Validated
public class AdminStatController {
    private final Log logger = LogFactory.getLog(AdminStatController.class);

    @Autowired
    private StatService statService;

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private AdminOrderService adminOrderService;

    @RequiresPermissions("admin:stat:user")
    @RequiresPermissionsDesc(menu = {"统计管理", "用户统计"}, button = "查询")
    @GetMapping("/user")
    public Object statUser() {
        List<Map> rows = statService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("admin:stat:order")
    @RequiresPermissionsDesc(menu = {"统计管理", "订单统计"}, button = "查询")
    @GetMapping("/order")
    public Object statOrder() {
        List<Map> rows = statService.statOrder();
        String[] columns = new String[]{"day", "orders", "customers", "amount", "pcr"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);

        return ResponseUtil.ok(statVo);
    }

    @RequiresPermissions("admin:stat:goods")
    @RequiresPermissionsDesc(menu = {"统计管理", "商品统计"}, button = "查询")
    @GetMapping("/goods")
    public Object statGoods() {
        List<Map> rows = statService.statGoods();
        String[] columns = new String[]{"day", "orders", "products", "amount"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(rows);
        return ResponseUtil.ok(statVo);
    }

    /**
     * 获取全部可下单用户
     *
     * @return 订单操作结果
     */
    @GetMapping("/allUser")
    public Object user() {
        List<LitemallUser> userList = userService.querySelective("", "", 0, 100, "", "");
        return ResponseUtil.okList(userList);
    }

    /**
     * 取全部销售员
     *
     * @return 订单操作结果
     */
    @GetMapping("/allSailer")
    public Object sailer() {
        List<String> sailerList = userService.querySailer();
        return ResponseUtil.okList(sailerList);
    }

    /**
     * 客户销售汇总表
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/userSalesSummary")
    public Object userSalesSummary(@RequestParam(required = false) String userName,
                       @RequestParam(required = false) String sailer,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.userSalesSummary(beginTime, endTime, userName, sailer);
        return ResponseUtil.okList(rows);
    }

    /**
     * 客户销售明细表
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/userSalesDetail")
    public Object userSalesDetail(@RequestParam(required = false) String userName,
                       @RequestParam(required = false) String sailer,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.userSalesDetail(beginTime, endTime, userName, sailer);
        return ResponseUtil.okList(rows);
    }

    /**
     * 商品销售汇总表
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/goodsSalesSummary")
    public Object goodsSalesSummary(@RequestParam(required = false) String userName,
                                  @RequestParam(required = false) String sailer,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                  @Sort @RequestParam(defaultValue = "add_time") String sort,
                                  @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.goodsSalesSummary(beginTime, endTime, userName, sailer);
        return ResponseUtil.okList(rows);
    }

    /**
     * 商品销售明细表
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/goodsSalesDetail")
    public Object goodsSalesDetail(@RequestParam(required = false) String userName,
                                  @RequestParam(required = false) String sailer,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                  @Sort @RequestParam(defaultValue = "add_time") String sort,
                                  @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.goodsSalesDetail(beginTime, endTime, userName, sailer);
        return ResponseUtil.okList(rows);
    }

    /**
     * 业务员销售明细表
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/sailerSalesDetail")
    public Object sailerSalesDetail(@RequestParam(required = false) String userName,
                                  @RequestParam(required = false) String sailer,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                  @Sort @RequestParam(defaultValue = "add_time") String sort,
                                  @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.sailerSalesDetail(beginTime, endTime, userName, sailer);
        return ResponseUtil.okList(rows);
    }

    /**
     * 未下单客户统计
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/unorderedUser")
    public Object unorderedUser(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                  @Sort @RequestParam(defaultValue = "add_time") String sort,
                                  @Order @RequestParam(defaultValue = "desc") String order) {
        List<Map> rows = statService.unorderedUser(beginTime, endTime);
        return ResponseUtil.okList(rows);
    }

}
