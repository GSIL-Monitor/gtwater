package com.gt.manager.user.entity.area;

import java.io.Serializable;

/**
 * 市
 * @author Administrator
 *
 */
public class City extends Province implements Serializable {
	private static final long serialVersionUID = 1L;
	private String province;

	
	public City(String name, String id) {
		super(name, id);
	}

	public City(String name, String id, String province) {
		super(name, id);
		this.province = province;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
