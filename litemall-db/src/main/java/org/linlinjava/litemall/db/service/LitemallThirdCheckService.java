package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.db.dao.LitemallThirdCheckMapper;
import org.linlinjava.litemall.db.domain.LitemallThirdCheck;
import org.linlinjava.litemall.db.domain.LitemallThirdCheckExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallThirdCheckService {
    @Resource
    private LitemallThirdCheckMapper thirdCheckMMapper;

    public void add(LitemallThirdCheck thirdCheck) {
        thirdCheck.setCreateTime(LocalDateTime.now());
        thirdCheckMMapper.insert(thirdCheck);
    }

    public PageInfo<LitemallThirdCheck> querySelective(Integer page, Integer size) {
        LitemallThirdCheck example = new LitemallThirdCheck();



//        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
//            example.setOrderByClause(sort + " " + order);
//        }

        PageHelper.startPage(page, size);
        List<LitemallThirdCheck> ll = thirdCheckMMapper.selectByExample(example);
        PageInfo<LitemallThirdCheck> pp = new PageInfo<LitemallThirdCheck>(ll);
        return pp;
    }
}
