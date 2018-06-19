package com.mobile.lucidity.lucidity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AddTestMaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_material);

        Button btn_photo = findViewById(R.id.add_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTestMaterialActivity.this);
                LayoutInflater inflater = ((Activity) AddTestMaterialActivity.this).getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.add_image_dialog,
                        null);

                final AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.setView(dialogLayout, 0, 0, 0, 0);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                WindowManager.LayoutParams wlmp = dialog.getWindow()
                        .getAttributes();
                wlmp.gravity = Gravity.BOTTOM;


                Button btnCamera = (Button) dialogLayout.findViewById(R.id.btn_add_gallery);
                Button btnGallery = (Button) dialogLayout.findViewById(R.id.btn_add_camera);
                Button btnStored = (Button) dialogLayout.findViewById(R.id.btn_current_gal);
                Button btnDismiss = (Button) dialogLayout.findViewById(R.id.btn_cancel_img_dialog);


                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                        startActivity(intent);
                    }
                });


                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                        startActivity(intent);
                    }
                });


                builder.setView(dialogLayout);

                dialog.show();
            }
        });
    }

}
