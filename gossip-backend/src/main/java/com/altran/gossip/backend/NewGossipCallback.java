package com.altran.gossip.backend;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altran.gossip.backend.dao.DaoListener;
import com.altran.gossip.entities.Gossip;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class NewGossipCallback implements MqttCallback {
	private static final Logger LOG = LoggerFactory.getLogger(NewGossipCallback.class);
	private MqttClientManager mqttClientManager;
	private DaoListener daoListener;

	public NewGossipCallback(MqttClientManager mqttClientManager, DaoListener dao) {
		this.mqttClientManager = mqttClientManager;
		this.daoListener = dao;
	}

	@Override
	public void connectionLost(Throwable cause) {
		LOG.warn("Connection lost: {}", cause.getMessage());
		try {
			mqttClientManager.getClient().reconnect();
		} catch (MqttException e) {
			LOG.error("Could not reconnect: {}", e.getMessage());
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		LOG.info("Topic: {}, message: {}", topic, message.toString());
		String payload = new String(message.getPayload());
		LOG.info("Payload: {}", payload);
		Gossip gossip = null;
		try {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
					.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			gossip = mapper.readValue(payload, Gossip.class);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
			return;
		}
		LOG.info("Starting store action");
		daoListener.saveNewRumor(gossip);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		LOG.info("Delivery complete: {}", token);
	}
}
