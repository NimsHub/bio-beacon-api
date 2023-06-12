package com.nimshub.biobeacon.mq;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.integration.mqtt.support.MqttHeaders.TOPIC;

@RestController
@RequiredArgsConstructor
public class MessagingController {

    private final MessagingService messagingService;

    @GetMapping("/message/publish")
    public String publish(@RequestBody String msg) throws MqttException {
        messagingService.publish(TOPIC, msg, 0, true);
        return "published";
    }

    @GetMapping("/message/subscribe")
    public String subscribe() throws MqttException {
        messagingService.subscribe(TOPIC);
        return "subscribed";
    }
    @GetMapping("/message/unsubscribe")
    public String unsubscribe() throws MqttException {
        messagingService.unsubscribe(TOPIC);
        return "unsubscribed";
    }
}
