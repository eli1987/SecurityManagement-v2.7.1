package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import static android.R.attr.port;

public class PorterageMonthActivity extends AppCompatActivity {
    private String myUsername,myID,month,year;
    private Button cancelBT, addBT;
    private TextView monthYearText,totalMoneyInMonth;
    private ListView pListView;
    private Firebase mRef;
    private double sum;
    private ArrayList<PorterageIntity> porterageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porterage_month);


        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        this.month = intent.getStringExtra("month");
        Log.e("",month);
        this.year =  intent.getStringExtra("year");

        totalMoneyInMonth = (TextView) findViewById(R.id.totalMoneyInMonth);


        totalMoneyInMonth.setText(sum + "");
        monthYearText = (TextView)findViewById(R.id.month_yearTxt);
        monthYearText.setText(month + "/" + year);

        cancelBT = (Button) findViewById(R.id.cancelBT);
        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        addBT = (Button) findViewById(R.id.addGroupBT);
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PorterageMonthActivity.this, AddGroupActivity.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);



            }
        });


        pListView = (ListView) findViewById(R.id.lv_grouplist);
        final PorterageListAdapter adapter = new PorterageListAdapter(this,R.layout.listview_porterage_layout,porterageList);
        pListView.setAdapter(adapter);

        mRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/Porterage/"+myID+"/"+year);
       // mRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/Porterage");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    //  if (dataSnapshot.getKey().equals(myID)) {
                    //       if (dataSnapshot.child(myID).child(year).getKey().equals(year)) {
                    ///            if (dataSnapshot.child(myID).child(year).child(month).getKey().equals(month)) {
                    if (dataSnapshot.getKey().equals(month)) {
                        PorterageIntity port = child.getValue(PorterageIntity.class);
                        Log.e("port", child.getValue().toString());


                        porterageList.add(new PorterageIntity(port.getNumhands(), port.getNumpeople(), port.getNumstuff(), port.getStatus(), port.getDate(),port.getTotalMoney(),port.getTotalPeople()));
                        sum+=Double.parseDouble(port.getTotalMoney());
                        totalMoneyInMonth.setText(" $" + sum + " ");

                        adapter.notifyDataSetChanged();
                        pListView.setSelection(adapter.getCount() - 1);
                    }
                }
                //        }
                //    }
               // }

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

}
