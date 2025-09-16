package com.example.carlightsapp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistoryLog;
    private HistoryAdapter historyAdapter;
    private List<BulbLogEntry> logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistoryLog = findViewById(R.id.rvHistoryLog);
        rvHistoryLog.setLayoutManager(new LinearLayoutManager(this));

        logList = generateDummyLogEntries();
        historyAdapter = new HistoryAdapter(logList);
        rvHistoryLog.setAdapter(historyAdapter);
    }

    private List<BulbLogEntry> generateDummyLogEntries() {
        List<BulbLogEntry> logs = new ArrayList<>();

        logs.add(new BulbLogEntry(getDate(2025, 9, 18, 23, 30),
                new Bulb("Trunk Light", 35.0, 12.0), 20));

        logs.add(new BulbLogEntry(getDate(2025, 9, 17, 21, 45),
                new Bulb("Front Left Headlight", 55.0, 12.0), 12));

        logs.add(new BulbLogEntry(getDate(2025, 9, 17, 21, 45),
                new Bulb("Front Right Headlight", 55.0, 12.0), 12));

        logs.add(new BulbLogEntry(getDate(2025, 9, 17, 19, 10),
                new Bulb("Rear Left Tail Light", 21.0, 12.0), 15));

        logs.add(new BulbLogEntry(getDate(2025, 9, 17, 19, 10),
                new Bulb("Rear Right Tail Light", 21.0, 12.0), 15));

        logs.add(new BulbLogEntry(getDate(2025, 9, 16, 18, 55),
                new Bulb("Interior Dome Light", 5.0, 12.0), 24));

        logs.add(new BulbLogEntry(getDate(2025, 9, 16, 18, 15),
                new Bulb("License Plate Light", 5.0, 12.0), 5));

        logs.add(new BulbLogEntry(getDate(2025, 9, 16, 8, 31),
                new Bulb("Left Indicator", 21.0, 12.0), 1));

        logs.add(new BulbLogEntry(getDate(2025, 9, 16, 9, 11),
                new Bulb("Right Indicator", 21.0, 12.0), 1));

        logs.add(new BulbLogEntry(getDate(2025, 9, 16, 17, 45),
                new Bulb("Left fog light", 3.0, 12.0), 120));

        logs.add(new BulbLogEntry(getDate(2025, 9, 12, 17, 45),
                new Bulb("Right fog light", 3.0, 12.0), 120));

        return logs;
    }

    private Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        return calendar.getTime();
    }
}