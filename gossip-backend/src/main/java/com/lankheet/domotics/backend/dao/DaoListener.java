package com.lankheet.domotics.backend.dao;

import java.util.Date;
import java.util.List;

import com.altran.gossip.entities.Gossip;

public interface DaoListener {

	/**
	 * A new rumor has arrived and will be stored. We call it rumor here, because we
	 * don't know whether it's gossip or the truth.
	 * 
	 * @param gossip
	 *            The gossip that has arrived.
	 */
	void saveNewRumor(Gossip gossip);

	/**
	 * A client requests all measurements by sensor from the database
	 * 
	 * @param sensorId
	 *            The sensorId of which the measurements to get
	 * 
	 * @return The measurements from the database.
	 */
	List<Gossip> getGossipByUser(String user);

	/**
	 * Request all measurements of a specific type for a specific sensor.
	 * 
	 * @param sensorId
	 *            The originated sensor
	 * @param type
	 *            The message type
	 * @return The measurements that comply to the input criteria
	 */
	List<Gossip> getGossipByDate(Date date);

}
