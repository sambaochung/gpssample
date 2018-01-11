package com.demo.gps.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class TrackPoint extends Position {

	/*
	 * <trkpt lat="42.2208895" lon="-1.4580696"> <ele>315.86</ele>
	 * <time>2017-10-22T09:41:38Z</time> </trkpt>
	 */
	private String ele;
	private String gpsId;

	private Date datetime; // Convert datetime to timestamp

	public String getEle() {
		return ele;
	}

	public void setEle(String ele) {
		this.ele = ele;
	}

	@XmlElement(name = "time")
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date timestamp) {
		this.datetime = timestamp;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}
}
