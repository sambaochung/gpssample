package com.demo.gps.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Track {
	List<Trackegment> trackegments;

	@XmlElement(name="trkseg")
	public List<Trackegment> getTrackegments() {
		return trackegments;
	}

	public void setTrackegments(List<Trackegment> trackegments) {
		this.trackegments = trackegments;
	}

	public void addTrackSegments(Trackegment trackegment) {
		if(trackegments == null) {
			trackegments = new ArrayList<Trackegment>();
		}
		trackegments.add(trackegment);
	}
	
}
