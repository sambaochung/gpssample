package com.demo.gps.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gpx", namespace="http://www.topografix.com/GPX/1/1")
@Entity
public class GPS {
	private String userName;
	private String gpxFileName;
	private MetaGPS meta;
	private List<WayPoint> wpt;
	private Track track;
	private String gpsId;
	
	@XmlElement(name="metadata")
	public MetaGPS getMeta() {
		return meta;
	}

	public void setMeta(MetaGPS meta) {
		this.meta = meta;
	}

	public List<WayPoint> getWpt() {
		return wpt;
	}

	public void setWpt(List<WayPoint> wpt) {
		this.wpt = wpt;
	}
	
	@XmlElement(name="trk")
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGpxFileName() {
		return gpxFileName;
	}

	public void setGpxFileName(String gpxFileName) {
		this.gpxFileName = gpxFileName;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

}


