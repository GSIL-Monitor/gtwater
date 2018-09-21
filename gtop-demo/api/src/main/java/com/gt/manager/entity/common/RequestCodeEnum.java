package com.gt.manager.entity.common;

/**
 * 请求码
 */
public enum RequestCodeEnum {
    添加地址("1000"),
    修改地址("1001"),
    删除地址("1002"),
    设置默认地址("1003"),
    查询地址列表("1004"),
    获取全部省("1005"),
    获取省下面市("1006"),
    获取市下面的区("1007"),
    获取省市区("1008"),
    根据地址id获取地址("1009"),
    根据用户id获取默认地址("1010"),
    添加合伙人("2000"),
    提成明细("2001"),
    根据合伙人id获取绑定二维码信息("2002"),
    获取我的推广列表("2003"),
    根据用户id获取合伙人信息("2004"),
    获取微信配置("3000"),
    根据微信定位获取地址("3001"),
    默认注册("3002"),
    一键送水获取水票("4001"),
    水票使用("4002"),
    立即配送("4003");

    public String req_code;

    RequestCodeEnum(String req_code){
        this.req_code = req_code;
    }

    public static void main(String[] args) {
        System.out.println(RequestCodeEnum.添加地址.req_code);
    }
}
