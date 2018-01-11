package com.demo.gps.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Trackegment {
	
	List<TrackPoint> trackPoints;

	public void setTrackPoints(List<TrackPoint> trackPoints) {
		this.trackPoints = trackPoints;
	}

	@XmlElement(name="trkpt")
	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}

	public void addTrackPoints(TrackPoint trackPoint) {
		if(trackPoints == null) {
			trackPoints = new ArrayList<TrackPoint>();
		}
		trackPoints.add(trackPoint);
	}
	
}
