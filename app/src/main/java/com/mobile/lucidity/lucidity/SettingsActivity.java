package com.mobile.lucidity.lucidity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#E9E9E9"));

        Button register = findViewById(R.id.MH);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedHistoryDemo.class);
                startActivity(intent);
            }
        });

        Button wearable = findViewById(R.id.wearable);
        wearable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddWearableActivity.class);
                startActivity(intent);
            }
        });

        Button test = findViewById(R.id.upload_test_material);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTestMaterialActivity.class);
                startActivity(intent);
            }
        });

    }
}
