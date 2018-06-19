package com.mobile.lucidity.lucidity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gallery = findViewById(R.id.photoGalary);

        //get the image sent by selected
        //reference: stackoverflow.com/questions/11010386
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = null;
        if(byteArray != null){
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }


    }


    public class BitmapAdapter extends BaseAdapter {

        private Context mContext;
        List<Bitmap> gridViewitems;

        public BitmapAdapter(Context c, List<Bitmap> gridViewitems){

            mContext = c;
            this.gridViewitems=gridViewitems;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(gridViewitems.get(position));
            return imageView;
        }


    }
}
