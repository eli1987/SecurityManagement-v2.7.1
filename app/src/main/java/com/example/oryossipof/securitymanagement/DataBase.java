package com.example.oryossipof.securitymanagement;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.net.Uri;
import com.firebase.client.Firebase;

import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by or yossipof on 21/09/2017.
 */

class DataBase {
    private static float totalSum;
    private static Firebase myRef;
    private Context context;
    public static boolean flag;

    //

    public DataBase(Context context) {
        this.context = context;

        Firebase.setAndroidContext(this.context);

        this.myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");


    }


    public static void signupUser(String id, String name) {

        //Firebase mRefChild = myRef.child("users");
        Firebase mRefChild = myRef.child("users");


        mRefChild.push();
        mRefChild.child(id + "").setValue(id + "");

 mRefChild.child(id + "").child("Name").setValue(name + "");
       // mRefChild.child(id + "").child("N").setValue(name + "");


    }
    //public static void addEventToDataBase(String dateandTime, String myUsername, String description, String hour_minutes, String timetoshow) {
        public static void addEventToDataBase(String dateandTime, String myUsername, String description, String hour_minutes,String timetoshow ,String donwloadImage) {

        Firebase mRefChild = myRef.child("Events");
        mRefChild.push();
        mRefChild.child(dateandTime).child(hour_minutes).child("time").setValue(timetoshow);
        mRefChild.child(dateandTime).child(hour_minutes).child("username").setValue(myUsername);
        mRefChild.child(dateandTime).child(hour_minutes).child("description").setValue(description);
            mRefChild.child(dateandTime).child(hour_minutes).child("urlImage").setValue(donwloadImage);



    }


    public static void removeUser(String id)
    {

        Firebase mRefChild = myRef.child("users");
        mRefChild.child(id).removeValue();

    }

    public static void addLostToFireBaseDataBase(int  lostnumber ,final String lostDescrption,final String whereLostFound,final String whoFound,final String dateofLost ,final String myUsername,final String imageString,final String timeToShow) {

        Firebase mRefChild = myRef.child("LostandFound").child(dateofLost);

        mRefChild.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int lostnumber = 0 ;
                if(dataSnapshot.exists())  //there is a match
                {
                    String name = "";
                    for (com.firebase.client.DataSnapshot  d :dataSnapshot.getChildren()) {
                        lostnumber ++;
                    }

                    Log.e("number of lost",lostnumber+"");
                }

                Firebase  mRefChild = myRef.child("LostandFound");

                mRefChild.push();
                mRefChild.child(dateofLost).child(lostnumber+"").child("lostnumber").setValue(lostnumber+"");
                mRefChild.child(dateofLost).child(lostnumber+"").child("lostDescrption").setValue(lostDescrption);
                mRefChild.child(dateofLost).child(lostnumber+"").child("whereLostFound").setValue(whereLostFound);
                mRefChild.child(dateofLost).child(lostnumber+"").child("whoFound").setValue(whoFound);
                mRefChild.child(dateofLost).child(lostnumber+"").child("month").setValue(dateofLost);
                mRefChild.child(dateofLost).child(lostnumber+"").child("DayFounded").setValue(timeToShow);
                mRefChild.child(dateofLost).child(lostnumber+"").child("username").setValue(myUsername);
                mRefChild.child(dateofLost).child(lostnumber+"").child("imageUri").setValue(imageString);
                mRefChild.child(dateofLost).child(lostnumber+"").child("isReturn").setValue("No");

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public static void returnItem(final String time ,final String id ,final String isReturn) {

        Firebase mRefChild = myRef.child("LostandFound").child(time);

        mRefChild.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String valueReturn;
                if(isReturn.equals("Yes"))
                     valueReturn = "Yes";
                else
                    valueReturn = "No";

                try {
                    myRef.child("LostandFound").child(time).child(id).child("isReturn").setValue(valueReturn+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public static void handleAddNewGroup(final String month, final String year , final String statusStr, final  int groupNumInt, final int handsOnGroupInt,final int stuffNumInt,final String myUserID) {
      /*  Map<String,String> mParent = new HashMap<String,String>();
        mParent.put("Numpeople", groupNumInt+"");
        mParent.put("Numhands", handsOnGroupInt+"");
        mParent.put("Numstuff", stuffNumInt+"");
        mParent.put("status", statusStr);*/

        //  myFirebaseRef.push().setValue(mParent);

      /*  Firebase mRefChild = myRef.child("Porterage").child(myUserID).child(year).child(month);
        mRefChild.push().setValue(mParent);*/

        Query query = myRef.child("Porterage").child(myUserID).child(year).child(month);



        query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy HH:mm " , Locale.US/* hh:mm*/);
                final String date = sdf2.format(new Date());
                if (dataSnapshot.exists())  //there is a match
                {
                    String groupid = "";



                    for (com.firebase.client.DataSnapshot d : dataSnapshot.getChildren()) {
                        groupid = d.getKey().toString();  //retrieve the name of the man
                        int groupIdInt = Integer.parseInt(groupid);
                        groupIdInt+=1;
                        groupid = groupIdInt+"";


                    }


                    Firebase mRefChild = myRef.child("Porterage").child(myUserID);
                    mRefChild.push();
                    mRefChild.child(year).child(month).child(groupid).child("Numpeople").setValue(groupNumInt+"");
                    mRefChild.child(year).child(month).child(groupid).child("Numhands").setValue(handsOnGroupInt+"");
                    mRefChild.child(year).child(month).child(groupid).child("Numstuff").setValue(stuffNumInt+"");
                    mRefChild.child(year).child(month).child(groupid).child("status").setValue(statusStr);
                    mRefChild.child(year).child(month).child(groupid).child("date").setValue(date);
                    mRefChild.child(year).child(month).child(groupid).child("totalPeople").setValue(groupNumInt-stuffNumInt + "");
                    mRefChild.child(year).child(month).child(groupid).child("totalMoney").setValue((float)((groupNumInt-stuffNumInt)*1.38/handsOnGroupInt) + "");
                }
                else
                {
                    Log.e("dont existst","dont exists");


                    Firebase mRefChild = myRef.child("Porterage").child(myUserID);
                    mRefChild.push();
                    mRefChild.child(year).child(month).child("1").child("Numpeople").setValue(groupNumInt+"");
                    mRefChild.child(year).child(month).child("1").child("Numhands").setValue(handsOnGroupInt+"");
                    mRefChild.child(year).child(month).child("1").child("Numstuff").setValue(stuffNumInt+"");
                    mRefChild.child(year).child(month).child("1").child("status").setValue(statusStr);
                    mRefChild.child(year).child(month).child("1").child("date").setValue(date);
                    mRefChild.child(year).child(month).child("1").child("totalPeople").setValue(groupNumInt-stuffNumInt + "");
                    mRefChild.child(year).child(month).child("1").child("totalMoney").setValue((float)((groupNumInt-stuffNumInt)*1.38/handsOnGroupInt) + "");

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

/*
                    Firebase mRefChild = myRef.child("Porterage").child(myUserID);
        mRefChild.push();
        mRefChild.child(year).child(month).child("Numpeople").setValue(groupNumInt);
        mRefChild.child(year).child(month).child("Numhands").setValue(handsOnGroupInt);
        mRefChild.child(year).child(month).child("Numstuff").setValue(stuffNumInt);
        mRefChild.child(year).child(month).child("status").setValue(statusStr);*/


    }


    public static void updateReduce(final String userID ,final float toReduce) {

        final Firebase mRefChild = myRef.child("ToReduce").child(userID);

        mRefChild.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    float wasToReduce =  0;
                    wasToReduce = (float) Double.parseDouble(dataSnapshot.child("sum").getValue().toString());
                    getTotal(userID);
                    if(wasToReduce + toReduce > totalSum )
                    {
                        return;
                    }



                    try {
                        mRefChild.child("sum").setValue(((float)(wasToReduce+toReduce)+""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else
                {
                    getTotal(userID);
                    if(toReduce > totalSum )
                    {
                        return;
                    }
                    try {
                        mRefChild.child("sum").setValue(((float)toReduce)+"");
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


    public static void getTotal(String myID)
    {


        Query query = myRef.child("Porterage").child(myID);



        query.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                totalSum = 0;
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



                            }
                        }

                    }






                }





            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });



    }

}



