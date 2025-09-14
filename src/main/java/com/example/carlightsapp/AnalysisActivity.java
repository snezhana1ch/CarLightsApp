package com.example.carlightsapp;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.widget.*;
import java.util.*;

public class AnalysisActivity extends AppCompatActivity {

    private static final double BATTERY_CAPACITY_AH = 70.0;
    private static final double BATTERY_VOLTAGE = 12.0;
    private static final double MIN_VOLTAGE = 11.0;

    private TextView textRemainingHours;
    private Spinner spinnerBulbs;
    private List<Bulb> allBulbs;
    private List<Bulb> activeBulbs;
    private BulbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        textRemainingHours = findViewById(R.id.textRemainingHours);
        spinnerBulbs = findViewById(R.id.spinnerBulbs);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewActiveBulbs);
        Button btnAddBulb = findViewById(R.id.btnAddBulb);

        allBulbs = CurrentLightConfig.loadConfiguration(this);
        activeBulbs = new ArrayList<>();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                getBulbNames(allBulbs));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulbs.setAdapter(spinnerAdapter);

        adapter = new BulbAdapter(activeBulbs, true, position -> {
            activeBulbs.remove(position);
            adapter.notifyItemRemoved(position);
            updateBatteryInfo();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAddBulb.setOnClickListener(v -> {
            int selectedIndex = spinnerBulbs.getSelectedItemPosition();
            if (selectedIndex >= 0) {
                Bulb selected = allBulbs.get(selectedIndex);
                activeBulbs.add(selected);
                adapter.notifyItemInserted(activeBulbs.size() - 1);
                updateBatteryInfo();
            }
        });
    }

    private List<String> getBulbNames(List<Bulb> bulbs) {
        List<String> names = new ArrayList<>();
        for (Bulb b : bulbs) {
            names.add(b.getPosition() + " (" + b.getWatt() + "W)");
        }
        return names;
    }
    private void updateBatteryInfo() {
        double totalCurrent = 0;
        for (Bulb b : activeBulbs) {
            totalCurrent += b.getCurrentInAmps(); // I = P / U
        }

        double usableCapacityAh = BATTERY_CAPACITY_AH * ((BATTERY_VOLTAGE - MIN_VOLTAGE) / BATTERY_VOLTAGE);
        double hoursRemaining = (totalCurrent > 0) ? usableCapacityAh / totalCurrent : 0;

        textRemainingHours.setText(String.format(Locale.getDefault(), "Estimated runtime: %.2f hours", hoursRemaining));
    }
}
