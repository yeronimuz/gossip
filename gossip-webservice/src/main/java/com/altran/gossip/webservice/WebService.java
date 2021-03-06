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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altran.gossip.webservice.config.WebServiceConfig;
import com.altran.gossip.webservice.health.DatabaseHealthCheck;
import com.altran.gossip.webservice.resources.GossipResource;
import com.altran.gossip.webservice.resources.WebServiceInfo;
import com.altran.gossip.webservice.resources.WebServiceInfoResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * The web service has the following tasks:<BR>
 * <li>It accepts measurements from the message broker
 * <li>It saves the measurements in the database
 * <li>It serves as a resource for the measurements database
 *
 */
public class WebService extends Application<WebServiceConfig> {
    private static final Logger LOG = LoggerFactory.getLogger(WebService.class);

    private WebServiceConfig configuration;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            LOG.error("Missing or wrong arguments");
        } else {
            new WebService().run(args[0], args[1]);
        }
    }

    @Override
    public void initialize(Bootstrap<WebServiceConfig> bootstrap) {
        LOG.info("Altran Gossip web service", "");
    }

    @Override
    public void run(WebServiceConfig configuration, Environment environment) throws Exception {
        this.setConfiguration(configuration);
        DatabaseManager dbManager = new DatabaseManager(configuration.getDatabaseConfig());
//        MqttClientManager mqttClientManager = new MqttClientManager(configuration.getMqttConfig(), dbManager);
        WebServiceInfoResource webServiceInfoResource = new WebServiceInfoResource(new WebServiceInfo());
        GossipResource measurementsResource = new GossipResource(dbManager);
        environment.getApplicationContext().setContextPath("/api");
//        environment.lifecycle().manage(mqttClientManager);
        environment.lifecycle().manage(dbManager);
        environment.jersey().register(webServiceInfoResource);
        environment.jersey().register(measurementsResource);
        environment.healthChecks().register("database", new DatabaseHealthCheck(dbManager));
//        environment.healthChecks().register("mqtt-server", new MqttConnectionHealthCheck(mqttClientManager));
    }

    public WebServiceConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(WebServiceConfig configuration) {
        this.configuration = configuration;
    }
}
