package com.example.carlightsapp;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
public class CurrentLightConfig {
    private static final String FILE_NAME = "config.json";
    public static List<Bulb> loadConfiguration(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            List<Bulb> defaultConfig = getDefaultConfiguration();
            saveConfiguration(context, defaultConfig);
            return defaultConfig;
        }

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Bulb>>() {}.getType();
            return gson.fromJson(builder.toString(), listType);

        } catch (IOException e) {
            return getDefaultConfiguration();
        }
    }

    public static void saveConfiguration(Context context, List<Bulb> bulbs) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String json = new Gson().toJson(bulbs);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Config", "save failed: " + e.getMessage());
        }
    }

    public static List<Bulb> getDefaultConfiguration() {
        List<Bulb> bulbs = new ArrayList<>();
        bulbs.add(new Bulb("Front left head light", 55, 12));
        bulbs.add(new Bulb("Front right head light", 55, 12));
        bulbs.add(new Bulb("Rear left brake", 5, 12));
        bulbs.add(new Bulb("Rear right brake", 5, 12));
        bulbs.add(new Bulb("Rear left tail light", 5, 12));
        bulbs.add(new Bulb("Rear right tail light", 5, 12));
        return bulbs;
    }

}