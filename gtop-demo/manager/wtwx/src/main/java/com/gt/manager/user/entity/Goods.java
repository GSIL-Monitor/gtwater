package com.gt.manager.user.entity;

import com.alibaba.fastjson.JSONObject;
import com.gt.manager.entity.wtSku.WtSku;

public class Goods extends WtSku {
    private String goodsName;
    private String brandName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBrandName(){
        return brandName;
    }

    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    public static void main(String[] args) {
        Goods g = new Goods();
        g.setId(3L);
        g.setBrandId(1001L);
        g.setpId(1L);
        g.setGoodsCode("3002");
        g.setBrandId(2L);
        g.setBrandName("景甜15L");
        g.setSkuCode("10010315");
        g.setGoodsSpec("15");
        g.setGoodsUtil(4);
        g.setSellPrice(25L);
        g.setGoodsName("乐百氏");
        g.setGoodsPic("http://img.goola.cn/mallImages/20180809/a8ef2321f1fe4efea687739085ab889c.jpg");
        g.setBrandName("娃哈哈");
        System.out.println(JSONObject.toJSONString(g));
    }
}
