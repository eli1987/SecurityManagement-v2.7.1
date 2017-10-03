package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PorterageActivity extends AppCompatActivity {
private String myUsername, myID;
    final List<String> monthList = new ArrayList<String>();
    final  List<String> yearList = new ArrayList<String>();
    private Spinner sp1,sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porterage);

        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");


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



         sp1 = (Spinner) findViewById(R.id.monthCombo);
        sp2 = (Spinner) findViewById(R.id.yearCombo);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, monthList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, yearList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adp2);
    }
}
