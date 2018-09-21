package com.gt.manager.sign;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "data")
public class RequestData {
    @XmlTransient
    private String source;

    @XmlElement(name = "operation")
    private String operation;
    @XmlElement(name = "appKey")
    private String appKey;
    @XmlElement(name = "partnerId")
    private String partnerId;
    @XmlElement(name = "timeStamp")
//    private Date timeStamp;
    private String timeStamp;
    @XmlElement(name = "format", required = false)
    private String format;
    @XmlElement(name = "parameters")
    @XmlJavaTypeAdapter(MapAdapter.class)
    private Map<String, String> parameters;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }


    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "operation='" + operation + '\'' +
                ", format='" + format + '\'' +
                ", appKey='" + appKey + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", timeStamp=" + timeStamp +
                ", parameters=" + parameters +
                "}\n";
    }
}