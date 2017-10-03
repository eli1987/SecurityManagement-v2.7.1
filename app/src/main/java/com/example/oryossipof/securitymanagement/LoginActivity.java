package com.example.oryossipof.securitymanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Freezable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText nameText ;
    private Button loginButton ;
    private  Firebase myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(LoginActivity.this);

        this.myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");



        nameText = (EditText) findViewById(R.id.input_name);
        loginButton =  (Button) findViewById(R.id.btn_login);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }


        String name = nameText.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onLoginSuccess();

                    }
                }, 1000);
    }


    public void onLoginSuccess() {

  //      DataBase.signupUser(na.getText().toString(),nameText.getText().toString());


        /* if (DataBase.loginUser(nameText.getText().toString())){
            setResult(RESULT_OK, null);
            Toast.makeText(getBaseContext(), "Hello " + nameText.getText() + "!", Toast.LENGTH_LONG).show();
        }
        else
        {
            onLoginFailed();
        }*/

        String userID = nameText.getText().toString();
        Query query = myRef.child("users").child(userID);



        query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())  //there is a match
                {
                        String name = "";

                    for (com.firebase.client.DataSnapshot  d :dataSnapshot.getChildren()) {
                       name =  d.getValue().toString();  //retrieve the name of the man
                        break;
                    }

                    Toast.makeText(getBaseContext(), "Hello " + name, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
                    intent.putExtra("myUsername",name);
                    intent.putExtra("myID",nameText.getText().toString());
                    startActivity(intent);
                    finish();   //close this activity

                }
                else
                {
                    onLoginFailed();   //Didnt find any person
                }


            }



            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Wrong ID", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {
        boolean valid = true;

        String id = nameText.getText().toString();

         if(id.isEmpty() || id.length()!=9) {
            nameText.setError("ID must contain 9 characters!");
            valid = false;
        }
        else {
             nameText.setError(null);
        }

        return valid;
    }


}



