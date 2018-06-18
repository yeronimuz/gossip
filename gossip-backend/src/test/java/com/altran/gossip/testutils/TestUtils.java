package com.altran.gossip.testutils;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altran.gossip.entities.Gossip;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtils {
	private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);
	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	public static void sendMqttMeasurements(MqttClient mqttClient, List<Gossip> gossipList) throws Exception {
		gossipList.forEach((gossip) -> {
			LOG.info(gossip.toString());
			try {
				sendMqttGossip(mqttClient, gossip);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("Could not send Gossip around");
			}
		});
	}

	public static void sendMqttGossip(MqttClient mqttClient, Gossip gossip) throws Exception {
		MqttMessage msg = new MqttMessage();
		msg.setPayload(mapper.writeValueAsString(gossip).getBytes());
		mqttClient.publish("test", msg);
	}

	public static MqttClient createMqttClientConnection() throws MqttSecurityException, MqttException {
		MqttClient mqttClient = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setConnectionTimeout(60);
		options.setKeepAliveInterval(60);
		options.setUserName("testuser");
		options.setPassword("testpassword".toCharArray());
		mqttClient.connect(options);
		return mqttClient;
	}
}
