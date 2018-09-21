package com.gt.manager.sign.partner;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
* <p>Description: Xml用参数</p>
* <p>param  </p>
* <p>author zhouhe </p>
* <p>date 2016/11/15 17:48 </p>
* <p>return </p>
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "allowInvokeAPI")
public class AllowInvokeAPI {

    @XmlElement(name = "api")
    private String api;

    @XmlElement(name = "method")
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
