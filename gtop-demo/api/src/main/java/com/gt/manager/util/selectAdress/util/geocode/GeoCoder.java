package com.gt.manager.util.selectAdress.util.geocode;


import com.gt.manager.util.selectAdress.util.entity.GeoCode;
import com.gt.manager.util.selectAdress.util.entity.Poi;
import com.gt.manager.util.selectAdress.util.http.HttpHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class GeoCoder
{
    private static HttpHelper http = new HttpHelper();

    public static GeoCode geocode(String address, String city)
            throws Exception
    {
        String path = "http://api.map.baidu.com/geocoder/v2/";
        Map params = new HashMap();
        params.put("ak", "hPxnqkxaBVEAZYReS1pdogdK");
        params.put("output", "json");
        params.put("city", city);
        params.put("address", address);
        byte[] resultArray = http.post(path, params, "utf8", 30);

        String jsonString = new String(resultArray);
        org.json.JSONObject jsonObject = new org.json.JSONObject(jsonString);
        String status = String.valueOf(jsonObject.getInt("status"));
        if ("0".equals(status)) {
            JSONObject locationObject =  jsonObject.getJSONObject("result").getJSONObject("location");

            GeoCode geoCode = new GeoCode( String.valueOf(locationObject.getDouble("lat")),
                    String.valueOf(locationObject.getDouble("lng")));
            return geoCode;
        }
        return null;
    }

    public static GeoCode geocode(String coordtype, String lat, String lng, int pois)
            throws Exception
    {
        String path = "http://api.map.baidu.com/geocoder/v2/";
        Map params = new HashMap();
        params.put("ak", "hPxnqkxaBVEAZYReS1pdogdK");
        params.put("coordtype", coordtype);
        params.put("location", lat + "," + lng);
        params.put("output", "json");
        params.put("pois", String.valueOf(pois));
        byte[] resultArray = http.post(path, params, "utf8", 30);
        String jsonString = new String(resultArray, "utf8");
        JSONObject jsonObject = new JSONObject(jsonString);
        String status = String.valueOf(jsonObject.getInt("status"));

        if ("0".equals(status)) {
            JSONObject obj0 = jsonObject.getJSONObject("result");
            String address = obj0.getString("formatted_address");
            JSONObject obj1 = obj0.getJSONObject("location");
            JSONObject obj2 = obj0.getJSONObject("addressComponent");
            GeoCode geoCode = new GeoCode(
                   String.valueOf(obj1.getDouble("lat")),
                    String.valueOf(obj1.getDouble("lng")),
                    obj2.getString("city"),
                    obj2.getString("district"),
                    obj2.getString("province"),
                    obj2.getString("street"),
                    obj2.getString("street_number"), address,
                    obj0.getString("business"),
                    String.valueOf(obj0.getInt("cityCode")));

            if (pois > 0) {
                JSONObject obj3 = jsonObject.getJSONObject("result");
                JSONArray obj4 = obj3.getJSONArray("pois");
                if ((obj4 != null) && (obj4.length() > 0)) {
                    List poiList = new ArrayList();
                    for (int i = 0; i < obj4.length(); ++i) {
                        JSONObject poiObject = obj4.getJSONObject(i);
                        Poi poi = new Poi(poiObject.getString("addr"),
                                poiObject.getString("cp"),
                                poiObject.getString("distance"),
                                poiObject.getString("name"),
                                poiObject.getString("poiType"),
                                String.valueOf(poiObject.getJSONObject("point").getDouble("y")),
                                String.valueOf(poiObject.getJSONObject("point").getDouble("x")),
                                poiObject.getString("tel"),
                                poiObject.getString("uid"),
                                poiObject.getString("zip"));
                        poiList.add(poi);
                    }
                    geoCode.setPois(poiList);
                }
            }
            return geoCode;
        }
        return null;
    }

    public static List<GeoCode> coords2BaiDu(String[] lats, String[] lngs, int from, int to)
            throws Exception
    {
        String path = "http://api.map.baidu.com/geoconv/v1/";
        Map params = new HashMap();
        params.put("ak", "hPxnqkxaBVEAZYReS1pdogdK");
        StringBuffer coords = new StringBuffer();
        for (int i = 0; i < lats.length; i++) {
            coords.append(lats[i] + "," + lngs[i] + ";");
        }
        coords.deleteCharAt(coords.lastIndexOf(";"));
        params.put("coords", coords.toString());
        params.put("from", String.valueOf(from));
        params.put("to", String.valueOf(to));
        byte[] resultArray = http.post(path, params, "utf8", 30);
        String jsonString = new String(resultArray, "utf8");
        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);

        String status = String.valueOf(jsonObject.getInt("status"));
        if ("0".equals(status)) {
            JSONArray resultJsonArray = new JSONArray(jsonObject.getString("result"));
            System.out.println("resultJsonArray===="+resultJsonArray);
            if ((resultJsonArray != null) && (resultJsonArray.length() > 0)) {
                List geoCodes = new ArrayList();
                for (int i = 0; i < resultJsonArray.length(); i++)
                {
                    GeoCode gc = new GeoCode(
                            String.valueOf(resultJsonArray.getJSONObject(i).getDouble("x")),
                            String.valueOf(resultJsonArray.getJSONObject(i).getDouble("y"))
                    );
                    geoCodes.add(gc);
                }
                return geoCodes;
            }
            return null;
        }

        return null;
    }

    public static double getShortDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double ns1 = lat1 * 0.01745329252D;
        double ew1 = lng1 * 0.01745329252D;
        double ns2 = lat2 * 0.01745329252D;
        double ew2 = lng2 * 0.01745329252D;

        double dew = ew1 - ew2;

        if (dew > 3.14159265359D)
            dew = 6.28318530712D - dew;
        else if (dew < -3.14159265359D) {
            dew += 6.28318530712D;
        }
        double dx = 6370693.5D * Math.cos(ns1) * dew;

        double dy = 6370693.5D * (ns1 - ns2);

        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public static double getLongDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double ns1 = lat1 * 0.01745329252D;
        double ew1 = lng1 * 0.01745329252D;
        double ns2 = lat2 * 0.01745329252D;
        double ew2 = lng2 * 0.01745329252D;

        double distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) *
                Math.cos(ns2) * Math.cos(ew1 - ew2);

        if (distance > 1.0D)
            distance = 1.0D;
        else if (distance < -1.0D) {
            distance = -1.0D;
        }
        distance = 6370693.5D * Math.acos(distance);
        return distance;
    }

    /*public static Map<String, String> getDistrictAddress(String city, String address)
            throws Exception
    {
        Map map = new HashMap();
        String district = null;
        String uc = URLEncoder.encode(city, "UTF-8");
        String ua = URLEncoder.encode(address, "UTF-8");
        InputStream ins = null;


        String path = "http://apis.map.qq.com/ws/place/v1/suggestion/?keyword=" +
                ua + "&region=" + uc +
                "&output=json&key=OT5BZ-QCV3S-D74O6-6PLTM-7H6MZ-RRBDE";
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(path);
        client.getParams().setSoTimeout(300000);
        client.executeMethod(getMethod);
        net.sf.json.JSONObject obj =
                net.sf.json.JSONObject.fromObject(getMethod.getResponseBodyAsString());
        ins = getMethod.getResponseBodyAsStream();
        byte[] b = new byte[2048];
        StringBuilder sb = new StringBuilder();
        while (ins.read(b) != -1) {
            sb.append(new String(b, "utf-8").trim());
            b = new byte[2048];
        }
        getMethod.releaseConnection();
        ins.close();

        String status = obj.getString("status");
        if (status.equals("0")) {
            String str = obj.getString("data");
            if (!(str.equals("[]"))) {
                net.sf.json.JSONArray arr =
                        net.sf.json.JSONArray.fromObject(str);
                for (int i = 0; i < arr.size(); ++i) {
                    net.sf.json.JSONObject obj2 =
                            net.sf.json.JSONObject.fromObject(arr.get(i));
                    district = obj2.getString("address");
                    String[] s = ReckonAddress.getArea(district);
                    if ((city.equals(s[2])) || (city.equals(s[1]))) {
                        map.put("district", s[3]);
                        map.put("districtId", s[0]);
                    }
                }
            }
        }
        if (map.isEmpty()) {
            map = getDistrictAddressForBaiDu(city, address);
        }
        return map;
    }*/

/*
    public static Map<String, String> getDistrictAddressForBaiDu(String city, String address)
            throws Exception
    {
        Map map = new HashMap();
        String district = null;
        String uc = URLEncoder.encode(city, "UTF-8");
        String ua = URLEncoder.encode(address, "UTF-8");
        InputStream ins = null;

        String path = "http://api.map.baidu.com/place/v2/search?ak=hPxnqkxaBVEAZYReS1pdogdK&output=json&query=" +
                ua +
                "&page_size=20&page_num=0&scope=1&region=" + uc;

        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(path);
        client.getParams().setSoTimeout(300000);
        client.executeMethod(getMethod);
        net.sf.json.JSONObject obj =
                net.sf.json.JSONObject.fromObject(getMethod.getResponseBodyAsString());
        ins = getMethod.getResponseBodyAsStream();
        byte[] b = new byte[2048];
        StringBuilder sb = new StringBuilder();
        while (ins.read(b) != -1) {
            sb.append(new String(b, "utf-8").trim());
            b = new byte[2048];
        }
        getMethod.releaseConnection();
        ins.close();

        String status = obj.getString("status");
        if (status.equals("0")) {
            String str = obj.getString("results");
            if (!(str.equals("[]"))) {
                net.sf.json.JSONArray arr =
                        net.sf.json.JSONArray.fromObject(str);
                for (int i = 0; i < arr.size(); ++i) {
                    net.sf.json.JSONObject obj2 =
                            net.sf.json.JSONObject.fromObject(arr.get(i));
                    district = obj2.getString("address");
                    String[] s = ReckonAddress.getArea(district);
                    if ((city.equals(s[2])) || (city.equals(s[1]))) {
                        map.put("district", s[3]);
                        map.put("districtId", s[0]);
                    }
                }
            }
        }
        return map;
    }*/
}
