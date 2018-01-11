package com.demo.gps.demo.model;

import java.util.Date;

public class User {
	private String userName;
	private double latestLatitude;
	private double latestLongitude;
	private Date latestUpdated;
	public double getLatestLongitude() {
		return latestLongitude;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getLatestLatitude() {
		return latestLatitude;
	}
	public void setLatestLatitude(double latestLatitude) {
		this.latestLatitude = latestLatitude;
	}
	public void setLatestLongitude(double latestLongitude) {
		this.latestLongitude = latestLongitude;
	}
	public Date getLatestUpdated() {
		return latestUpdated;
	}
	public void setLatestUpdated(Date latestUpdated) {
		this.latestUpdated = latestUpdated;
	}
}
