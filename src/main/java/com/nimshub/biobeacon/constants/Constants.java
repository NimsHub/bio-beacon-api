package com.nimshub.biobeacon.constants;

public class Constants {
    public static final String NO_DATA = "*";
    public static final String BASH = "bash";
    public static final String COMMAND = "-c";
    public static final String COMMA = ",";
    public static final Integer CHUNK_SIZE = 4;
    public static final String CYCLING = "0";
    public static final String PUSH_UP = "1";
    public static final String RUNNING = "2";
    public static final String SQUAT = "3";
    public static final String TABLE_TENNIS = "4";
    public static final String WALKING = "5";
    public static final Integer SAMPLING_SIZE = 10;
    // creating a private constructor to hide the public implicit one
    private Constants() {
        throw new IllegalStateException("Constants class");
    }
}
