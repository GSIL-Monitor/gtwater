package com.gt.manager.user.service.thirdUser;

import com.gt.manager.entity.thirdUser.ThirdUser;
import com.gt.manager.user.repository.thirdUser.ThirdUserDAO;
import com.robert.vesta.service.intf.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("thirdUserService")
public class ThirdUserServiceImpl implements ThirdUserService {
    @Autowired
    private ThirdUserDAO thirdUserDAO;
    @Autowired
    private IdService idService;

    /**
     * 根据用户id查询用户微信信息
     * @param userId
     * @return
     */
    @Override
    public ThirdUser queryByUserId(Long userId) {
        ThirdUser query = new ThirdUser();
        query.setUserId(userId);
        List<ThirdUser> list = this.thirdUserDAO.selectList(query);
        if(null != list || !list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据openid获取用户微信信息
     * @param openId
     * @return
     */
    @Override
    public ThirdUser queryByOpenId(String openId) {
        ThirdUser query = new ThirdUser();
        query.setOpenId(openId);
        List<ThirdUser> list = this.thirdUserDAO.selectList(query);
        if(null != list && !list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据openid查询用户是否存在
     * @param openId
     * @return true:存在，false：不存在
     */
    @Override
    public boolean queryIsExist(String openId) {
        ThirdUser query = new ThirdUser();
        query.setOpenId(openId);
        List<ThirdUser> list = this.thirdUserDAO.selectList(query);
        if(null != list || !list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据条件查询
     * @param thirdUser
     * @return
     */
    @Override
    public List<ThirdUser> queryByCondition(ThirdUser thirdUser) {
        List<ThirdUser> thirdUsers = this.thirdUserDAO.selectList(thirdUser);
        return thirdUsers;
    }
}
