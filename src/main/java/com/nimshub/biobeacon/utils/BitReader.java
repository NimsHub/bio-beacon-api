package com.nimshub.biobeacon.utils;

import com.nimshub.biobeacon.exceptions.ByteCodeException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.StringJoiner;

import static com.nimshub.biobeacon.constants.Constants.*;

@Service
public class BitReader {
    private Acceleration getAcceleration(byte[] accelerationBytes) {

        boolean isDataAvailable = ((accelerationBytes[0] & 0x80) >> 7) == 0;

        if (isDataAvailable)
            return new Acceleration(NO_DATA);

        int z = ((accelerationBytes[2] & 0x03) << 8) | (accelerationBytes[3] & 0xFF);
        int y = ((accelerationBytes[1] & 0x0F) << 6) | ((accelerationBytes[2] & 0xFC) >> 2);
        int x = ((accelerationBytes[0] & 0x3F) << 4) | ((accelerationBytes[1] & 0xF0) >> 4);

        return new Acceleration(Integer.toString(x), Integer.toString(y), Integer.toString(z));
    }


    private String createMotionData(byte[] data) {

        if (data.length % 4 != 0) throw new ByteCodeException("Data error: missing or redundant data");

        StringJoiner csv = new StringJoiner(COMMA);

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

    public String getMotionData(String motionData) {
        return createMotionData(
                Base64
                        .getDecoder()
                        .decode(motionData));
    }
}
