package com.mobile.lucidity.lucidity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    private List<String> myList;
    private GridView gridview;
    private String mCurrentPhotoPath;
    public static final String PREFS_NAME = "MyPrefsFile";


    private ArrayList<String> images;


    ImageView targetImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        FloatingActionButton loadImg = findViewById(R.id.loadimage);
        targetImage = (ImageView)findViewById(R.id.targetimage);
        gridview = findViewById(R.id.photoGalary);
        gridview.setAdapter(new ImageAdapter(this));

        loadImg.setOnClickListener(new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }});

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (null != images && !images.isEmpty())
                    Toast.makeText(
                            getApplicationContext(),
                            "position " + position + " " + images.get(position),
                            Toast.LENGTH_SHORT).show();
                ;

            }
        });







        //TODO: code to take picture with camera
        /*SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        myList = new ArrayList<String>();
        myList.clear();
        int size = prefs.getInt("myList.size()", 0);

        for (int i=0; i < size; i++) {
            String imagePath = prefs.getString(String.format("myList[%d]", i), "");
        }

        final Context con = this;
        Button camera = findViewById(R.id.cameraButton);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });

        gridview = (GridView) findViewById(R.id.photoGalary);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // Send File Path to Image Display Activity
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                intent.putExtra("path", myList.get(position));
                startActivity(intent);
            }
        });*/


    }


    // To load single image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);

                /*TODO: store this image in the string of images
                 *refer to this further if fails:
                 *https://stackoverflow.com/questions/28505123/getting-an-image-path-from-a-imageview*/

                /*Uri path = Uri.parse("android.resource:com.mobile.lucidity.lucidity/" + targetImage.getId());
                String imgPath = path.toString();
                images.add(imgPath);*/


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * The Class ImageAdapter.
     */
    private class ImageAdapter extends BaseAdapter {

        /** The context. */
        private Activity context;

        /**
         * Instantiates a new image adapter.
         *
         * @param localContext
         *            the local context
         */
        public ImageAdapter(Activity localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }

        public int getCount() {
            return images.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(270, 270));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position)).into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity
         *            the activity
         * @return ArrayList with images Path
         */
        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }
    }


    //taking a photo with app
    /*static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photo = null;
            try {
                photo = createImageFile();
            }
            catch (IOException ex) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            if(photo != null){
                takePictureIntent.putExtra(MediaStore. EXTRA_OUTPUT,
                        FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", photo));

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        } else {
            Toast.makeText(this, "Another thing went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeS = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picName = "JPEG_" + timeS + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(picName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();

        return image;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //ImageView mImageView = findViewById(R.id.image1);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent medIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
            File f = new File(mCurrentPhotoPath );
            Uri contentUri = Uri.fromFile(f);
            medIntent.setData(contentUri);
            this.sendBroadcast(medIntent);

            myList.add(mCurrentPhotoPath);
            gridview.invalidateViews();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("myList.size()", myList.size());

        for (int i=0; i < myList.size(); i++) {
            editor.putString(String.format("myList[%d]", i), myList.get(i));
        }

        editor.commit();


    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        public int getCount(){
            return myList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false ;
            bmOptions.inSampleSize = 4;
            bmOptions.inPurgeable = true ;
            Bitmap bitmap = BitmapFactory.decodeFile(myList.get(position), bmOptions);

            imageView.setImageBitmap(bitmap);

            return imageView;
        }
    }*/

}
