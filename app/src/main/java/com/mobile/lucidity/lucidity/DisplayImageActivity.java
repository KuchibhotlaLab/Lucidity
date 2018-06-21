package com.mobile.lucidity.lucidity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        ImageView targetImage = findViewById(R.id.targetimage);
        Bitmap bmp = null;
        //open file with the path passed here
        //and show on full screen in imageview
        String url = getIntent().getStringExtra("image");
        final File f = new File(url);
        try {
            bmp = BitmapFactory.decodeStream(new FileInputStream(f));
            targetImage.setImageBitmap(bmp);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        Button btnCancel = findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Button btnDelete = findViewById(R.id.delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.delete();

                finish();
            }
        });


    }
}
