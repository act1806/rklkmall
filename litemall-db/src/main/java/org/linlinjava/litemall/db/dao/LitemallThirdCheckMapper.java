package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.LitemallThirdCheck;
import org.linlinjava.litemall.db.domain.LitemallThirdCheckExample;

import java.util.List;

public interface LitemallThirdCheckMapper {

    int insert(LitemallThirdCheck record);

    List<LitemallThirdCheck> selectByExample(LitemallThirdCheck example);

    int updateByPrimaryKeySelective(LitemallThirdCheck record);

    List<LitemallThirdCheck> selectByStatusArray(List statusArray);
}
