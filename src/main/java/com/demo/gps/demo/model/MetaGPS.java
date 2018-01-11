package com.demo.gps.demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import com.demo.gps.demo.model.Link;

public class MetaGPS {
	private String name;
	private String desc;
	private String author;
	private Date date;
	private Link link;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@XmlElement(name="time")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@XmlElement(name="link")
	public Link getLink() {
		return link;
	}
	public void setLink(Link link) {
		this.link = link;
	}
}