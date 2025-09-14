package com.example.carlightsapp;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfigurationActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBulbs;
    private BulbAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigurationActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewBulbs = findViewById(R.id.recyclerViewBulbs);
        recyclerViewBulbs.setLayoutManager(new LinearLayoutManager(this));

        loadBulbs();

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadBulbs();
    }

    private void loadBulbs() {
        List<Bulb> bulbs = CurrentLightConfig.loadConfiguration(this);

        if (bulbs == null || bulbs.isEmpty()) {
            bulbs = CurrentLightConfig.getDefaultConfiguration();
            CurrentLightConfig.saveConfiguration(this, bulbs);
        }
        if (adapter == null) {
            adapter = new BulbAdapter(bulbs);
            recyclerViewBulbs.setAdapter(adapter);
        } else {
            adapter.updateData(bulbs);
        }
    }
}