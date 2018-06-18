/**
 * 
 */
package com.altran.gossip.backend;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.BeforeClass;

import com.altran.gossip.backend.config.DatabaseConfig;
import com.altran.gossip.entities.Gossip;
import com.altran.gossip.testutils.TestUtils;

/**
 * Test for @link {@link BackendService}
 * A database is required
 * An mqtt server is required
 */
public class BackendServiceTest {
    private static final String PERSISTENCE_UNIT = "meas-pu";

    private static BackendService beService = new BackendService();
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static MqttClient mqttClient;

    @BeforeClass
    public static void doSetup() throws Exception {
        // Start service
        beService.run("server", "src/test/resources/application.yml");

        DatabaseConfig dbConfig = beService.getConfiguration().getDatabaseConfig();
        // Overwrite production persistence unit settings with test values
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.driver", dbConfig.getDriver());
        properties.put("javax.persistence.jdbc.url", dbConfig.getUrl());
        properties.put("javax.persistence.jdbc.user", dbConfig.getUserName());
        properties.put("javax.persistence.jdbc.password", dbConfig.getPassword());
        
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
        em = emf.createEntityManager();
        // Prepare database
        Gossip gossip = new Gossip(new Date(), "itsme youknow", "it's fake news");
        em.getTransaction().begin();
        em.persist(gossip);
        em.getTransaction().commit();
        mqttClient = TestUtils.createMqttClientConnection();
    }

    // @Test (disabled; it's a system test, not a unit test)
    public void testEndToEndTest() throws Exception {
        Gossip gossip = new Gossip(new Date(), "itsme youknow", "it's fake news");
        TestUtils.sendMqttGossip(mqttClient, gossip);
        assertTrue(mqttClient.isConnected());
        // Give the service some time to finish
        Thread.sleep(2000);
    }
}
