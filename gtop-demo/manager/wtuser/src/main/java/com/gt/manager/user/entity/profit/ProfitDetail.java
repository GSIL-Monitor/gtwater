package com.gt.manager.user.entity.profit;

/**
 * 分佣明细
 */
public class ProfitDetail {
    private String sendNo;      //配送单号
    private String goodsName;   //商品名称
    private Long arriveTime;    //送达时间
    private int arriveNum;      //实际送达数量
    private Long money;          //配送单金额
    private int proportion;     //分佣比例
    private Long sendMoney;     //派送单金额

    public ProfitDetail(String sendNo, String goodsName, Long arriveTime, int arriveNum, Long money, int proportion, Long sendMoney) {
        this.sendNo = sendNo;
        this.goodsName = goodsName;
        this.arriveTime = arriveTime;
        this.arriveNum = arriveNum;
        this.money = money;
        this.proportion = proportion;
        this.sendMoney = sendMoney;
    }

    public ProfitDetail() {
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getArriveNum() {
        return arriveNum;
    }

    public void setArriveNum(int arriveNum) {
        this.arriveNum = arriveNum;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }

    public Long getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(Long sendMoney) {
        this.sendMoney = sendMoney;
    }
}
