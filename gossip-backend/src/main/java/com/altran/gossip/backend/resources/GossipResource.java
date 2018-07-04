package com.altran.gossip.backend.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.altran.gossip.backend.DatabaseManager;
import com.altran.gossip.backend.dao.DaoListener;
import com.altran.gossip.entities.Gossip;
import com.codahale.metrics.annotation.Timed;

@Path("/gossip")
public class GossipResource {

	private DaoListener daoListener;

	public GossipResource(DatabaseManager dbManager) {
		this.daoListener = dbManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public List<Gossip> getGossipByUser(@QueryParam("blabber") String blabber) {
		return daoListener.getGossipByUser(blabber);
	}

	@POST
	@Timed
	public void putNewGossip(@QueryParam("blabber") String blabber, @QueryParam("message") String message) {
		daoListener.saveNewRumor(new Gossip(new Date(), blabber, message));
	}

}
