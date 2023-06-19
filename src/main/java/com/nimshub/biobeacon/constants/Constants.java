package com.nimshub.biobeacon.constants;

public class Constants {
    // creating a private constructor to hide the public implicit one
    private Constants() {
        throw new IllegalStateException("Constants class");
    }
    public static final String NO_DATA = "*";
    public static final String COMMA = ",";
    public static final Integer CHUNK_SIZE = 4;
}
