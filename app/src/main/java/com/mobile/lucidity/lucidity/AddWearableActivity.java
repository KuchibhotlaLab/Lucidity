package com.mobile.lucidity.lucidity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddWearableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wearable);


        final Button btn_add = findViewById(R.id.addWearable);
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), AddWearableFormActivity.class);
                startActivity(intent);


                Button btn_wearable = new Button(getApplicationContext());
                btn_wearable.setText("Push Me");

                LinearLayout ll = findViewById(R.id.lnr_wearable);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.addView(btn_wearable, lp);
            }
        });
    }
}
