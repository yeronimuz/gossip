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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The rumor.
 */
@Entity(name = "gossip")
public class Gossip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonProperty
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date time;

	@JsonProperty
	private String name;

	@JsonProperty
	private String message;

	public Gossip() {
		// Both JPA and Jaxon need this
	}

	public Gossip(Date time, String name, String message) {
		this.time = time;
		this.name = name;
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Gossip [time=" + time + ", name=" + name + ", message=" + message + "]";
	}
}
