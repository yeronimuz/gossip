/**
 * 
 */
package com.altran.gossip.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The rumor.
 */
@Entity(name = "gossips")
public class Gossip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonProperty
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date time;

	@JsonProperty
	private String blabber;

	@JsonProperty
	private String message;

	public Gossip() {
		// Both JPA and Jaxon need this
	}

	public Gossip(Date time, String blabber, String message) {
		this.time = time;
		this.blabber = blabber;
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getBlabber() {
		return blabber;
	}

	public void setBlabber(String blabber) {
		this.blabber = blabber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Gossip [time=" + time + ", blabber=" + blabber + ", message=" + message + "]";
	}
}
