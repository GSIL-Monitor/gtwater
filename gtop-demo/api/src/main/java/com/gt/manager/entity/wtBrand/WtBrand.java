package com.gt.manager.entity.wtBrand;

import java.io.Serializable;

/**
 * 品牌表
 *
 * @author why
 */

public class WtBrand implements Serializable {

    /**
     * default SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // Fields

    private Long id;//
    private String name;//
    private String messages;//
    private Long createTime;// 创建时间
    private Long createId;// 创建人
    private Integer delState;// 删除状态
    private Long gtopBrandId;//gtop品牌ID

    // Empty Constructor
    public WtBrand() {
        super();
    }

    // Full Constructor
    public WtBrand(Long id, Long gtopBrandId, String name, String messages, Long createTime, Long createId, Integer delState) {
        this.id = id;
        this.gtopBrandId = gtopBrandId;
        this.name = name;
        this.messages = messages;
        this.createTime = createTime;
        this.createId = createId;
        this.delState = delState;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessages() {
        return this.messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return this.createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Integer getDelState() {
        return this.delState;
    }

    public void setDelState(Integer delState) {
        this.delState = delState;
    }

    public Long getGtopBrandId() {
        return gtopBrandId;
    }

    public void setGtopBrandId(Long gtopBrandId) {
        this.gtopBrandId = gtopBrandId;
    }

    @Override
    public String toString() {
        StringBuffer entityStirngBuffer = new StringBuffer();
        entityStirngBuffer.append("id = " + this.getId() + ",");
        entityStirngBuffer.append("gtopBrandId = " + this.getGtopBrandId() + ",");
        entityStirngBuffer.append("name = " + this.getName() + ",");
        entityStirngBuffer.append("messages = " + this.getMessages() + ",");
        entityStirngBuffer.append("createTime = " + this.getCreateTime() + ",");
        entityStirngBuffer.append("createId = " + this.getCreateId() + ",");
        entityStirngBuffer.append("delState = " + this.getDelState() + ",");
        return entityStirngBuffer.toString();
    }

}