package com.gt.manager.user.utils.wx;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

public class GetReqUrl {
    /**
     * 获取当前请求url
     * @param request
     * @return
     */
    public static String getPageUrl(HttpServletRequest request){
        String url = null;
        String yuming = request.getServerName();		//域名
        Integer duankou = request.getServerPort();		//端口
        String xiangmuming = request.getContextPath();	//项目名称
        String qitalujing = request.getServletPath();	//请求方法的地址
        String httpstr = request.getScheme();			//http或者https
        String canshu = request.getQueryString();		//参数
        if(canshu != null && !"".equals(canshu)){
            url = httpstr+"://"+yuming+xiangmuming+qitalujing+"?"+canshu;
        }else{
            url = httpstr+"://"+yuming+xiangmuming+qitalujing;
        }
        return url;
    }
}
