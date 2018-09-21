package com.gt.manager.sign.partner;

import com.gt.manager.sign.XMLUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
public class TokenPartnerConfig {
    private static Map<String, Partner> partners = new HashMap<String, Partner>();
    private static TokenPartnerConfig instance;

    public static TokenPartnerConfig getInstance() {
        if (instance == null) {
            instance = new TokenPartnerConfig();
            try {
                Partners partnersBean = XMLUtils.xml2Java(Partners.class, TokenPartnerConfig.class.getResourceAsStream("/TokenPartnerConfig.xml"));
                List<Partner> partnerList = partnersBean.getPartners();
                for (Partner p : partnerList) {
                    partners.put(p.getAppKey(), p);
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static Partner getPartner(String appKey) {
        if (partners.size() == 0) {
            getInstance();
        }
        return partners.get(appKey);
    }

    public static void setPartner(String appkey, Partner partner) {
        partners.put(appkey, partner);
    }

}
