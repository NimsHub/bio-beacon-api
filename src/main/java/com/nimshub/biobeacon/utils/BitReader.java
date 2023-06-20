package com.nimshub.biobeacon.utils;

import com.nimshub.biobeacon.exceptions.ByteCodeException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static com.nimshub.biobeacon.constants.Constants.*;

/**
 * This class provides functionality for bit manipulation and read motion data
 */
@Service
public class BitReader {
    /**
     * This method reads 4 bytes and returns acceleration data
     *
     * @param accelerationBytes : byte[]
     * @return Acceleration
     */
    private Acceleration getAcceleration(byte[] accelerationBytes) {

        boolean isDataAvailable = ((accelerationBytes[0] & 0x80) >> 7) == 1;

        if (isDataAvailable)
            return new Acceleration(NO_DATA);

        int z = ((accelerationBytes[2] & 0x03) << 8) | (accelerationBytes[3] & 0xFF);
        int y = ((accelerationBytes[1] & 0x0F) << 6) | ((accelerationBytes[2] & 0xFC) >> 2);
        int x = ((accelerationBytes[0] & 0x3F) << 4) | ((accelerationBytes[1] & 0xF0) >> 4);

        return new Acceleration(Integer.toString(x), Integer.toString(y), Integer.toString(z));
    }

    /**
     * This method creates a motion data map for all the modules
     *
     * @param data    : byte[]
     * @param modules : Integer[]
     * @return Map<Integer, String>
     */
    private Map<Integer, String> createMotionData(byte[] data, Integer[] modules) {

        if (data.length % CHUNK_SIZE != 0) throw new ByteCodeException("Data error: missing or redundant data");

        Map<Integer, String> motionData = new HashMap<>();

        int numberOfModules = modules.length;

        for (int j = 0; j < numberOfModules; j++) {
            StringJoiner csv = new StringJoiner(COMMA);

            for (int i = j * CHUNK_SIZE; i < data.length; i += CHUNK_SIZE * numberOfModules) {

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
            motionData.put(modules[j], csv.toString());
        }
        return motionData;
    }

    /**
     * This method returns the modules as an Integer array
     *
     * @param modules : String
     * @return Integer []
     */
    public Integer[] getModules(String modules) {

        String[] parts = modules.split(COMMA);
        Integer[] array = new Integer[parts.length];

        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

    /**
     * This method retrieves the motion data map by calling createMotionData method
     *
     * @param modules    : String
     * @param motionData : String
     * @return Map<Integer, String>
     */
    public Map<Integer, String> getMotionData(String modules, String motionData) {

        return createMotionData(
                Base64
                        .getDecoder()
                        .decode(motionData), getModules(modules));
    }
}
