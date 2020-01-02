package org.linlinjava.litemall.admin.web;

import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminThirdCheckService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallThirdCheck;
import org.linlinjava.litemall.db.service.LitemallThirdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/thirdcheck")
@Validated
public class AdminThirdCheckController {
    private final Log logger = LogFactory.getLog(AdminUserController.class);

    @Autowired
    private LitemallThirdCheckService thirdCheckService;
    @Autowired
    private AdminThirdCheckService adminThirdCheckService;

//    @RequiresPermissions("admin:thirdcheck:list")
//    @RequiresPermissionsDesc(menu = {"用户管理", "会员管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(required = false) List<String> statusArray) {
        PageInfo userList = thirdCheckService.querySelective(page, limit, statusArray);
       // return userList;
        return ResponseUtil.okPageInfo(userList);
    }

    /**
     * 第三方检测的状态修改
     *
     * @param
     * @return 第三方检测操作结果
     */
    @PostMapping("/operation")
    public Object operation(@RequestBody LitemallThirdCheck litemallThirdCheck) {

            return adminThirdCheckService.operation(litemallThirdCheck);
    }
}
