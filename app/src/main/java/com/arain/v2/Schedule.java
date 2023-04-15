package com.arain.v2;

import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    // Add other days of the week fields as needed

    // Constructors, getters, and setters as needed
    // Example:
    public Schedule(int startHour, int startMinute, int endHour, int endMinute, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public Schedule(int year, int month, int day, int hour, int minute, int duration) {
    }

    // Methods as needed, such as to convert Schedule object to a Map for Firebase saving
    // Example:
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("startHour", startHour);
        result.put("startMinute", startMinute);
        result.put("endHour", endHour);
        result.put("endMinute", endMinute);
        result.put("sunday", sunday);
        result.put("monday", monday);
        result.put("tuesday", tuesday);
        result.put("wednesday", wednesday);
        result.put("thursday", thursday);
        result.put("friday", friday);
        result.put("saturday", saturday);
        return result;
    }
}

