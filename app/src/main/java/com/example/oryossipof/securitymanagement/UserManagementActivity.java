package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class UserManagementActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText userid,IDText,nameText;
    private Firebase myRef;
    private Button registerBT, removeBT;
    private String myID, myUsername;
    private final String  ADMIN = "000000000";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_activity);
        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");


        Firebase.setAndroidContext(this);
        firebaseAuth= FirebaseAuth.getInstance();
        userid = (EditText)findViewById(R.id.userID) ;


        IDText = (EditText) findViewById(R.id.userID);
        nameText =  (EditText) findViewById(R.id.userName);

        int maxLength = 9;
        IDText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});


        myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");


        registerBT = (Button)findViewById(R.id.signupbt) ;
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();


            }
        });

        removeBT = (Button)findViewById(R.id.removeUserbt);
        removeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateRemove())
                {
                    onRemoveFailed();
                    return;
                }

                if (IDText.getText().toString().equals(myID))
                {
                    IDText.setError("cant remove yourself!");
                    onRemoveFailed();
                    return;
                }

                if (IDText.getText().toString().equals(ADMIN))
                {
                    IDText.setError("cant remove Administrator!");
                    onRemoveFailed();
                    return;
                }

                DataBase.removeUser(IDText.getText().toString());
                Toast.makeText(getBaseContext(), "Remove Succeded!", Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }

    private boolean validateRemove()
    {
        String id = IDText.getText().toString();
        if (id.isEmpty() || id.length() != 9 ) {
            IDText.setError("ID need to be  9 characters!");
            return false;

        }
        return true;
    }

    private void onRemoveFailed() {
        Toast.makeText(getBaseContext(), "Remove failed!", Toast.LENGTH_LONG).show();

    }

    public void signup() {



        if (!validate()) {
            onSignupFailed();
            return;
        }

        registerBT.setEnabled(false);


        String name = IDText.getText().toString();




        // TODO: Implement your own signup logic here.



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();

                    }
                }, 2000);
    }


    public void onSignupSuccess() {
        registerBT.setEnabled(true);
        DataBase.signupUser(IDText.getText().toString(),nameText.getText().toString());

        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Signup Succeeded!", Toast.LENGTH_LONG).show();

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed!", Toast.LENGTH_LONG).show();

        registerBT.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String id = IDText.getText().toString();
        String name = nameText.getText().toString();

        if (id.isEmpty() || id.length() != 9 ) {
            IDText.setError("ID need to be  9 characters!");
            valid = false;

        }

        else if(name.isEmpty()) {
            nameText.setError("Name cant be empty!");
            valid = false;
        }
        else {
            IDText.setError(null);
        }

        return valid;
    }




}












