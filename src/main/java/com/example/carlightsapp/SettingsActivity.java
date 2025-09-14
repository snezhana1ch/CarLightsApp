package com.example.carlightsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private List<Bulb> tempBulbs;
    private BulbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Бутони Cancel / Apply
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> finish());

        Button btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(v -> {
            CurrentLightConfig.saveConfiguration(this, tempBulbs);
            finish();
        });

        // Зареждаме копие от файла
        tempBulbs = new ArrayList<>(CurrentLightConfig.loadConfiguration(this));
        if (tempBulbs.isEmpty()) {
            tempBulbs = CurrentLightConfig.getDefaultConfiguration();
        }

        // RecyclerView + Adapter
        RecyclerView recyclerViewBulbs = findViewById(R.id.recyclerViewBulbs);
        recyclerViewBulbs.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BulbAdapter(tempBulbs, true, position -> {
            if (tempBulbs.size() > 1) {
                tempBulbs.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, tempBulbs.size());
            } else {
                Toast.makeText(this, "At least one bulb must remain", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBulbs.setAdapter(adapter);

        // Add button
        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_bulb, null);
            EditText inputPosition = dialogView.findViewById(R.id.inputPosition);
            EditText inputWatt = dialogView.findViewById(R.id.inputWatt);
            EditText inputVoltage = dialogView.findViewById(R.id.inputVoltage);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add New Bulb")
                    .setView(dialogView)
                    .setPositiveButton("OK", null)
                    .setNegativeButton("Cancel", null)
                    .create();

            dialog.setOnShowListener(dlg -> {
                Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(btn -> {
                    String position = inputPosition.getText().toString().trim();
                    String wattStr = inputWatt.getText().toString().trim();
                    String voltageStr = inputVoltage.getText().toString().trim();

                    if (position.isEmpty() || wattStr.isEmpty() || voltageStr.isEmpty()) {
                        Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        double watt = Double.parseDouble(wattStr);
                        double voltage = Double.parseDouble(voltageStr);
                        Bulb newBulb = new Bulb(position, watt, voltage);

                        tempBulbs.add(newBulb);
                        adapter.notifyItemInserted(tempBulbs.size() - 1);
                        adapter.notifyItemChanged(0);

                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Watt and Voltage must be numbers", Toast.LENGTH_SHORT).show();
                    }
                });
            });

            dialog.show();
        });
    }
}
