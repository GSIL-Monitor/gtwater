package com.gt.manager.user.resource.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gt.manager.entity.user.User;
import com.gt.manager.util.httpHelper.HttpHelper;
import com.sun.org.apache.xerces.internal.dom.PSVIDocumentImpl;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UserTest {
    //获取地址
    public static void getAllAdd() throws Exception {
        HashMap p = new HashMap();
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://192.168.30.7:10000/wtuser/apiwtuser/sysRegion/getAll", p, "UTF-8", 10);
        System.out.println("获取地址" + res);
    }


    public static void addAddress() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "1000");
        JSONObject json = new JSONObject();
        json.put("userid", "1");
        json.put("name", "王慧永");
        json.put("phone", "18210552613");
        json.put("address", "西三环北路72号世纪经贸大厦");
        json.put("isDefault", "1");
        json.put("provinceId", "1111");
        json.put("provinceName", "北京市");
        json.put("cityId", "111101");
        json.put("cityName", "北京市");
        json.put("districtId", "11110108");
        json.put("districtName", "海淀区");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
//        String res = h.postData2("http://192.168.30.7:8195/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
        System.out.println("添加地址" + res);
    }

    public static void updateAdd() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "1001");
        JSONObject json = new JSONObject();
        json.put("id", "1");
        json.put("isDelete", "1");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
        System.out.println("修改地址" + res);
    }

    public static void delAdd() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "1002");
        JSONObject json = new JSONObject();
        json.put("id", "2");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
        System.out.println("删除地址" + res);
    }

    public static void defaultAdd() throws Exception {
        JSONObject p = new JSONObject();
        p.put("platform", "wx");
        p.put("requestCode", "1003");
        JSONObject json = new JSONObject();
        json.put("id", "2");
        p.put("params", json);
//        JSONObject res = doPost("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p);
//        System.out.println("设置默认地址"+res);
    }

    public static void selectAdd() throws Exception {
        JSONObject p = new JSONObject();
        p.put("platform", "wx");
        p.put("requestCode", "1004");
        JSONObject json = new JSONObject();
        json.put("userId", "1");
        json.put("pageNo", "2");
        json.put("pageSize", "3");
        p.put("params", json);
//        JSONObject res = doPost("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p);
//        String res = doJsonPost("http://127.0.0.1:10000/wtuser/apiwtuser/receiveAddress/addressBusiness", p.toJSONString());
//        System.out.println("获取地址列表" + res);

    }

    public static void getOpenId() throws Exception {
        HashMap p = new HashMap();
        p.put("code", "0713Y0XR1TlGJ71SbqVR1BMTWR13Y0Xv");
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/user/getOpenId", p, "UTF-8", 10);
        System.out.println("地址列表" + res);
    }

    public static void addPartner() throws Exception {
        //http://127.0.0.1:10000/wtuser/apiwtuser/partner/partnerScan?code=061vifK00KjKhG1gMaJ00t0nK00vifKN&state=123
        HashMap p = new HashMap();
        p.put("code", "061vifK00KjKhG1gMaJ00t0nK00vifKN");
        p.put("state", "123");
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/partner/partnerScan", p, "UTF-8", 10);
        System.out.println("地址列表" + res);
    }

    public static void getWxConfig() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "3000");
        JSONObject json = new JSONObject();
        json.put("url", "http://www.baidu.com");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/wx/wxBusiness", p, "UTF-8", 10);
//        String res = h.postData2("http://127.0.0.1:8195/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
        System.out.println("微信配置" + res);
    }

    public static void getWxAddress() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "3001");
        JSONObject json = new JSONObject();
        json.put("latitude", "39.9350478666273");
        json.put("longitude", "116.31747087043256");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/wx/wxBusiness", p, "UTF-8", 10);
//        String res = h.postData2("http://127.0.0.1:8195/apiwtuser/receiveAddress/addressBusiness", p, "UTF-8", 10);
        System.out.println("根据经纬度获取地址" + res);
    }

    public static void addPartnerInfo() throws Exception {
        HashMap p = new HashMap();
        p.put("platform", "wx");
        p.put("requestCode", "2000");
        JSONObject json = new JSONObject();
        json.put("userId", "122537974607904769");
        json.put("messages", "123");
        json.put("userName", "why");
        json.put("phond", "18210552613");
        json.put("zmFile", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANcAAACeCAYAAAChKp8RAAAC6UlEQVR4nO3awU0bQRiA0XGUUqAJqCAUAkcoIgXAEQoxFUAFuZleHFshCgSIA+LDwL4nzWEtrzU+fJpfa8+WKwN4dV+2vQH4rMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQmXhcl+NoNhuzZ6/9cXYzxs3Z/gvu/bX21x/ApzZbrmx7E+/TOryD8eN0Ma6Od55569FY3Trmy/PxrdkcH8DXbW/g87kZZ98vxt4qSmFN28THwlvrk+Z21Hv+rX+PfLvj5HqM65Pdp8fCo8vX/w68O+J6BetTaj1dL5fzcbi6Ppwvb69Xa3E69lavzm+vF6d7294ub0RcEBHXBo+OdxvGuouDO+/dPRnXb7RX3hdxbfBn5Luzzv/9qOLhWMgUiQsiHsUH1mPhxb1XDre0E7bJyRUwFrImLohM+O9PN+Ns/9cPvi+ydzoWV8djcTQbBxeb337P4XzjQxE+vgmfXDvj+Gr58Eng/65VWL//cfjoE8Unlh+Rp2PCJxe0JnxyQUtcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQ+Qm6qM1mQ5le5QAAAABJRU5ErkJggg==");
        json.put("fmFile", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANkAAACQCAYAAACF6c7SAAADpElEQVR4nO3aMU4baRzG4b9XOQreAuUE9glwmlRpt7NL06SjpKMxJe5ot0plnwCfIKJYc5fZsQHLtlgFS7xLgOeRRshjZhgQP33fjL9O0yog5o/XvgB470QGYSKDMJFBmMggTGQQJjIIExmEiQzCRAZhIoMwkUGYyCBMZBAmMggTGYSJDMJEBmEigzCRQZjIIExkECYyCBMZhIkMwkQGYSKDMJFBmMggTGQQJjIIExmEiQzCRAZhIoMwkUGYyCBMZBAmMggTGYSJDMJEBmEigzCRQZjIIExkECYyCBMZhIkMwkQGYSKDMJFBmMggTGQQJjIIExmEiewAd5f96nQ6m61/effal8QbILKNu7rsd3Yi2t/+qutqmmazXX95OHIvvkM2ob5/nWb130LdR9at27Omrk5e6JTzUXUGVbPmql7qlLw9n177At6vNtrzafUmS4F9cKaLe6aD5071+rU905uP9t/v1umianHa/e9zjOav94vyvxHZnuGsqeWkV+0QVMut+6/tbf1+fa4/j3aPXY1a998zq+HDuTbHLSfVa/fOds7BRyCyJxyNz2q4OK2LJweaeV20Q1Rv8t00kGcR2ZNO6ns70kzPL2v/2d98NKhpO8pdj4+ePHLbztSze1qLzMXymxPZxrJuF7067t6/ehzNutv3TfNRDaa9mlyP69eJPTVd5CPydPHR3T/1s73P+rqp56Su2jB+dgfVqVk1X39UZzBdh/OMQQw2RPZoeVuL3nF1t/cdjetmWdVfhTa9H5kO+QxtNV2c7uwZvsil8raYLq49fKb17cveNHBeo/W91LAmq3u0wWErNEwXWRHZyvyiThfDOnucB95dVn/9wGK9XKON5KrG45v267K+/d21HIrDNDSzYTXtqNM2NGna0aZpx6Bm9oxjVn++Gs52Xx+yDX/1U3gPrF18IasVH+fHy7p55lOR1aLi7u1ZNS+2UJLflcggzD0ZhIkMwkQGYSKDMJFBmMggTGQQJjIIExmEiQzCRAZhIoMwkUGYyCBMZBAmMggTGYSJDMJEBmEigzCRQZjIIExkECYyCBMZhIkMwkQGYSKDMJFBmMggTGQQJjIIExmEiQzCRAZhIoMwkUGYyCBMZBAmMggTGYSJDMJEBmEigzCRQZjIIExkECYyCBMZhIkMwkQGYSKDMJFBmMggTGQQJjIIExmEiQzCRAZhIoMwkUGYyCBMZBAmMggTGYSJDMJEBmEigzCRQZjIIExkEPYvgUIFqjETCS8AAAAASUVORK5CYII=");
        p.put("params", json);
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://127.0.0.1:10000/wtuser/apiwtuser/partner/partnerReg", p, "UTF-8", 10);
        System.out.println("添加合伙人信息" + res);
    }

    public static void Zf() throws Exception {
        HashMap p = new HashMap();
        p.put("orderCode", "123456789");
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://192.168.101.219:10000/wtorder/api/order/onLinePayCallBack", p, "UTF-8", 10);
        System.out.println("支付" + res);
    }

    public static void long2Short() throws Exception{
        HashMap p = new HashMap();
        p.put("url","https://www.baidu.com");
        HttpHelper h = new HttpHelper();
        String res = h.postData2("http://gtop-trial.gyexpress.cn/wtuser/apiwtuser/wx/long2Short",p,"UTF-8",10);
        System.out.println("长链接转短连接"+res);
    }

    public static void main(String[] args) throws Exception {
//        String str = "{\"zmFile\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANcAAACeCAYAAAChKp8RAAAC6UlEQVR4nO3awU0bQRiA0XGUUqAJqCAUAkcoIgXAEQoxFUAFuZleHFshCgSIA+LDwL4nzWEtrzU+fJpfa8+WKwN4dV+2vQH4rMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQmXhcl+NoNhuzZ6/9cXYzxs3Z/gvu/bX21x/ApzZbrmx7E+/TOryD8eN0Ma6Od55569FY3Trmy/PxrdkcH8DXbW/g87kZZ98vxt4qSmFN28THwlvrk+Z21Hv+rX+PfLvj5HqM65Pdp8fCo8vX/w68O+J6BetTaj1dL5fzcbi6Ppwvb69Xa3E69lavzm+vF6d7294ub0RcEBHXBo+OdxvGuouDO+/dPRnXb7RX3hdxbfBn5Luzzv/9qOLhWMgUiQsiHsUH1mPhxb1XDre0E7bJyRUwFrImLohM+O9PN+Ns/9cPvi+ydzoWV8djcTQbBxeb337P4XzjQxE+vgmfXDvj+Gr58Eng/65VWL//cfjoE8Unlh+Rp2PCJxe0JnxyQUtcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQERdExAURcUFEXBARF0TEBRFxQURcEBEXRMQFEXFBRFwQ+Qm6qM1mQ5le5QAAAABJRU5ErkJggg==\"}";
//        JSONObject j = JSONObject.parseObject(str);
//        System.out.println(j.getString("zmFile"));

//        getAllAdd();//获取省市区
//        addAddress();//添加地址
//        updateAdd();//修改地址
//        delAdd();//删除地址
//        defaultAdd();//设置默认
//        selectAdd();//地址列表
//        getOpenId();//默认注册
//        getWxConfig();//获取微信jssdk配置
//        getWxAddress();//根据微信定位经纬度获取详细地址
//        addPartnerInfo();//添加合伙人
//        Zf();
        long2Short();
    }


}
