package com.demo.gps.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.demo.gps.demo.model.TrackPoint;

@Component
public class TrackPointRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<TrackPoint> findByUser(String userName) {
		String sql = "select t from TrackPoint t, GPS g where t.gpsId = g.id and g.userName=?";
		List<TrackPoint> tracks = jdbcTemplate.query(sql, new Object[] { userName },
				new BeanPropertyRowMapper<TrackPoint>(TrackPoint.class));
		return tracks;
	}

	public List<TrackPoint> findByUserAndMap(String userName, String fileName) {
		String sql = "select t from TrackPoint t, GPS g where t.gpsId = g.id and g.userName=? and g.fileName=?";
		List<TrackPoint> tracks = jdbcTemplate.query(sql, new Object[] { userName, fileName },
				new BeanPropertyRowMapper<TrackPoint>(TrackPoint.class));
		return tracks;
	}
	
	public List<TrackPoint> findByMapId(String gpsId) {
		String sql = "select * from TrackPoint t, GPS g where t.gpsId = g.id and g.id=?";
		List<TrackPoint> tracks = jdbcTemplate.query(sql, new Object[] { gpsId },
				new BeanPropertyRowMapper<TrackPoint>(TrackPoint.class));
		return tracks;
	}

	public int insert(TrackPoint trackPoint) {
		return jdbcTemplate.update(
				"insert into TrackPoint (gpsId, latitude, longitude, ele, datetime) " + "values(?,  ?, ?, ?,?)",
				new Object[] { trackPoint.getGpsId(), trackPoint.getLatitude(), trackPoint.getLongitude(),
						trackPoint.getEle(), trackPoint.getDatetime()});
	}
}
