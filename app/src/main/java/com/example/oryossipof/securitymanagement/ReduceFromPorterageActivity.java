package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class ReduceFromPorterageActivity extends AppCompatActivity {
private String myUsername,myID;
    private EditText dollarValue, howmuchToReduce;
    private Button cancelBT, okBT;
    private Firebase myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce_from_porterage);

        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        this.myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");

//
        cancelBT = (Button) findViewById(R.id.cancelBT);
        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        okBT = (Button)findViewById(R.id.okBT);
        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert a new record
                if(validation())
                {
                    float ToReduce = (float) (Double.parseDouble(howmuchToReduce.getText().toString())  / Double.parseDouble(dollarValue.getText().toString()));
                    DataBase.updateReduce(myID,ToReduce);
                    Toast.makeText(ReduceFromPorterageActivity.this,"Inserted!",Toast.LENGTH_SHORT).show();
                    finish();


                }
                else
                {
                    return;
                }


            }
        });

        dollarValue = (EditText)findViewById(R.id.dollarValue);
        howmuchToReduce = (EditText) findViewById(R.id.SumToReduce);

    }

    private boolean validation() {
         boolean flag = true;


        if (howmuchToReduce.getText().toString().isEmpty())
        {
            howmuchToReduce.setError("Cant Be Empty");
            flag = false;
        }

        if (dollarValue.getText().toString().isEmpty())
        {
            dollarValue.setError("Cant Be Empty");
            flag = false;
        }








        return flag;
    }
}
