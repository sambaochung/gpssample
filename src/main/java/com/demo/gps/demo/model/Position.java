package com.demo.gps.demo.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Position {

	
	private double latitude;
	private double longitude;
	
	@XmlAttribute(name="lat")
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@XmlAttribute(name="lon")
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
