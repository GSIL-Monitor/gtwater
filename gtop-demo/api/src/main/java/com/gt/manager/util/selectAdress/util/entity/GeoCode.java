package com.gt.manager.util.selectAdress.util.entity;


import java.io.Serializable;
import java.util.List;

public class GeoCode
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String lat;
    private String lng;
    private String city;
    private String district;
    private String province;
    private String street;
    private String streetNumber;
    private String address;
    private String business;
    private String cityCode;
    private List<Poi> pois;

    public GeoCode()
    {
    }

    public GeoCode(String lat, String lng, String city, String district, String province, String street, String streetNumber, String address, String business, String cityCode)
    {
        this.lat = lat;
        this.lng = lng;
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
        this.streetNumber = streetNumber;
        this.address = address;
        this.business = business;
        this.cityCode = cityCode;
    }

    public GeoCode(String lat, String lng)
    {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat()
    {
        return this.lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLng()
    {
        return this.lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getCity()
    {
        return this.city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getDistrict()
    {
        return this.district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public String getProvince()
    {
        return this.province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getStreet()
    {
        return this.street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreetNumber()
    {
        return this.streetNumber;
    }

    public void setStreetNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getBusiness()
    {
        return this.business;
    }

    public void setBusiness(String business)
    {
        this.business = business;
    }

    public String getCityCode()
    {
        return this.cityCode;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    public List<Poi> getPois()
    {
        return this.pois;
    }

    public void setPois(List<Poi> pois)
    {
        this.pois = pois;
    }
}
