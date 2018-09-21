package com.gt.manager.user.service.thirdUser;

import com.gt.manager.entity.thirdUser.ThirdUser;

import java.util.List;

public interface ThirdUserService {

    public ThirdUser queryByUserId(Long id);

    public ThirdUser queryByOpenId(String openId);

    public boolean queryIsExist(String openId);

    public List<ThirdUser> queryByCondition(ThirdUser thirdUser);
}
