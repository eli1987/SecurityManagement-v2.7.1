package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {
    private Button addGroupBT,porterageThisMonthBT,cancelBT;
    final List<String> statusList = new ArrayList<String>();
    private String myUsername,myID,month,year,statusStr;
    private EditText date, groupNum, handsOnGroup, stuffNum;
    private int  groupNumInt,handsOnGroupInt,stuffNumInt;  //initialize all ints and send it to the database. if an error alert!
    private Spinner statusCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        this.month = intent.getStringExtra("month");
        this.year =  intent.getStringExtra("year");

        date = (EditText)findViewById(R.id.month_yearTxt);
        date.setText(month + "/" + year);


        groupNum = (EditText) findViewById(R.id.numpeolpetxt);
        handsOnGroup = (EditText) findViewById(R.id.handsontxt);
        stuffNum  = (EditText) findViewById(R.id.numofstufftxt);
        statusCombo = (Spinner) findViewById(R.id.statusSpinner);

        statusList.add("C/I");
        statusList.add("C/O");




        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, statusList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusCombo.setAdapter(adp1);

        porterageThisMonthBT = (Button) findViewById(R.id.porterageThisMonth);
        porterageThisMonthBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroupActivity.this, PorterageMonthActivity.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                intent.putExtra("month",month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });

        addGroupBT = (Button) findViewById(R.id.addGroupBT);
        addGroupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validation())  //validation ok
                {
                    DataBase.handleAddNewGroup(month, year, statusStr,groupNumInt,handsOnGroupInt,stuffNumInt, myID);

                    groupNum.setText(""); handsOnGroup.setText(""); stuffNum.setText("");
                    Toast.makeText(AddGroupActivity.this,"Group Successfully Inserted!", Toast.LENGTH_SHORT).show();

                }
                else return;



            }
        });

        cancelBT = (Button) findViewById(R.id.cancelBT);
        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validation() {
        boolean flag = true;
        statusStr = statusCombo.getSelectedItem().toString();


        try
        {
            groupNumInt = Integer.parseInt(groupNum.getText().toString());


        }catch (Exception e)
        {
            flag = false;
            groupNum.setError("All Fileds Must Be Filled Correctly!");
        }


        try
        {
            handsOnGroupInt = Integer.parseInt(handsOnGroup.getText().toString());

        }catch (Exception e)
        {

            flag = false;
            handsOnGroup.setError("All Fileds Must Be Filled Correctly!");
        }

        try
        {
            stuffNumInt = Integer.parseInt(stuffNum.getText().toString());

        } catch (Exception e)
        {
            flag = false;
            stuffNum.setError("All Fields Must Be Filled Correctly!");
        }


        if (stuffNumInt> groupNumInt)
        {
            stuffNum.setError("#Stufff Cant Be Greater Than #People");
            flag = false;
        }
        else if (stuffNumInt<=0)
        {
            flag = false;
            stuffNum.setError("Need To Be Greater Than 0");
        }
        else if(groupNumInt<=0)
        {
            flag = false;
            groupNum.setError("Need To Be Greater Than 0");
        }
        else if(handsOnGroupInt <=0)
        {
            flag = false;
            handsOnGroup.setError("Need To Be Greater Than 0");
        }

        return flag;

    }

}
