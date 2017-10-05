package com.example.oryossipof.securitymanagement;

import android.*;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LostAndFoundsActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private String myID,myUsername;
    private Button dateBt ,addLostBt ,photoBt,showDocumentation;
    private Context context;
    private int day,month,year;
    private DatePicker calendar;
    private DatePickerDialog datepick;
    private TextView dateTextview;
    private EditText lostDescrptionEditText,whereLostFoundEditText,whoFoundText;
    private  static int lostNumber = 0  ;
    private  String whoFound,whereLostFound,lostDescrption;
    private static String currentMonth;
    private StorageReference storageRef;
    private ProgressDialog progressbar;
    private final int GALLERY = 1;
    private  Uri donwloadImage = null;
    private  Firebase myRef;
    private  long value =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_founds);

        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        context = this;

        dateTextview = (TextView) findViewById(R.id.dateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy "/* hh:mm*/);
        String currentDateandTime = sdf.format(new Date());
        dateTextview.setText(currentDateandTime);

        lostDescrptionEditText = (EditText) findViewById(R.id.lostDescrptionText);
        whereLostFoundEditText = (EditText) findViewById(R.id.whereLostFoundText);
        whoFoundText = (EditText) findViewById(R.id.whoFoundText);

        storageRef = FirebaseStorage.getInstance().getReference();
        progressbar = new ProgressDialog(this);


        addLostBt = (Button) findViewById(R.id.addLostBt);
        addLostBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"/* hh:mm*/);
                    String NewcurrentMonth = sdf.format(new Date());

                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM-yyyy"/* hh:mm*/);
                    String dateofLost = sdf2.format(new Date());

                    if(donwloadImage != null) {


                        String stringUri;
                        stringUri = donwloadImage.toString();
                        lostNumber += 1;
                        DataBase.addLostToFireBaseDataBase(lostNumber, lostDescrption, whereLostFound, whoFound, dateofLost, myUsername,stringUri ,NewcurrentMonth );

                        setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "Lost successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        DataBase.addLostToFireBaseDataBase(lostNumber, lostDescrption, whereLostFound, whoFound, dateofLost, myUsername,"",NewcurrentMonth);

                        setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "Lost successfully registered!", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        photoBt = (Button) findViewById(R.id.dateBt);
        photoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(LostAndFoundsActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(LostAndFoundsActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request
                }
                else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.setType("image/*");
                    startActivityForResult(intent ,GALLERY);

                }

            }
        });


        showDocumentation = (Button)findViewById(R.id.DocumentationBt);
        showDocumentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostAndFoundsActivity.this, LostAndFoundDocumentation.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                startActivity(intent);
            }
        });

    }


    public boolean validate() {
        boolean valid = true;

        lostDescrption = lostDescrptionEditText.getText().toString();
         whereLostFound = whereLostFoundEditText.getText().toString();
         whoFound = whoFoundText.getText().toString();


        if(lostDescrption.isEmpty()) {
            lostDescrptionEditText.setError("Must fill all fields");
            valid = false;
        }
        else if(whereLostFound.isEmpty( )){
            whereLostFoundEditText.setError("Must fill all fields");
            valid = false;
        }
        else if(whoFound.isEmpty()){
            whoFoundText.setError("Must fill all fields");
            valid = false;
        }
        else {
            lostDescrptionEditText.setError(null);
            whereLostFoundEditText.setError(null);
            whoFoundText.setError(null);

        }

        return valid;
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {

            progressbar.setMessage("uploading...");
            progressbar.show();
            Uri uri = data.getData();

            StorageReference filePath = storageRef.child("LostAndFoundImages/"+uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    donwloadImage = taskSnapshot.getDownloadUrl();
                    progressbar.setMessage("Uploading file finished...");
                    progressbar.dismiss();
                    Toast.makeText(getBaseContext(), "Upload done successfully", Toast.LENGTH_LONG).show();
                }
            });


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.setType("image/*");
                    startActivityForResult(intent ,GALLERY);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getBaseContext(), "Sorry without this permission \n Can not take a picture", Toast.LENGTH_LONG).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
