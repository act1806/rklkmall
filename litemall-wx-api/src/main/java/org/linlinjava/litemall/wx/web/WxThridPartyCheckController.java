package org.linlinjava.litemall.wx.web;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.service.WxThirdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方检测保存
 */
@RestController
@RequestMapping("/wx/thirdCheck")
@Validated
public class WxThridPartyCheckController {
    private final Log logger = LogFactory.getLog(WxThridPartyCheckController.class);

    @Autowired
    private WxThirdCheckService wxThirdCheckService;

    /**
     * 提交送检单
     *
     * @param body   送检单信息
     * @return 提交送检单单操作结果
     */
    @PostMapping("submit")
    public Object submit(@RequestBody String body) {

        return wxThirdCheckService.submit(body);
    }
}
