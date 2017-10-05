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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositsActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private String myID,myUsername;
    private Button dateBt ,addDepositBt ,photoBt,showDocumentation;
    private Context context;
    private int day,month,year;
    private DatePicker calendar;
    private DatePickerDialog datepick;
    private TextView dateTextview;
    private EditText depositDescriptionEditText,whoDeliverEditText, whoReceiveText;
    private  static int depositNumber = 0  ;
    private  String whoDeliver,whoRecive,depositDescription;
    private static String currentMonth;
    private StorageReference storageRef;
    private ProgressDialog progressbar;
    private final int GALLERY = 1;
    private Uri donwloadImage = null;
    private Firebase myRef;
    private  long value =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);
        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        context = this;

        dateTextview = (TextView) findViewById(R.id.dateText);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy "/* hh:mm*/);
        String currentDateandTime = sdf.format(new Date());
        dateTextview.setText(currentDateandTime);

        depositDescriptionEditText = (EditText) findViewById(R.id.depostitDescrptionText);
        whoReceiveText = (EditText) findViewById(R.id.whoDepositForText);
        whoDeliverEditText = (EditText) findViewById(R.id.whoDeliverText);

        storageRef = FirebaseStorage.getInstance().getReference();
        progressbar = new ProgressDialog(this);


        addDepositBt = (Button) findViewById(R.id.addDepositBt);
        addDepositBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"/* hh:mm*/);
                    String NewcurrentMonth = sdf.format(new Date());

                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM-yyyy"/* hh:mm*/);
                    String dateofDeposit = sdf2.format(new Date());

                    if(donwloadImage != null) {


                        String stringUri;
                        stringUri = donwloadImage.toString();
                        depositNumber += 1;
                        DataBase.addDepositToFireBaseDataBase(depositNumber, depositDescription, whoRecive, whoDeliver, dateofDeposit, myUsername,stringUri ,NewcurrentMonth );

                        setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "deposit successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        DataBase.addDepositToFireBaseDataBase(depositNumber, depositDescription, whoRecive, whoDeliver, dateofDeposit, myUsername,"",NewcurrentMonth);

                        setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "deposit successfully registered!", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });


        photoBt = (Button) findViewById(R.id.camBt);
        photoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(DepositsActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(DepositsActivity.this,
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
                Intent intent = new Intent(DepositsActivity.this, Deposits_Documentation.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                startActivity(intent);
            }
        });

    }


    public boolean validate() {
        boolean valid = true;

        depositDescription = depositDescriptionEditText.getText().toString();
        whoRecive = whoReceiveText.getText().toString();
        whoDeliver = whoDeliverEditText.getText().toString();


        if(depositDescription.isEmpty()) {
            depositDescriptionEditText.setError("Must fill all fields");
            valid = false;
        }
        else if(whoRecive.isEmpty( )){
            whoReceiveText.setError("Must fill all fields");
            valid = false;
        }
        else if(whoDeliver.isEmpty()){
            whoDeliverEditText.setError("Must fill all fields");
            valid = false;
        }
        else {
            depositDescriptionEditText.setError(null);
            whoReceiveText.setError(null);
            whoDeliverEditText.setError(null);

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

            StorageReference filePath = storageRef.child("Deposits/"+uri.getLastPathSegment());
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
