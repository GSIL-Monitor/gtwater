package com.gt.manager.util.selectAdress.util.common;


import com.gt.manager.util.selectAdress.util.entity.GeoCode;
import com.gt.manager.util.selectAdress.util.geocode.GeoCoder;
import net.sf.json.JSONObject;

public class T {

        public static void main(String[] args)
                throws Exception
        {
            Double gc3 =  GeoCoder.getShortDistance(39.914814,116.502076,39.914925,116.522342);
            Double gc4 =  GeoCoder.getLongDistance(39.914814,116.502076,39.914925,116.522342);
            GeoCode gc2 = GeoCoder.geocode(GeoConstant.WGS84LL,"39.93","116.30",0);
            GeoCode gc = GeoCoder.geocode("北京市海淀区西三环北路72号世纪经贸大厦B座2901","北京市");

            //int i = (new Double(gc3)).intValue();
            System.out.println(gc4);
            //System.out.println(JSONObject.fromObject(gc2));
            System.out.println(JSONObject.fromObject(gc));
        }

}
