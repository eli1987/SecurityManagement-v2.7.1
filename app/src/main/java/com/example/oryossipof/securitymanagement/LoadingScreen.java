package com.example.oryossipof.securitymanagement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoadingScreen extends AppCompatActivity {
private ProgressBar progress;
    private Handler handler = new Handler();
    int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);


    progress = (ProgressBar)findViewById(R.id.progressBar3);
        progress.setRotation(180);
        String color = colorDecToHex(34, 0, 255);

        // Define a shape with rounded corners
        final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners,     null, null));

        // Sets the progressBar color
        pgDrawable.getPaint().setColor(Color.parseColor(color));

        // Adds the drawable to your progressBar
        ClipDrawable p = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progress.setProgressDrawable(p);

        // Sets a background to have the 3D effect
        progress.setBackgroundDrawable(LoadingScreen.this.getResources()
                .getDrawable(android.R.drawable.progress_horizontal));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus<100)
                {
                    progressStatus+=2;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();   //finish this so when come back after that exit app

            }
        }).start();
        //



    }

    public static String colorDecToHex(int p_red, int p_green, int p_blue)
    {
        String red = Integer.toHexString(p_red);
        String green = Integer.toHexString(p_green);
        String blue = Integer.toHexString(p_blue);

        if (red.length() == 1)
        {
            red = "0" + red;
        }
        if (green.length() == 1)
        {
            green = "0" + green;
        }
        if (blue.length() == 1)
        {
            blue = "0" + blue;
        }

        String colorHex = "#" + red + green + blue;
        return colorHex;
    }
}
