package com.demo.gps.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.demo.gps.demo.model.WayPoint;

@Component
public class WayPointRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class WayPointRowMapper implements RowMapper<WayPoint> {
		public WayPointRowMapper(Class<WayPoint> class1) {
		}

		@Override
		public WayPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
			WayPoint wayPoint = new WayPoint();
			wayPoint.setGpsId(rs.getString("gpsId"));
			wayPoint.setLatitude(rs.getDouble("latitude"));
			wayPoint.setLongitude(rs.getDouble("longitude"));
			wayPoint.setSym(rs.getString("sym"));
			wayPoint.setName(rs.getString("name"));
			return wayPoint;
		}
	}

	public List<WayPoint> findByUser(String gpsId) {
		return jdbcTemplate.query("select * from WayPoint where gpsId=?", new Object[] { gpsId },
				new WayPointRowMapper(WayPoint.class));
	}

	public int insert(WayPoint wayPoint) {
		return jdbcTemplate.update(
				"insert into WayPoint (gpsId, latitude, longitude,sym,name) " + "values(?,  ?, ?, ?, ?)",
				new Object[] { wayPoint.getGpsId(), wayPoint.getLatitude(), wayPoint.getLongitude(), wayPoint.getSym(),
						wayPoint.getName() });
	}
}
