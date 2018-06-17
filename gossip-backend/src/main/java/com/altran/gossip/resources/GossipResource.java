package com.altran.gossip.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.altran.gossip.entities.Gossip;
import com.codahale.metrics.annotation.Timed;
import com.lankheet.domotics.backend.DatabaseManager;

@Path("/gossip")
@Produces(MediaType.APPLICATION_JSON)
public class GossipResource {

    private DatabaseManager dbManager;
    
    public GossipResource(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    
    @GET
    @Timed
    public List<Gossip> getGossip(@PathParam("name") String name) {
        return dbManager.getGossipByUser(name);
    }
    
    @GET
    @Timed
    public List<Gossip> getGossip(@PathParam("time") Date date) {
        return dbManager.getGossipByDate(date);
    }
    
}
