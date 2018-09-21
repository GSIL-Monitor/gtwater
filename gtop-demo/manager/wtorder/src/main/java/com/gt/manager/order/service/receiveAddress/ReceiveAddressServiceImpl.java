package com.gt.manager.order.service.receiveAddress;

import com.github.pagehelper.PageHelper;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.order.repository.receiveAddress.ReceiveAddressDAO;
import com.gt.manager.util.selectAdress.util.entity.GeoCode;
import com.gt.manager.util.selectAdress.util.geocode.GeoCoder;
import com.robert.vesta.service.intf.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("receiveAddressService")
public class ReceiveAddressServiceImpl implements ReceiveAddressService {
    private static Logger logger = LoggerFactory.getLogger(ReceiveAddressServiceImpl.class);

    @Autowired
    private ReceiveAddressDAO receiveAddressDAO;
    @Autowired
    private IdService idService;
    @Override
    public Long add(ReceiveAddress receiveAddress) {
        this.receiveAddressDAO.insert(receiveAddress);
        return receiveAddress.getId();
    }

    @Override
    public void delete(Long id) {
        ReceiveAddress del = new ReceiveAddress();
        del.setId(id);
        del.setIsDelete(1);//1删除 2：未删除
        this.receiveAddressDAO.update(del);
    }

    @Override
    public void edit(ReceiveAddress receiveAddress) {
        //如果当前添加的地址是默认地址，则修改用户其他地址为非默认地址
        if(receiveAddress.getIsDefault() == 1){//是默认地址
            ReceiveAddress query = new ReceiveAddress();
            query.setUserid(receiveAddress.getUserid());
            query.setIsDefault(1);
            List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
            if(null != list && !list.isEmpty()){
                list.forEach(r ->{
                    ReceiveAddress edit = new ReceiveAddress();
                    edit.setId(r.getId());
                    edit.setIsDefault(2);
                    this.receiveAddressDAO.update(edit);
                });
            }
        }
        this.receiveAddressDAO.update(receiveAddress);
    }

    @Override
    public ReceiveAddress queryById(Long id) {
        return this.receiveAddressDAO.selectById(id);
    }

    @Override
    public List<ReceiveAddress> queryList(ReceiveAddress receiveAddress) {
        return this.receiveAddressDAO.selectList(receiveAddress);
    }

    /**
     * 设置默认地址
     * @param id
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void updateDefault(Long id) {
        ReceiveAddress res = this.receiveAddressDAO.selectById(id);
        ReceiveAddress query = new ReceiveAddress();
        query.setIsDefault(1);
        query.setIsDelete(2);
        query.setUserid(res.getUserid());
        List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
        if(null != list && !list.isEmpty()){
            list.stream().forEach(add ->{
                ReceiveAddress edit = new ReceiveAddress();
                edit.setIsDefault(2);
                edit.setId(add.getId());
                this.receiveAddressDAO.update(edit);
            });
        }
        ReceiveAddress edit = new ReceiveAddress();
        edit.setIsDefault(1);
        edit.setId(id);
        this.receiveAddressDAO.update(edit);
    }

    /**
     * 查询我的地址列表
     * @param userId
     * @param pageSize
     * @param pageNo
     * @return
     */
    @Override
    public List<ReceiveAddress> queryMyList(Long userId, int pageSize, int pageNo) {
//        pageNo = pageNo<1?1:++pageNo;
        PageHelper.startPage(pageNo, pageSize,"id desc");
        ReceiveAddress query = new ReceiveAddress();
        query.setUserid(userId);
        query.setIsDelete(2);
        List<ReceiveAddress> addressList = this.queryList(query);
        return addressList;
    }
}
