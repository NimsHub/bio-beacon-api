package com.nimshub.biobeacon.utils;

import java.util.ArrayList;
import java.util.List;

import static com.nimshub.biobeacon.constants.Constants.COMMA;

public class Converters {
    public List<String> getStringList(String csvString) {

        String[] elements = csvString.split(COMMA);
        List<String> intList = new ArrayList<>();
        for (String element : elements) {
            if (element.equalsIgnoreCase("*")) {
                intList.add(null);
            } else {
                intList.add(element);
            }
        }
        return intList;
    }
}
