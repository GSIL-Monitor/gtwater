package com.gt.manager;

/**
 * <p>ClassName: Result.java</p>
 * <p>Description:错误返回码定义 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年4月1日 上午11:38:12</p>
 */
public enum Result {
    //公共部分
    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL"),
    SERVER_EXCEPTION(500, "服务器错误"),
    TOKEN_FAIL(501, "token已过期，请重新登录！"),
    //BAD_PARAMETERS(502,"参数[%s]不能为空"),
      BAD_PARAMETERS(502,"参数不能为空"),
    //优惠券
    COUPON_EXCE_NUM(1001,"已领取此批次优惠券"),
    COUPON_EXCE_TOTAL(1002,"此批次优惠券已被领完"),
    COUPON_RECEIVE_TERM(1003,"只有新用户才可以领取"),
    // 用户
    USER_NOT_EXIST(2001, "用户不存在"),
    USER_EXIST(2002, "用户已存在"),
    USER_PHONE_EXIST(2003, "电话号码已绑定"),
    USER_ACTIVATE(2004, "用户已激活"),
    USER_CODE_ERROR(2005, "验证码不正确"),
    USER_PASS_ERROR(2006, "密码不正确"),
    USER_NOT_ALLOW(2008, "用户被禁用,请联系客服！"),
    ROLE_EXIST(2009, "角色已存在"),
    MENU_EXIST(2011, "菜单已存在"),
    ROLE_NOT_EXIST(2010, "角色不存在"),
    USER_BIND(2007, "用户被绑定！"),
    TENANT_EXIST(2012, "租户已存在"),
    //个人中心分红
    WITHDRAW_MONEY(3000,"提现金额不正确"),
    WITHDRAW_MONEY_NOTNULL(3000,"提现金额不能为0"),
    // 订单模块4000-4999
    REFUND_MONEY_ERROR(4000, "退款金额不正确"),
    APPLY_EXCEED_BUY_COUNT(4001,"申请数量超过购买数量"),
    ORDER_CREATE_ERROR(4500, "订单建设失败"),
    ORDER_NOT_EXIST(4504, "订单不存在"),
    ORDER_SENT_NOT_EXIST(4506, "订单的发货记录不存在"),
    
    //	11000-11999	Banner、新闻、评论
    BANNER_NOT_EXIT(11001, "Banner不存在"),
    BANNER_ZERO_EXIT(11002, "Banner列表数据为0"),

    // 代理商3000-4000
    AGENT_NOT_EXIST(3001, "代理商不存在"),
    // 代理商3000-4000
    AGENT_EXIST(3006, "代理商已存在"),
    AGENT_TYPE_ERROR1(3007,"二维码扫码的代理商为公司，应为门店"),

    AGENT_TYPE_ERROR2(3008,"二维码扫码的代理商为门店，应为公司"),
    AGENT_IS_DISABLED(3009,"二维码扫码的代理商已被冻结"),
    // 代理商3000-4000
    AGENT_CAN_NOT_ADD_SALES(3002, "总代理商下不允许添加普通员工角色，只能添加管理员角色"),
    // 代理商3000-4000
    AGENT_CAN_NOT_ADD_MULTI_MANAGER(3005, "总代理商下不允许添加多个管理员角色"),
    // 代理商4000-4500
    USER_ROLE_NOT_EXIST(4001, "用户角色不存在"),
    // 代理商4000-4500（如：门店添加了管理员角色，管理角色只能在父级代理出现）
    USER_ROLE_NOT_CORRECT(4002, "用户角色不正确"),

    // 代理商4500-5000
    USER_AGENT_ROLE_NOT_EXIST(4501, "用户关系信息不存在"),

    // 代理商4500-5000
    SALES_AGENT_ROLE_NOT_EXIST(4502, "用户关联业务人员时，业务人员的关系不存在。"),

    //二维码5001-5100
    BARCODE_CODEKEY_NOT_EXIST(5001,"请求参数codekey不能为空"),

    BARCODE_GET_TENANT_CODESTRORE_EXCEPTION(5002,"getTenantCodeStore查询异常"),

    SALESPULLNEW_PAGENUM_NOT_EXIST(5003,"pageNum can not empty"),

    SALESPULLNEW_PAGESIZE_NOT_EXIST(5004,"pageSize can not empty"),

    SALESPULLNEW_SALEID_NOT_EXIST(5005,"saleId can not empty"),

    SALESPULLNEW_EXCEPTION(5006,"search exception"),

    GETMYCUSTOMER_TYPE_NOT_EXIST(5007,"type can not empty"),

    GETMYCUSTOMER_USERID_NOT_EXIST(5007,"userId can not empty");
    public final int code;
    public final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
