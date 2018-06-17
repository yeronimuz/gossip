/**
 * MIT License
 * 
 * Copyright (c) 2017 Lankheet Software and System Solutions
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.altran.gossip.webservice.health;

import java.util.List;

import com.altran.gossip.entities.Gossip;
import com.altran.gossip.webservice.DatabaseManager;
import com.codahale.metrics.health.HealthCheck;

/**
 * Healthcheck for the database.
 */
public class DatabaseHealthCheck extends HealthCheck {
	private DatabaseManager dbManager;

	/**
	 * Constructor.
	 * 
	 * @param dbManager
	 *            The DatabaseManager that is consulted.
	 */
	public DatabaseHealthCheck(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	protected Result check() {
		Result result = Result.unhealthy("Nothing retrieved from database");
		List<Gossip> gossipList = dbManager.getAllGossip();
		if (gossipList != null) {
			result = Result.healthy("Healthy; " + gossipList.size() + " gossips");
		}
		return result;
	}
}
