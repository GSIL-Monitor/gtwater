package com.gt.manager.sign.partner;

import com.gt.manager.sign.HmacSha1;
import com.gt.manager.sign.SignUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>ClassName: Validate</p>
 * <p>Description: 这里用一句话描述这个方法的作用</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/14 13:58</p>
 */
public class ValidateAvailable {

    /**
     * <p>Description: 对签名有效性进行判断</p>
     * <p>param  </p>
     * <p>author zhouhe </p>
     * <p>date 2016/11/14 14:06 </p>
     * <p>return </p>
     */
    public static Boolean isAvailableSignature(HttpServletRequest request, String signatureHeader, Partner partner) {
        Enumeration<String> parameterNames = request.getParameterNames();
        SortedMap<String, Object> paramsMap = new TreeMap<String, Object>();
        for (Enumeration e = parameterNames; e.hasMoreElements(); ) {
            String thisName = e.nextElement().toString();
            String thisValue = request.getParameter(thisName);
            paramsMap.put(thisName, thisValue);
        }
        StringBuffer sb = SignUtils.paramsSort(paramsMap);
        String sbString = sb.toString();
        System.out.println("SignUtils.paramsSort===========  " + sbString);
        //秘钥
        String signatureHmaSha = null;
        try {
            //进行SHA-1加密
            signatureHmaSha = HmacSha1.getSignature(sbString.getBytes(), partner.getAppSecret().getBytes());
        } catch (Exception e) {
            //失败则签名为空
            signatureHmaSha = "";
        }
        System.out.println("======================signatureHmaSha==========  " + signatureHmaSha);
        System.out.println("======================signatureHeader==========  " + signatureHeader);
        return signatureHmaSha.equals(signatureHeader);
    }

    /**
     * <p>Description: 对用户的权限的有效性进行判断</p>
     * <p>param  </p>
     * <p>author zhouhe </p>
     * <p>date 2016/11/14 14:07 </p>
     * <p>return </p>
     */
    public static Boolean isAllowed(HttpServletRequest request, Partner partner) {
        //获取请求的地址 API接口地址
        String method = request.getMethod();
        String relativeUrl = request.getRequestURI();

        //对需要进行token验证的Api列表进行判断
        List<AllowInvokeAPI> allowInvokeAPIs = partner.getAllowInvokeAPIs();
        for (AllowInvokeAPI allowInvokeAPI : allowInvokeAPIs) {
            if (method.equals(allowInvokeAPI.getMethod()) && relativeUrl.contains(allowInvokeAPI.getApi())) {
                return true;
            }
        }
        return false;
    }

}
