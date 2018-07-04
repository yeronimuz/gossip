package com.altran.gossip.backend.dao;

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
	 * A client requests all gossip produced by a specific user
	 * 
	 * @param user
	 *            The user of which the gossip to get
	 * 
	 * @return The gossips from the database.
	 */
	List<Gossip> getGossipByUser(String user);

	/**
	 * Request all gossip produced on or after a specific day.
	 * 
	 * @param date
	 *            The timestamp of the message
	 * @return The gossips from the database.
	 */
	List<Gossip> getGossipByDate(Date date);

}
