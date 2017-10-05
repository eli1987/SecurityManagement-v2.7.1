package com.example.oryossipof.securitymanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.util.TimeZone;

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
    private int day,month2,year2,month3;
    private DatePickerDialog datepick;
    private Calendar cal ;
    private   static final int DATE_DIALOG_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        day = cal.get(Calendar.DAY_OF_MONTH);
        month2 = cal.get(Calendar.MONTH);
        year2 = cal.get(Calendar.YEAR);

        selectMonthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datepick = new DatePickerDialog(context,0,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {


                        month3 = datePicker.getMonth();
                        year2 = datePicker.getYear();
                        month3+=1;

                        if(month3 < 10)
                            monthStr= "0"+month3;
                        else
                            monthStr =""+month3;

                        if(mLost.size()>0)
                        {
                            mLost.clear();
                            adapter.notifyDataSetChanged();

                        }

                        mListView.setSelection(adapter.getCount() - 1);

                        mRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/LostandFound");

                        mRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                                for (DataSnapshot child: dataSnapshot.getChildren()) {


                                    if (dataSnapshot.getKey().equals(monthStr +"-"+year2)) {
                                        Lost lost = child.getValue(Lost.class);

                                        mLost.add(new Lost(lost.getLostDescrption(), lost.getMonth(), lost.getUsername(), lost.getWhereLostFound(), lost.getWhoFound(), lost.getImageUri(), lost.getLostnumber(), lost.getIsReturn(),lost.getDayFounded()));

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
                },year2,month2,day);
                datepick.show();
            }
        });


    }
}
