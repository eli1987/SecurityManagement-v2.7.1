package com.example.oryossipof.securitymanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Calendar;

public class LostAndFoundDocumentation extends AppCompatActivity {


    private Context context;
    private ImageView iv;
    private Firebase mRef;
    private ListView mListView ;
    private ArrayList<Lost> mLost = new ArrayList<>();
    private  String time,name,des;
    private  String myUsername, myID;
    private String value,monthStr;
    private  Button selectMonthBt,isreturnBt;
    private int day,month2,year2;
    private DatePickerDialog datepick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_documentation);


        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");

        mListView = (ListView) findViewById(R.id.lv_msglist);
        final LostAdapter adapter = new LostAdapter(this,R.layout.listviewlost_layout,mLost);
        mListView.setAdapter(adapter);
        context=this;
        selectMonthBt = (Button) findViewById(R.id.selectMonthBt);



        selectMonthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepick = new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        month2 = datePicker.getMonth();
                        year2 = datePicker.getYear();
                        month2+=1;

                        if(month2 < 10)
                            monthStr= "0"+month2;
                        else
                            monthStr =""+month2;


                        mListView.setSelection(adapter.getCount() - 1);

                        mRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/LostandFound");

                        mRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                                for (DataSnapshot child: dataSnapshot.getChildren()) {


                                    if (dataSnapshot.getKey().equals(monthStr +"-"+year2)) {
                                        Lost lost = child.getValue(Lost.class);

                                        mLost.add(new Lost(lost.getLostDescrption(), lost.getMonth(), lost.getUsername(), lost.getWhereLostFound(), lost.getWhoFound(), lost.getImageUri(), lost.getLostnumber(), lost.getIsReturn()));

                                        adapter.notifyDataSetChanged();
                                        mListView.setSelection(adapter.getCount() - 1);

                                    }

                                }

                                // String value = dataSnapshot.child("time").getValue(String.class);



                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                    }
                },day,month2,year2);
                datepick.show();}
        });



    }
}
