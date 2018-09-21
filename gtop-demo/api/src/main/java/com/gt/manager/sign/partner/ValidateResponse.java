package com.gt.manager.sign.partner;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: ValidateResponse</p>
 * <p>Description: 参数验证返回参数</p>
 * <p>author zhouhe</p>
 * <p>date 2017/2/13 14:26</p>
 */
public class ValidateResponse implements Serializable {

    private static final long serialVersionUID = 3260372920296853029L;

    private Boolean result;

    private List<String> paramNames;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }
}
