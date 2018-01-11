package com.demo.gps.demo.controller;

import java.io.*;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.demo.gps.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.gps.dao.GPSRepository;
import com.demo.gps.dao.TrackPointRepository;
import com.demo.gps.dao.UserRepository;
import com.demo.gps.dao.WayPointRepository;

@RestController
@RequestMapping("/gps")
public class GPSController {
	@RequestMapping("/get")
	public String index() {
		createTestData();
		return "Greetings from Spring Boot!2333";
	}

	@Autowired
	private GPSRepository repository;

	@Autowired
	private TrackPointRepository trackPointRepository;

	@Autowired
	private WayPointRepository wayPointRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam String userName) {
		GPS gps = null;
		if (!file.isEmpty()) {
			try {
				gps = createMap(file, userName);
				addTrackPointForMap(gps);
				getLatestPositionOnMap(gps);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			return "success";
		} else {
			return "failed";
		}
	}

	@RequestMapping(value="/loadUserMap/{userName}", method=RequestMethod.GET)
	public List<GPS> loadMapByUser(String userName) {
		List<GPS> gpsList = repository.findListGPSByUser(userName);
		gpsList.forEach(gps -> {List<TrackPoint> trackPoints = trackPointRepository.findByMapId(gps.getGpsId());
						addAllTrackPoint(trackPoints, gps);});
		return gpsList;
	}
	private GPS createMap(MultipartFile file, String userName) throws IOException {
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
	
	private User getLatestPositionOnMap(GPS gps) {
		User user = new User();
		user.setUserName(gps.getUserName());
		gps.getTrack().getTrackegments().forEach(trackegment -> {
			trackegment.getTrackPoints().sort((trackPoint1, trackPoint2)->trackPoint1.getDatetime().compareTo(trackPoint2.getDatetime()));
			user.setLatestLatitude(trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() -1).getLatitude());
			user.setLatestLongitude(trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() -1).getLongitude());
			user.setLatestUpdated(trackegment.getTrackPoints().get(trackegment.getTrackPoints().size() -1).getDatetime());
		});
		User existUser = userRepository.findByUser(user.getUserName());
		if(existUser != null) {
			if(existUser.getLatestUpdated().compareTo(user.getLatestUpdated()) < 0) {
				userRepository.update(user);
			}
		} else {
			userRepository.insert(user);
		}
		return user;
		
	}

	private void addTrackPointForMap(GPS gps) {
		gps.getTrack().getTrackegments().forEach(trackegment -> {
			trackegment.getTrackPoints().forEach(trackPoint -> {
				trackPoint.setGpsId(gps.getGpsId());
				addTrackPoint(trackPoint);
			});
		});
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

	private void addAllTrackPoint(List<TrackPoint> trackPoints, GPS gps) {
		if(gps!= null) {
			Track track = new Track();
			Trackegment segment = new Trackegment();
			segment.setTrackPoints(trackPoints);
			track.addTrackSegments(segment);
			gps.setTrack(track);
		}
	}

	private void createTestData() {
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
}
