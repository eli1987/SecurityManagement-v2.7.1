package com.example.oryossipof.securitymanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText nameText ;
    private Button loginButton ;
    private  Firebase myRef;
    String add ="http://10.0.0.2/secuirtyManagement/fcm_insert.php";  //Change it to your own path//


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Firebase.setAndroidContext(LoginActivity.this);

        this.myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");



        nameText = (EditText) findViewById(R.id.input_name);
        loginButton =  (Button) findViewById(R.id.btn_login);

        int maxLength = 9;
        nameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_Token),"");
                StringRequest sR = new StringRequest(Request.Method.POST,add, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }

                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                    //    params.put("fcm_token",token);
                        return super.getParams();

                    }
                };
                MySinglton.getmInstance(LoginActivity.this).addToRequestque(sR);


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



