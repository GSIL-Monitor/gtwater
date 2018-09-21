package com.gt.manager.user.service.user;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.entity.user.User;
import com.gt.manager.user.repository.receiveAddress.ReceiveAddressDAO;
import com.gt.manager.user.repository.thirdUser.ThirdUserDAO;
import com.gt.manager.user.repository.user.UserDAO;
import com.gt.manager.user.service.receiveAddress.ReceiveAddressService;
import com.robert.vesta.service.intf.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ThirdUserDAO thirdUserDAO;
    @Autowired
    private IdService idService;
    @Autowired
    private ReceiveAddressDAO receiveAddressDAO;

    /**
     * 注意处理用户未关注问题
     * 未关注的用户先用openId注册，关注后在补全用户信息
     * @param thirdUser
     * @return
     */
    @Override
//    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public Long insert(ThirdUser thirdUser) {
        if(!StringUtils.isEmpty(thirdUser.getNickname())){
            thirdUser.setNickname(filter(thirdUser.getNickname()));
        }
        ThirdUser query = new ThirdUser();
        query.setOpenId(thirdUser.getOpenId());
        List<ThirdUser> thirdUserList = this.thirdUserDAO.selectList(query);
        if(null == thirdUserList || thirdUserList.isEmpty()){
            //添加用户信息
            User u = new User();
            u.setId(idService.genId());
            u.setCreateTime(new Date());
            u.setLoginTime(new Date());
            u.setLoginSource(1);
            u.setNickname(thirdUser.getNickname());
            u.setIcon(thirdUser.getIcon());
            u.setVoicePromptSwitch("0");
            this.userDAO.insert(u);
            //添加用户微信信息
            thirdUser.setId(idService.genId());
            thirdUser.setThirdName("WeChat");
            thirdUser.setThirdType(1);
            thirdUser.setUserId(u.getId());
            thirdUser.setCreateTime(new Date());
            thirdUser.setUpdateTime(new Date());
            thirdUser.setAppCode("2");
            thirdUser.setAppName("gtwater");
            this.thirdUserDAO.insert(thirdUser);
            return u.getId();
        }else{
            if("".equals(thirdUserList.get(0).getNickname()) || null == thirdUserList.get(0).getNickname()){
                //给之前未关注注册的用户补全昵称和头像信息
                ThirdUser t_edit = new ThirdUser();
                t_edit.setId(thirdUserList.get(0).getId());
                t_edit.setIcon(thirdUser.getIcon());
                t_edit.setNickname(thirdUser.getNickname());
                this.thirdUserDAO.update(t_edit);
                User u_edit = new User();
                u_edit.setId(thirdUserList.get(0).getUserId());
                u_edit.setNickname(thirdUser.getNickname());
                u_edit.setIcon(thirdUser.getIcon());
                this.userDAO.update(u_edit);
            }
            return thirdUserList.get(0).getUserId();
        }
    }

    @Override
    public User queryById(Long userId) {
        return this.userDAO.selectById(userId);
    }

    @Override
    public void update(User user) {
        this.userDAO.update(user);
    }

    @Override
    public Map<String, Object> getUserAndDefaultAdd(Long userId) {
        User u = this.userDAO.selectById(userId);
        ReceiveAddress query = new ReceiveAddress();
        query.setIsDelete(2);
//        query.setAddressType(3);
        query.setIsDefault(1);
        query.setUserid(userId);
        List<ReceiveAddress> defadd = this.receiveAddressDAO.selectList(query);
        Map<String, Object> res = new HashMap<>();
        res.put("userInfo", u);
        if(null != defadd && !defadd.isEmpty()){
            res.put("defAddress", defadd.get(0));
        }else{
            res.put("defAddress", null);
        }
        return res;
    }

    public static String filter(String str) {
        if(str.trim().isEmpty()){
            return str;
        }
        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr="";
        Pattern emoji=Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;
    }


}
