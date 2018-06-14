package com.mobile.lucidity.lucidity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Stores the username of the user
    private String username;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // Progress Dialog
    private ProgressDialog pDialog;

    // url to add a user
    private static String url_verify_carepass = "http://ec2-174-129-156-45.compute-1.amazonaws.com/lucidity/verify_carepass.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets the username passed from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }

        Button caregiver = findViewById(R.id.caregiver);
        caregiver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //PasswordDialog alert = new PasswordDialog(MainActivity.this);
                //alert.show();

                //Intent intent = new Intent(getApplicationContext(), CaregiverHomePage.class);
                //startActivity(intent);

                showDialog();

            }
        });


        Button main = findViewById(R.id.main);
        main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showDialog()
    {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.password_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.caregiver_password);

        /*final Button btnSubmit = (Button) promptsView.findViewById(R.id.submit);

        final Button btnCancel = (Button) promptsView.findViewById(R.id.cancel);

        AlertDialog alertDialog = alertDialogBuilder.create();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View v) {
            }
        });*/

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // DO THE METHOD HERE WHEN PROCEED IS CLICKED
                                String user_text = (userInput.getText()).toString();

                                if (user_text.trim().length() == 0)
                                {
                                    userInput.setError("Please Enter Name");
                                }
                                else{
                                    /*Log.d(user_text,"string is empty");
                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancel", null);
                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            showDialog();
                                        }
                                    });
                                    builder.create().show();*/

                                    VerifyCaregiver verifyTask = new VerifyCaregiver(user_text, userInput);
                                    verifyTask.execute();
                                }
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }

                        }

                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    /**
     * Background Async Task to Login User
     * */
    class VerifyCaregiver extends AsyncTask<String, String, String> {

        private String inputText;
        private EditText inputTextObject;

        public VerifyCaregiver(String userText, EditText userInput)
        {
            inputText = userText;
            inputTextObject = userInput;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Verifying caregiver password..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Login
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("caregiverpassword", inputText));

            // getting JSON Object
            // Note that login user url accepts Get method
            JSONObject json = jsonParser.makeHttpRequest(url_verify_carepass,
                    "GET", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                String msg = json.getString(TAG_MESSAGE);

                if (success == 1) {
                    // successfully verified password
                    Intent intent = new Intent(getApplicationContext(), CaregiverHomePage.class);

                    //Pass username through to other activities
                    intent.putExtra("username", username);

                    startActivity(intent);
                } else {
                    setError(inputTextObject, msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }

    private void setError(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setError(value);
            }
        });
    }
}
