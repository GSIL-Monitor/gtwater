package com.gt.manager.user.util.entity;

import java.io.Serializable;

public class Poi
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String address;
    private String cp;
    private String distance;
    private String name;
    private String poiType;
    private String poiLat;
    private String poiLng;
    private String tel;
    private String uid;
    private String postcode;

    public Poi()
    {
    }

    public Poi(String address, String cp, String distance, String name, String poiType, String poiLat, String poiLng, String tel, String uid, String postcode)
    {
        this.address = address;
        this.cp = cp;
        this.distance = distance;
        this.name = name;
        this.poiType = poiType;
        this.poiLat = poiLat;
        this.poiLng = poiLng;
        this.tel = tel;
        this.uid = uid;
        this.postcode = postcode;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCp()
    {
        return this.cp;
    }

    public void setCp(String cp)
    {
        this.cp = cp;
    }

    public String getDistance()
    {
        return this.distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPoiType()
    {
        return this.poiType;
    }

    public void setPoiType(String poiType)
    {
        this.poiType = poiType;
    }

    public String getPoiLat()
    {
        return this.poiLat;
    }

    public void setPoiLat(String poiLat)
    {
        this.poiLat = poiLat;
    }

    public String getPoiLng()
    {
        return this.poiLng;
    }

    public void setPoiLng(String poiLng)
    {
        this.poiLng = poiLng;
    }

    public String getTel()
    {
        return this.tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getUid()
    {
        return this.uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getPostcode()
    {
        return this.postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
}
