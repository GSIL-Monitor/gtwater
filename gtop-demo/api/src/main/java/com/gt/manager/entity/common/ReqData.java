package com.gt.manager.entity.common;

import java.io.Serializable;

public class ReqData implements Serializable {
    private static final long serialVersionUID = 8872582392958929300L;
    private String platform;
    private String requestCode;
    private String params;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
