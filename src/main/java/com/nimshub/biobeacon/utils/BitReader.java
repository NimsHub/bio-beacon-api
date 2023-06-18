package com.nimshub.biobeacon.utils;

import com.nimshub.biobeacon.exceptions.ByteCodeException;

import java.util.StringJoiner;

public class BitReader {
    private Acceleration getAcceleration(byte[] accelerationBytes) {

        boolean isDataAvailable = ((accelerationBytes[0] & 0x80) >> 7) == 0;

        if (isDataAvailable)
            return new Acceleration("*", "*", "*");

        int z = ((accelerationBytes[2] & 0x0B) << 8) | (accelerationBytes[3] & 0xFF);
        int y = ((accelerationBytes[1] & 0x0F) << 6) | ((accelerationBytes[2] & 0xFC) >> 2);
        int x = ((accelerationBytes[0] & 0x3F) << 4) | ((accelerationBytes[1] & 0xF0) >> 4);

        return new Acceleration(Integer.toString(x), Integer.toString(y), Integer.toString(z));
    }


    public String getMotionData(byte[] data) {

        if (data.length % 4 != 0) throw new ByteCodeException("Data error: missing or redundant data");

        StringJoiner csv = new StringJoiner(",");

        for (int i = 0; i < data.length; i += 4) {
            byte[] accelerationBytes = {
                    data[i],
                    data[i + 1],
                    data[i + 2],
                    data[i + 3]
            };

            Acceleration acceleration = getAcceleration(accelerationBytes);

            csv.add(acceleration.getX());
            csv.add(acceleration.getY());
            csv.add(acceleration.getZ());
        }
        return csv.toString();
    }
}
