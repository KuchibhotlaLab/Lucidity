package com.mobile.lucidity.lucidity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddWearableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wearable);


        final FloatingActionButton btn_add = findViewById(R.id.addWearable);
        ArrayList<String> buttons = new ArrayList<String>();

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.device_dialog, null);


                //TODO: edit text able to accept input
                final EditText device = popupView.findViewById(R.id.device_to_add);

                /*final InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                // Auto show keyboard
                device.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean isFocused) {

                        if (isFocused)
                        {
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        }
                    }
                });*/


                //inflate the add device dialog
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);




                Button btnDismiss = (Button)popupView.findViewById(R.id.cancel_device);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                Button btnSave = (Button)popupView.findViewById(R.id.add_device);
                btnSave.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO: save the device information

                        //TODO: get input string and put into cutomized button
                        //TODO: set absolute position of button/put in tableview
                        String deviceName = device.getText().toString();
                        Toast.makeText(getBaseContext(), deviceName, Toast.LENGTH_LONG).show();

                        //add new button associated with device
                        Button btn_wearable = new Button(getApplicationContext());
                        btn_wearable.setText(deviceName);

                        LinearLayout ll = findViewById(R.id.lnr_wearable);
                        ViewGroup.LayoutParams lp =
                                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                        ll.addView(btn_wearable, lp);


                        popupWindow.dismiss();
                    }
                });
            }});
    }
}
