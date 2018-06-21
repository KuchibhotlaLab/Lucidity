package com.mobile.lucidity.lucidity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    ArrayList<Bitmap> images = new ArrayList<>();
    BitmapAdapter gridAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gallery = findViewById(R.id.photoGalary);
        Bitmap bmp = null;
        FloatingActionButton addImg = findViewById(R.id.add_img);


        //load the images saved in app locally
        //stackoverflow.com/questions/5694385/
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File folder = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File[] listOfFiles = folder.listFiles();
        ArrayList<File> listOfImages = new ArrayList<>();

        for(int i = 0; i < listOfFiles.length; i++){
            if(listOfFiles[i].isFile()) {
                listOfImages.add(listOfFiles[i]);
                images.add(loadFromInternalStorage(listOfFiles[i]));
            }
        }



        //get the image sent by selected
        //reference: stackoverflow.com/questions/11010386
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(bmp != null){
            //display file on this page
            images.add(bmp);
            //save file locally
            String imgName = "File_" + Integer.toString(listOfFiles.length);
            String newUrl = saveToInternalStorage(bmp, imgName);
            listOfImages.add(new File(newUrl));
        }

        gridAdapter = new BitmapAdapter(getBaseContext(), images, listOfImages);
        gallery.setAdapter(gridAdapter);
        gallery.setOnItemClickListener(itemClickListener);



        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent);
            }
        });

    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ImageObject l = (ImageObject)gridAdapter.getItem(position);
            Intent intent = new Intent(getApplicationContext(), DisplayImageActivity.class);
            intent.putExtra("image", l.getUrl());
            startActivity(intent);
            //TODO: figure out why the newly added picture doesn't show up(bug can be deleted)
        }
    };


    public class BitmapAdapter extends BaseAdapter {

        private Context mContext;
        List<Bitmap> gridViewitems;
        List<File> files;

        public BitmapAdapter(Context c, List<Bitmap> gridViewitems, List<File> files){

            mContext = c;
            this.gridViewitems = gridViewitems;
            this.files = files;
        }

        @Override
        public int getCount() {
            return gridViewitems.size();
        }

        @Override
        public Object getItem(int position) {
            ImageObject io = new ImageObject(
                    files.get(position).getName(), files.get(position).getAbsolutePath());
            return io;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(20, 20, 20, 20);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(gridViewitems.get(position));
            return imageView;
        }


    }

    public class ImageObject {
        private String title;
        private String url;

        public ImageObject(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return title;
        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage, String imgName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imgName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    private Bitmap loadFromInternalStorage(File f)
    {
        Bitmap b = null;

        try {
            //File f=new File(path, imgName);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.imgPicker);
            //img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return b;

    }
}
