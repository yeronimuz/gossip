package com.altran.gossip.backend.health;

import java.util.Date;
import java.util.List;

import com.altran.gossip.backend.DatabaseManager;
import com.altran.gossip.entities.Gossip;
import com.codahale.metrics.health.HealthCheck;

public class DatabaseHealthCheck extends HealthCheck {
    private static final long ONE_DAY = 24 * 60 *60 * 1000;
	private DatabaseManager dbManager;

    public DatabaseHealthCheck(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected Result check() {
        Result result = Result.unhealthy("Nothing retrieved from database");
        List<Gossip> measList = dbManager.getGossipByDate(new Date(System.currentTimeMillis() - ONE_DAY));
        if (measList.size() > 0) {
            result =  Result.healthy("Healthy");
        }
        return result;
    }
}
