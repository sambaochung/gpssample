package com.demo.gps.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.gps.demo.model.GPS;
import com.demo.gps.demo.model.TrackPoint;
import com.demo.gps.services.GPSServices;

@RestController
@RequestMapping("/gps")
public class GPSController {

	@Autowired
	private GPSServices gpsServices;

	@RequestMapping("/get")
	public String index() {
		//gpsServices.createTestData(); //This is for testing purpose
		return "Greetings from Spring Boot!2333";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam String userName) {
		GPS gps = null;
		if (!file.isEmpty()) {
			try {
				gps = gpsServices.createMap(file, userName);
				gpsServices.addTrackPointForMap(gps);
				gpsServices.getLatestPositionOnMap(gps);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			return "success";
		} else {
			return "failed";
		}
	}

	@RequestMapping(value = "/loadUserMap/{userName}", method = RequestMethod.GET)
	public List<GPS> loadMapByUser(@PathVariable String userName) {
		List<GPS> gpsList = gpsServices.findListGPSByUser(userName);
		gpsList.forEach(gps -> {
			List<TrackPoint> trackPoints = gpsServices.loadTrackPointByMapId(gps.getGpsId());
			gpsServices.addAllTrackPoint(trackPoints, gps);
		});
		return gpsList;
	}

}
