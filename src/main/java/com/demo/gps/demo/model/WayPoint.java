package com.demo.gps.demo.model;

public class WayPoint extends Position {
	/*XML format for Wpt
	 *
	 <wpt lat="42.2205377" lon="-1.4564538">
		<name>Sorteamos por arriba</name>
		<sym>/static/wpt/Waypoint</sym>
		</wpt>
	 */
	
	private String name;
	private String sym;
	private String gpsId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public String getGpsId() {
		return gpsId;
	}
	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}
}
