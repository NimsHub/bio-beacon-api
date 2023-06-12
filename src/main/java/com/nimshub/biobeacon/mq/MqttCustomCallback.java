package com.nimshub.biobeacon.mq;

import com.nimshub.biobeacon.exceptions.MqttConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

@RequiredArgsConstructor
@Slf4j
public class MqttCustomCallback implements MqttCallback {

    private final IMqttClient mqttClient;
    @Override
    public void connectionLost(Throwable throwable) {
        log.info("Connection lost...");
        try {
            log.info("trying to reconnect...");
            mqttClient.reconnect();
        } catch (MqttException e) {
            log.info("reconnection failed...");
            throw new MqttConnectionException(e.getMessage());
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        log.info("Message arrived");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Delivery complete");
    }
}
