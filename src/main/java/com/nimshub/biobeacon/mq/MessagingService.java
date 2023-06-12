package com.nimshub.biobeacon.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessagingService {
    private String message = "data ";
    private final IMqttClient mqttClient;

    public void publish(final String topic, final String payload, int qos, boolean retained)
            throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        mqttClient.publish(topic, mqttMessage);

    }

    public void subscribe(final String topic) throws MqttException {

        log.info("receiving...");

        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            message = message + new String(msg.getPayload(), StandardCharsets.UTF_8);
            log.info(message);
        });
    }
    public void unsubscribe(String topic) throws MqttException {
        mqttClient.unsubscribe(topic);
        log.info("successfully unsubscribed from {}",topic);
    }
}
