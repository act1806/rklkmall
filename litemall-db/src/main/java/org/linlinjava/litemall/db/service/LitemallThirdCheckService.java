package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.linlinjava.litemall.db.dao.LitemallThirdCheckMapper;
import org.linlinjava.litemall.db.domain.LitemallOrder;
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

    public PageInfo<LitemallThirdCheck> querySelective(Integer page, Integer size, List<String> statusArray) {
        LitemallThirdCheck example = new LitemallThirdCheck();

        PageInfo<LitemallThirdCheck> pp = null;

        if(!StringUtils.isEmpty(statusArray)){
            PageHelper.startPage(page, size);
            List<LitemallThirdCheck> ll = thirdCheckMMapper.selectByStatusArray(statusArray);
            pp = new PageInfo<LitemallThirdCheck>(ll);
        } else {
            PageHelper.startPage(page, size);
            List<LitemallThirdCheck> ll = thirdCheckMMapper.selectByExample(example);
            pp = new PageInfo<LitemallThirdCheck>(ll);

        }

        return pp;
    }

    public int update(LitemallThirdCheck litemallThirdCheck){
        return thirdCheckMMapper.updateByPrimaryKeySelective(litemallThirdCheck);
    }

//    public List<LitemallOrder> querySelective(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
//        LitemallOrderExample example = new LitemallOrderExample();
//        LitemallOrderExample.Criteria criteria = example.createCriteria();
//
//        if (userId != null) {
//            criteria.andUserIdEqualTo(userId);
//        }
//        if (!StringUtils.isEmpty(orderSn)) {
//            criteria.andOrderSnEqualTo(orderSn);
//        }
//        if (orderStatusArray != null && orderStatusArray.size() != 0) {
//            criteria.andOrderStatusIn(orderStatusArray);
//        }
//        criteria.andDeletedEqualTo(false);
//
//        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
//            example.setOrderByClause(sort + " " + order);
//        }
//
//        PageHelper.startPage(page, limit);
//        return thirdCheckMMapper.selectByExample(example);
//    }


}
