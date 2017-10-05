package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PorterageActivity extends AppCompatActivity {
private String myUsername, myID;
    final List<String> monthList = new ArrayList<String>();
    final  List<String> yearList = new ArrayList<String>();
    private Spinner sp1,sp2;
    private TextView totalPorterageTxt;
    private Button exitBT,okBT, gotsomeBT;
    private Firebase myRef;


    @Override
    protected void onResume() {
        super.onResume();
        setTextToTotalPorterage();

        Log.e("onresume:" , "onresume");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porterage);


        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        initializeCombos();
        this.myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");


        exitBT = (Button)findViewById(R.id.exitBT);
        exitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okBT = (Button)findViewById(R.id.okBT);
        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PorterageActivity.this, AddGroupActivity.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                intent.putExtra("month", sp1.getSelectedItemPosition()+1 + "");

                intent.putExtra("year", sp2.getSelectedItem()+"");
                startActivity(intent);

            }
        });

        gotsomeBT = (Button)findViewById(R.id.gotsomeBT);
        gotsomeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PorterageActivity.this, ReduceFromPorterageActivity.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                startActivity(intent);

            }
        });


        totalPorterageTxt = (TextView)findViewById(R.id.totalPorterageTxt);


        //setTextToTotalPorterage();






    }

    private void setTextToTotalPorterage() {
        Query query = myRef.child("Porterage").child(myID);



        query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                double totalSum = 0;
                if (dataSnapshot.exists())  //there is a match
                {
                    String name = "";

                    for (com.firebase.client.DataSnapshot d : dataSnapshot.getChildren()) {
                        //  name = d.getValue().toString();  //retrieve the name of the man
                        for  (com.firebase.client.DataSnapshot e : d.getChildren())
                        {
                            Log.e("e",e.getChildren().toString());
                            for(com.firebase.client.DataSnapshot f : e.getChildren())
                            {
                                String total =  f.child("totalMoney").getValue().toString();

                                totalSum+= Double.parseDouble(total);
                                totalPorterageTxt.setText(totalSum + "");


                            }
                        }

                    }



                    minusReduce();

                } else {
                    //Didnt find any person
                    totalPorterageTxt.setText(totalSum+"");
                    minusReduce();

                }





            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });


    }

    private void minusReduce() {
    Log.e("minus:","minus");
        final Firebase mRefChild = myRef.child("ToReduce").child(myID);

        mRefChild.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    float wasToReduce =  0;
                    wasToReduce = (float) Double.parseDouble(dataSnapshot.child("sum").getValue().toString());


                    float t = (float)Double.parseDouble(totalPorterageTxt.getText().toString());

                    totalPorterageTxt.setText((t - wasToReduce)+"");

                }

                else
                {

                    try {
                        float t = (float)Double.parseDouble(totalPorterageTxt.getText().toString());
                        totalPorterageTxt.setText(t+"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    private void initializeCombos() {
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");


        yearList.add("2017");
        yearList.add("2018");
        yearList.add("2019");
        yearList.add("2020");
        yearList.add("2021");
        yearList.add("2022");
        yearList.add("2023");



        sp1 = (Spinner) findViewById(R.id.monthCombo);
        sp2 = (Spinner) findViewById(R.id.yearCombo);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, monthList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, yearList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adp2);


        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        sp1.setSelection(month);
        sp2.setSelection(adp2.getPosition(year+""));
    }

}
