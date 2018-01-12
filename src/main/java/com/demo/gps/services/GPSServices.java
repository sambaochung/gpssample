package com.demo.gps.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.demo.gps.dao.GPSRepository;
import com.demo.gps.dao.TrackPointRepository;
import com.demo.gps.dao.UserRepository;
import com.demo.gps.demo.model.GPS;
import com.demo.gps.demo.model.Track;
import com.demo.gps.demo.model.TrackPoint;
import com.demo.gps.demo.model.Trackegment;
import com.demo.gps.demo.model.User;

@Component
public class GPSServices {

	@Autowired
	private GPSRepository repository;

	@Autowired
	private TrackPointRepository trackPointRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GPS> findListGPSByUser(String userName) {
		return repository.findListGPSByUser(userName);
	}

	public List<TrackPoint> loadTrackPointByMapId(String gpsId) {
		return trackPointRepository.findByMapId(gpsId);
	}

	public GPS createMap(MultipartFile file, String userName) throws IOException {
		GPS gps = null;
		try {
			gps = loadGPSData(file.getInputStream());
			gps.setGpxFileName(file.getOriginalFilename());
			gps.setUserName(userName);
		} catch (IOException e) {
			throw e;
		}
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		gps.setGpsId(randomUUIDString);
		repository.insert(gps);
		return gps;
	}

	public User getLatestPositionOnMap(GPS gps) {
		User user = new User();
		user.setUserName(gps.getUserName());
		gps.getTrack().getTrackegments().forEach(trackegment -> {
			trackegment.getTrackPoints()
					.sort((trackPoint1, trackPoint2) -> trackPoint1.getDatetime().compareTo(trackPoint2.getDatetime()));
			user.setLatestLatitude(
					trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() - 1).getLatitude());
			user.setLatestLongitude(
					trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() - 1).getLongitude());
			user.setLatestUpdated(
					trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() - 1).getDatetime());
		});
		User existUser = userRepository.findByUser(user.getUserName());
		if (existUser != null) {
			if (existUser.getLatestUpdated().compareTo(user.getLatestUpdated()) < 0) {
				userRepository.update(user);
			}
		} else {
			userRepository.insert(user);
		}
		return user;

	}

	public void addTrackPointForMap(GPS gps) {
		gps.getTrack().getTrackegments().forEach(trackegment -> {
			trackegment.getTrackPoints().forEach(trackPoint -> {
				trackPoint.setGpsId(gps.getGpsId());
				addTrackPoint(trackPoint);
			});
		});
	}

	public void addAllTrackPoint(List<TrackPoint> trackPoints, GPS gps) {
		if (gps != null) {
			Track track = new Track();
			Trackegment segment = new Trackegment();
			segment.setTrackPoints(trackPoints);
			track.addTrackSegments(segment);
			gps.setTrack(track);
		}
	}

	//This is for testing purpose
	public void createTestData() {
		GPS gps = null;
		File file = new File("E:\\sample.gpx");
		try {
			InputStream in = new FileInputStream(file);
			gps = loadGPSData(in);
			gps.setGpxFileName("sample.gpx");
			gps.setUserName("Sam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		gps.setGpsId(randomUUIDString);
		repository.insert(gps);
		addTrackPointForMap(gps);
		getLatestPositionOnMap(gps);
	}

	private void addTrackPoint(TrackPoint trackPoint) {
		trackPointRepository.insert(trackPoint);
	}

	private GPS loadGPSData(InputStream is) throws IOException {
		GPS gps = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(GPS.class);

			Unmarshaller unmarshaller = jc.createUnmarshaller();
			gps = (GPS) unmarshaller.unmarshal(new StreamSource(is));
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return gps;
	}
}
