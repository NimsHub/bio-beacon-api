package com.nimshub.biobeacon.mq;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttClientConfig {
    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    @Bean
    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
                                  @Value("${mqtt.hostname}") String hostname,
                                  @Value("${mqtt.port}") int port) throws MqttException {

        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);

        mqttClient.connect(mqttConnectOptions());
        mqttClient.setCallback(new MqttCustomCallback(mqttClient));
        return mqttClient;
    }
}
