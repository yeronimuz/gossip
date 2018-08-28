package com.altran.gossip.client;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.altran.gossip.entities.Gossip;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GossipClient {
    private MqttClient mqttClient;
    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static void main(String[] args) throws JsonProcessingException, MqttPersistenceException, MqttException  {
		GossipClient gossipClient = new GossipClient();
		gossipClient.sendARumor("Suzy from first Floor", "Stop sending fake news!");
	}
    
    public void sendARumor(String blabbist, String rumor) throws JsonProcessingException, MqttPersistenceException, MqttException {
		Gossip gossip = new Gossip(new Date(), blabbist, rumor);
        MqttMessage message = new MqttMessage();
        message.setPayload(mapper.writeValueAsString(gossip).getBytes());
        mqttClient.publish("gossip", message);
    }
}