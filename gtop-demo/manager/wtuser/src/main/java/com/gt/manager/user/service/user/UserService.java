package com.gt.manager.user.service.user;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.DataMessage;
import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.user.User;

import java.util.Map;

public interface UserService {
    public Long insert(ThirdUser thirdUser);

    public User queryById(Long userId);

    public void update(User user);

    public Map<String, Object> getUserAndDefaultAdd(Long userId);

}
