package com.nimshub.biobeacon.exceptions;

public class MqttConnectionException extends RuntimeException{
        public MqttConnectionException(String message) {
            super(message);
        }
}
