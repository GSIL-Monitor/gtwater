package com.gt.manager.user.service.receiveAddress;

import com.github.pagehelper.PageHelper;
import com.gt.manager.entity.receiveAddress.ReceiveAddress;
import com.gt.manager.entity.sysRegion.SysRegion;
import com.gt.manager.user.constants.NameSpaceConstant;
import com.gt.manager.user.repository.receiveAddress.ReceiveAddressDAO;
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

        try {
            if(receiveAddress.getProvinceId() == null || receiveAddress.getProvinceId().longValue() == 0L ){
                //补全省市区id
                if(NameSpaceConstant.regionList != null && !NameSpaceConstant.regionList.isEmpty()){
                    for(SysRegion item : NameSpaceConstant.regionList){
                        if(item.getRegionName().equals(receiveAddress.getProvinceName()) && item.getRegionId().length() == 4){
                            receiveAddress.setProvinceId(Long.parseLong(item.getRegionId()));
                        }
                        if(item.getRegionName().equals(receiveAddress.getCityName()) && item.getRegionId().length() == 6){
                            receiveAddress.setCityId(Long.parseLong(item.getRegionId()));
                        }
                        if(item.getRegionName().equals(receiveAddress.getDistrictName()) && item.getRegionId().length() == 8){
                            receiveAddress.setDistrictId(Long.parseLong(item.getRegionId()));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("添加地址根据名称获取地址id异常", e);
        }

        //如果当前添加的地址是默认地址，则修改用户其他地址为非默认地址
        if(receiveAddress.getIsDefault() == 1){//是默认地址
            ReceiveAddress query = new ReceiveAddress();
            query.setUserid(receiveAddress.getUserid());
            query.setIsDefault(1);
            List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
            if(null != list && !list.isEmpty()){
                for(ReceiveAddress r : list){
                    ReceiveAddress edit = new ReceiveAddress();
                    edit.setId(r.getId());
                    edit.setIsDefault(2);
                    this.receiveAddressDAO.update(edit);
                }
            }
        }
        receiveAddress.setId(idService.genId());
        receiveAddress.setIsDelete(2);
        receiveAddress.setAddressType(3);
        receiveAddress.setCreateTime(new Date());
        try {
            GeoCode gc = GeoCoder.geocode(receiveAddress.getCityName()+receiveAddress.getDistrictName()+receiveAddress.getAddress(), receiveAddress.getProvinceName());
            receiveAddress.setLatitude(gc.getLat());//纬度
            receiveAddress.setLongitude(gc.getLng());//经度
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加地址获取经纬度失败=", e);
        }
        this.receiveAddressDAO.insert(receiveAddress);
        return receiveAddress.getId();
    }

    @Override
    public Long addNew(ReceiveAddress receiveAddress){
        List<SysRegion> sysRegionList =  NameSpaceConstant.regionList;
        if(sysRegionList != null && !sysRegionList.isEmpty()){
            for(SysRegion s : sysRegionList){
                if(s.getRegionId().length() == 6 && s.getRegionName().equals(receiveAddress.getCityName())){
                    receiveAddress.setCityId(Long.parseLong(s.getRegionId()));
                    receiveAddress.setProvinceId(Long.parseLong(s.getRegionId().substring(0 , 4)));
                    break;
                }
            }
            for(SysRegion s : sysRegionList){
                if(s.getRegionId().equals(receiveAddress.getProvinceId()+"")){
                    receiveAddress.setProvinceName(s.getRegionName());
                    break;
                }
            }
        }
        //如果当前添加的地址是默认地址，则修改用户其他地址为非默认地址
        if(receiveAddress.getIsDefault() == 1){//是默认地址
            ReceiveAddress query = new ReceiveAddress();
            query.setUserid(receiveAddress.getUserid());
            query.setIsDefault(1);
            List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
            if(null != list && !list.isEmpty()){
                for(ReceiveAddress r : list){
                    ReceiveAddress edit = new ReceiveAddress();
                    edit.setId(r.getId());
                    edit.setIsDefault(2);
                    this.receiveAddressDAO.update(edit);
                }
            }
        }
        receiveAddress.setId(idService.genId());
        receiveAddress.setIsDelete(2);
        receiveAddress.setAddressType(3);
        receiveAddress.setCreateTime(new Date());
        try {
            GeoCode gc = GeoCoder.geocode(receiveAddress.getCityName()+receiveAddress.getDistrictName()+receiveAddress.getAddress(), receiveAddress.getProvinceName());
            receiveAddress.setLatitude(gc.getLat());//纬度
            receiveAddress.setLongitude(gc.getLng());//经度
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加地址获取经纬度失败=", e);
        }
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

        try {
            if(receiveAddress.getProvinceId() == null || receiveAddress.getProvinceId().longValue() == 0L ){
                //补全省市区id
                if(NameSpaceConstant.regionList != null && !NameSpaceConstant.regionList.isEmpty()){
                    for(SysRegion item : NameSpaceConstant.regionList){
                        if(item.getRegionName().equals(receiveAddress.getProvinceName()) && item.getRegionId().length() == 4){
                            receiveAddress.setProvinceId(Long.parseLong(item.getRegionId()));
                        }
                        if(item.getRegionName().equals(receiveAddress.getCityName()) && item.getRegionId().length() == 6){
                            receiveAddress.setCityId(Long.parseLong(item.getRegionId()));
                        }
                        if(item.getRegionName().equals(receiveAddress.getDistrictName()) && item.getRegionId().length() == 8){
                            receiveAddress.setDistrictId(Long.parseLong(item.getRegionId()));
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("添加地址根据名称获取地址id异常", e);
        }

        //如果当前修改的地址是默认地址，则修改用户其他地址为非默认地址
        if(receiveAddress.getIsDefault() == 1){//是默认地址
            ReceiveAddress query = new ReceiveAddress();
            query.setUserid(receiveAddress.getUserid());
            query.setIsDefault(1);
            List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
            if(null != list && !list.isEmpty()){
                for(ReceiveAddress r: list){
                    ReceiveAddress edit = new ReceiveAddress();
                    edit.setId(r.getId());
                    edit.setIsDefault(2);
                    this.receiveAddressDAO.update(edit);
                }
            }
        }
        this.receiveAddressDAO.update(receiveAddress);
    }

    @Override
    public void editNew(ReceiveAddress receiveAddress){
        List<SysRegion> sysRegionList =  NameSpaceConstant.regionList;
        if(sysRegionList != null && !sysRegionList.isEmpty()){
            for(SysRegion s : sysRegionList){
                if(s.getRegionId().length() == 6 && s.getRegionName().equals(receiveAddress.getCityName())){
                    receiveAddress.setCityId(Long.parseLong(s.getRegionId()));
                    receiveAddress.setProvinceId(Long.parseLong(s.getRegionId().substring(0 , 4)));
                    break;
                }
            }
            for(SysRegion s : sysRegionList){
                if(s.getRegionId().equals(receiveAddress.getProvinceId()+"")){
                    receiveAddress.setProvinceName(s.getRegionName());
                    break;
                }
            }
        }

        //如果当前修改的地址是默认地址，则修改用户其他地址为非默认地址
        if(receiveAddress.getIsDefault() == 1){//是默认地址
            ReceiveAddress query = new ReceiveAddress();
            query.setUserid(receiveAddress.getUserid());
            query.setIsDefault(1);
            List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
            if(null != list && !list.isEmpty()){
                for(ReceiveAddress r: list){
                    ReceiveAddress edit = new ReceiveAddress();
                    edit.setId(r.getId());
                    edit.setIsDefault(2);
                    this.receiveAddressDAO.update(edit);
                }
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
    public void updateDefault(Long id) {
        ReceiveAddress res = this.receiveAddressDAO.selectById(id);
        ReceiveAddress query = new ReceiveAddress();
        query.setIsDefault(1);
        query.setIsDelete(2);
        query.setUserid(res.getUserid());
        List<ReceiveAddress> list = this.receiveAddressDAO.selectList(query);
        if(null != list && !list.isEmpty()){
            for(ReceiveAddress add : list){
                ReceiveAddress edit = new ReceiveAddress();
                edit.setIsDefault(2);
                edit.setId(add.getId());
                this.receiveAddressDAO.update(edit);
            }
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

    public static void main(String[] args) {
        String id="111301";
        String str = id.substring(0,4);
        System.out.println(str);
    }

}
