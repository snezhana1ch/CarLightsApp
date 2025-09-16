package com.example.carlightsapp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BulbLogEntry {
    private Date timestamp;
    private Bulb bulb;
    private int durationMinutes;

    public BulbLogEntry(Date timestamp, Bulb bulb, int durationMinutes) {
        this.timestamp = timestamp;
        this.bulb = bulb;
        this.durationMinutes = durationMinutes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Bulb getBulb() {
        return bulb;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return "[" + sdf.format(timestamp) + "]";
    }

    public String getFormattedBulbInfo() {
        return bulb.getPosition() + " " + bulb.getWatt() + "W " + bulb.getVoltage() + "V";
    }

    public String getFormattedDuration() {
        return "On duration: " + durationMinutes + " min";
    }
}