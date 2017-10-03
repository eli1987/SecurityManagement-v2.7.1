package com.example.oryossipof.securitymanagement;

      import android.*;
      import android.Manifest;
      import android.app.NotificationManager;
      import android.app.PendingIntent;
      import android.app.ProgressDialog;
        import android.content.Intent;
      import android.content.pm.ActivityInfo;
      import android.content.pm.PackageManager;
      import android.graphics.Bitmap;
      import android.graphics.Matrix;
      import android.net.Uri;
      import android.os.Build;
      import android.os.Environment;
      import android.provider.MediaStore;
      import android.provider.Settings;
      import android.support.annotation.NonNull;
      import android.support.v4.app.ActivityCompat;
      import android.support.v4.content.ContextCompat;
      import android.support.v4.media.app.NotificationCompat;
      import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.firebase.client.Firebase;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
      import com.google.firebase.storage.UploadTask;
      import java.io.File;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
      import java.util.Locale;

public class AddEventAct extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private TextView dateTxt ,eventText;
    private Firebase myRef;
    private Button showEventsBtt ,addEventBtClass, cancelBT,photoBT;
    private String currentDateandTime;
    private String currentDateandTime2;
    private String currentDateandTime3;
    private  String myUsername,myID;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri uri;
    private StorageReference storageRef;
    private ProgressDialog progressbar;
    private final int GALLERY = 1;
   private  Uri donwloadImage = null;
    private android.support.v4.app.NotificationCompat.Builder notification;
    private final static int uniID = 65411 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        this.myUsername = intent.getStringExtra("myUsername");
        this.myID = intent.getStringExtra("myID");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy "/* hh:mm*/);
        currentDateandTime = sdf.format(new Date());
        dateTxt = (TextView)findViewById(R.id.datetxt);
        dateTxt.setText(currentDateandTime);

        notification = new android.support.v4.app.NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        progressbar = new ProgressDialog(this);
        myRef = new Firebase("https://securitymanagment-2427a.firebaseio.com/");


                // Take this
        storageRef = FirebaseStorage.getInstance().getReference();
        progressbar = new ProgressDialog(this);

        //

        eventText = (EditText) findViewById(R.id.EventText);

        addEventBtClass =  (Button) findViewById(R.id.addEventBT);
        addEventBtClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy hh:mm:ss "/* hh:mm*/);
                currentDateandTime = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy HH:mm " , Locale.US/* hh:mm*/);
                currentDateandTime2 = sdf2.format(new Date());

                String str = eventText.getText().toString();
                if(!str.equals("")) {
                    Log.e("Add event:", "addEventBtClass onClick");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy"/* hh:mm*/);
                    currentDateandTime3 = sdf3.format(new Date());
                    SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss",   Locale.US );

                    String hour_minutes = sdf4.format(new Date());

                    if(donwloadImage != null) {
                        String stringUri;
                        stringUri = donwloadImage.toString();
                        DataBase.addEventToDataBase(currentDateandTime3, myUsername, str, hour_minutes, currentDateandTime2, stringUri);
                       setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "Event successfully registered to the logbook!", Toast.LENGTH_LONG).show();

                   }
                    else{
                        DataBase.addEventToDataBase(currentDateandTime3, myUsername, str, hour_minutes, currentDateandTime2, "");
                        setResult(RESULT_OK, null);
                        Toast.makeText(getBaseContext(), "Event successfully registered to the logbook!", Toast.LENGTH_LONG).show();
                        bulidNotfication();
                    }


                    Intent intent = new Intent(AddEventAct.this, LogbookEvents.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(getBaseContext(), "Please enter a description", Toast.LENGTH_LONG).show();
                }


            }
        });



                // take this 2
        photoBT = (Button)findViewById(R.id.attachPhotobt);
        photoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.setType("image/*");

                startActivityForResult(intent ,GALLERY);
            }
        });

        showEventsBtt = (Button)findViewById(R.id.showEventsBt);
        showEventsBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventAct.this, LogbookEvents.class);
                intent.putExtra("myID", myID);
                intent.putExtra("myUsername",myUsername);
                startActivity(intent);
            }
        });


        cancelBT = (Button)findViewById(R.id.cancelBT);
        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }



    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {

            progressbar.setMessage("uploading...");
            progressbar.show();
            Uri uri = data.getData();

            StorageReference filePath = storageRef.child("Eventsimages/"+uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    donwloadImage = taskSnapshot.getDownloadUrl();
                    progressbar.setMessage("Uploading file finished...");
                    progressbar.dismiss();
                    Toast.makeText(getBaseContext(), "Upload done successfully", Toast.LENGTH_LONG).show();
                    bulidNotfication();
                }
            });


        }



    }

    public void  bulidNotfication( ){


        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("New event added");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("New event added");
        notification.setContentText("Something has happen ");

        Intent intent = new Intent(this,LogbookEvents.class) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniID,notification.build());

    }
}
