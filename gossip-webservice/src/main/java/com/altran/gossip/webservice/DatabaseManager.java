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

package com.altran.gossip.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altran.gossip.entities.Gossip;
import com.altran.gossip.webservice.config.DatabaseConfig;
import com.altran.gossip.webservice.dao.DaoListener;

import io.dropwizard.lifecycle.Managed;

public class DatabaseManager implements Managed, DaoListener {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseManager.class);

	private static final String PERSISTENCE_UNIT = "gossip-pu";
	private DatabaseConfig dbConfig;
	private EntityManagerFactory emf;
	private EntityManager em;

	public DatabaseManager(DatabaseConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	@Override
	public void start() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", dbConfig.getDriver());
		properties.put("javax.persistence.jdbc.url", dbConfig.getUrl());
		properties.put("javax.persistence.jdbc.user", dbConfig.getUserName());
		properties.put("javax.persistence.jdbc.password", dbConfig.getPassword());

		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
		em = emf.createEntityManager();
	}

	@Override
	public void stop() throws Exception {
		em.close();
		emf.close();
	}

	@Override
	public List<Gossip> getAllGossip() {
		return em.createQuery("SELECT e FROM gossip e").getResultList();
	}
}
