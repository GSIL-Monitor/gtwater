package com.gt.manager.user.service.receiveAddress;

import com.gt.manager.entity.receiveAddress.ReceiveAddress;

import java.util.List;

public interface ReceiveAddressService {

    public Long add(ReceiveAddress receiveAddress);

    public Long addNew(ReceiveAddress receiveAddress);

    public void delete(Long id);

    public void edit(ReceiveAddress receiveAddress);

    public void editNew(ReceiveAddress receiveAddress);

    public ReceiveAddress queryById(Long id);

    public List<ReceiveAddress> queryList(ReceiveAddress receiveAddress);

    public void updateDefault(Long id);

    public List<ReceiveAddress> queryMyList(Long userId, int pageSize, int pageNo);

}
