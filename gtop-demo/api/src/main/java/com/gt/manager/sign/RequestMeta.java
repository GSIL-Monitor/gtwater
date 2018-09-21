package com.gt.manager.sign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class RequestMeta {

    @XmlElement(name = "data", required = true)
    private RequestData data = new RequestData();
    @XmlElement(name = "signature", required = true)
    private String signature;

    public RequestData getData() {
        return data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "RequestMeta{" +
                "data=" + data +
                ", signature='" + signature + '\'' +
                '}';
    }
}
