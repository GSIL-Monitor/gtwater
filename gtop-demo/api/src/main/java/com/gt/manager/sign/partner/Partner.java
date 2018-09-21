package com.gt.manager.sign.partner;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "partner")
public class Partner {
    @XmlAttribute
    private String name;
    //服务器秘钥
    @XmlAttribute
    private String appSecret;
    //appkey应用唯一标识
    @XmlAttribute
    private String appKey;
    @XmlAttribute
    private boolean appKeyPermission;

    @XmlElementWrapper(name = "allowInvokeAPIs")
    @XmlElement(name = "allowInvokeAPI")
    private List<AllowInvokeAPI> allowInvokeAPIs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isAppKeyPermission() {
        return appKeyPermission;
    }

    public void setAppKeyPermission(boolean appKeyPermission) {
        this.appKeyPermission = appKeyPermission;
    }

    public List<AllowInvokeAPI> getAllowInvokeAPIs() {
        return allowInvokeAPIs;
    }

    public void setAllowInvokeAPIs(List<AllowInvokeAPI> allowInvokeAPIs) {
        this.allowInvokeAPIs = allowInvokeAPIs;
    }
}
