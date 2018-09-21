package com.gt.manager;


import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;


import java.io.IOException;


public class ErrorFilter extends ZuulFilter {

    Logger log = Logger.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        if (response.getStatus()==401){
            RibbonHttpResponse ribbonHttpResponse= (RibbonHttpResponse) ctx.get("zuulResponse");
            try {
                if(ribbonHttpResponse.getStatusText().equals("UNAUTHORIZED")){
                    response.setStatus(200);
                    response.setContentType(" application/json");
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("result",-5);
                    jsonObject.put("data","");
                    jsonObject.put("message","Invalid or expired token");
                    response.getWriter().write(jsonObject.toString());

                    response.getWriter().flush();
                    return ctx;
                }
            } catch (IOException e) {
                return null;
            }

        }

        return null;
    }

}