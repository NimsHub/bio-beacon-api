package com.nimshub.biobeacon.utils;

import org.springframework.lang.NonNull;

public class Acceleration {
    public Acceleration(@NonNull String x,@NonNull String y,@NonNull String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private String x;
    private String y;
    private String z;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + x + '\'' +
                ",'" + y + '\'' +
                ",'" + z + '\'' +
                '}';
    }
}

