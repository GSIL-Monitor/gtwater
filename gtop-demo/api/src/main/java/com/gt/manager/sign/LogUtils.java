package com.gt.manager.sign;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
public class LogUtils {

    private String name;
    private String appKey;
    private String parameters;
    private String message;
    private String result;
    private String createTime;
    private Integer userId;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public void error(Logger logger) {
//        this.setCreateTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss S")).format(new Date()));
//        logger.error(JSON.toJSONString(this));
//    }
//
//    public void info(Logger logger) {
//        this.setCreateTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss S")).format(new Date()));
//        logger.info(JSON.toJSONString(this));
//    }
}
