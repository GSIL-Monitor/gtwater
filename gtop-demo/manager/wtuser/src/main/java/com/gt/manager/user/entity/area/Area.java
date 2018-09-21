package com.gt.manager.user.entity.area;

import java.io.Serializable;

/**
 * 区
 * @author Administrator
 *
 */
public class Area extends Province implements Serializable {

	private static final long serialVersionUID = 1L;
	private String city;
	public Area(String name, String id, String city) {
		super(name, id);
		this.city = city;
	}
	public Area(String name, String id) {
		super(name, id);
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	

}
