package com.nimshub.biobeacon.utils;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitReaderTest {

    String vitalData = "127,255,255,255,31,255,255,255,159,255,255,255,159,255,255,255,159,255,255,255,159,255,255,255";
    byte[] sampleBytes = {(byte) 0b01111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b00011111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111
            , (byte) 0b10011111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b10011111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111
            , (byte) 0b10011111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b10011111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111};

    @Test
    void createVitalRecord() {

        String out = IntStream.range(0, sampleBytes.length)
                .mapToObj(i -> Integer.toString(Byte.toUnsignedInt(sampleBytes[i])))
                .collect(Collectors.joining(","));

        assertEquals(vitalData, out);
    }

}