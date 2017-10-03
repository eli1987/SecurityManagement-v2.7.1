package com.example.oryossipof.securitymanagement;

import android.app.Activity;
        import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
        import android.widget.ListView;

import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class LogbookEvents extends Activity {
    private Context context;
    private ImageView iv;
    private Firebase mRef;
    private ListView mListView ;
    private ArrayList<User> mUsersName = new ArrayList<>();
    private  String time,name,des;
    private  String myUsername, myID;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logbook_events);
        context= this;

        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");

        mListView = (ListView) findViewById(R.id.lv_msglist);
        final UserListAdapter adapter = new UserListAdapter(this,R.layout.listview_layout,mUsersName);
        mListView.setAdapter(adapter);



        mListView.setSelection(adapter.getCount() - 1);

        mRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/Events");



        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    User people = child.getValue(User.class);



                    mUsersName.add(new User(people.getDescription(),people.getTime(),people.getUsername(),people.getUrlImage()));




                    adapter.notifyDataSetChanged();
                        mListView.setSelection(adapter.getCount() - 1);

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
}


