package com.demo.gps.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

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
import com.demo.gps.demo.model.GPS;
import com.demo.gps.demo.model.TrackPoint;
import com.demo.gps.demo.model.User;

@RestController
@RequestMapping("/gps")
public class GPSController {
	@RequestMapping("/get")
	public String index() {

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

	public void addTrackPointForMap(GPS gps) {
		gps.getTrack().getTrackegments().forEach(trackegment -> {
			trackegment.getTrackPoints().forEach(trackPoint -> {
				trackPoint.setGpsId(gps.getGpsId());
				addTrackPoint(trackPoint);
			});
		});
	}

	public void addTrackPoint(TrackPoint trackPoint) {
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
