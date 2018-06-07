package com.mobile.lucidity.lucidity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                boolean valid = validEntry();
                if(valid){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //TODO: Store the information in the database
                }
            }
        });
    }

    private boolean validEntry(){
        //TODO: check that they are all valid (i.e. not already existent in the database)
        EditText username = (EditText)findViewById(R.id.signup_input_username);
        EditText name = (EditText)findViewById(R.id.signup_input_name);
        EditText pw1 = (EditText)findViewById(R.id.signup_input_password);
        EditText pw2 = (EditText)findViewById(R.id.signup_input_reconfirm_password);
        EditText caregiverpw = (EditText)findViewById(R.id.signup_input_caregiver_password);
        return usernameEntered(username) &&
                passwordConfirmed(pw1, pw2) &&
                caregierpwConfirmed(caregiverpw) &&
                nameEntered(name);
    }


    private boolean nameEntered(EditText name){
        if(name.getText().toString().trim().length() == 0){
            name.setError("Please Enter Name");
            return false;
        }
        return true;
    }

    private boolean usernameEntered(EditText username){
        if(username.getText().toString().trim().length() == 0){
            username.setError("Please Enter Username");
            return false;
        }
        return true;
    }

    private boolean caregierpwConfirmed(EditText caregiverpw){
        if(caregiverpw.getText().toString().trim().length() == 0){
            caregiverpw.setError("Please Enter Name");
            return false;
        }
        return true;
    }

    private boolean passwordConfirmed(EditText pw1, EditText pw2){
        if(pw1.getText().toString().trim().length() == 0){
            pw1.setError("Please Enter Password");
            return false;
        }
        if(pw2.getText().toString().trim().length() == 0){
            pw2.setError("Please Enter Password Again");
            return false;
        }
        if(!pw1.getText().toString().equals(pw2.getText().toString())){
            pw2.setError("Please make sure the two passwords are the same");
            return false;
        }
        return true;
    }


}
