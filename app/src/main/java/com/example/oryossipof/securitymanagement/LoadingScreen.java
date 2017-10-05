package com.example.oryossipof.securitymanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpDateGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

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


       // new UpdateTask().execute();


        progress = (ProgressBar) findViewById(R.id.progressBar3);
        progress.setRotation(180);
        String color = colorDecToHex(34, 0, 255);

        // Define a shape with rounded corners
        final float[] roundedCorners = new float[]{5, 5, 5, 5, 5, 5, 5, 5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));

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
                while (progressStatus < 100) {
                    progressStatus += 2;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
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

    public static String colorDecToHex(int p_red, int p_green, int p_blue) {
        String red = Integer.toHexString(p_red);
        String green = Integer.toHexString(p_green);
        String blue = Integer.toHexString(p_blue);

        if (red.length() == 1) {
            red = "0" + red;
        }
        if (green.length() == 1) {
            green = "0" + green;
        }
        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        String colorHex = "#" + red + green + blue;
        return colorHex;
    }
/*
    private class UpdateTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {


             HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://10.0.0.2/secuirtyManagement/fcm_insert.php");
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            JSONObject obj = new JSONObject();
            try {
                obj.put("token", token);
                post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
                HttpResponse response = client.execute(post);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            StringBuffer sb = new StringBuffer();
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic("news");
            String token = FirebaseInstanceId.getInstance().getToken();
            // [END subscribe_topics]

            Log.e("Eli", token + "");
            String urladd = "http://10.0.0.2/secuirtyManagement/fcm_insert.php?token=540";
            URL ur = null;
            try {
                ur = new URL(urladd);
                HttpURLConnection url = (HttpURLConnection) ur.openConnection();
                Log.e("Eli", token + "");
                url.setRequestMethod("GET");
                url.setConnectTimeout(20000);
                url.setReadTimeout(2000);
                url.setDoInput(true);
                url.setDoInput(true);
                JSONObject jo = new JSONObject();
                StringBuffer packedData = new StringBuffer();
                Log.e("Eli", token + "");
                jo.put("token", token);

                Boolean firstValue = true;
                Iterator it = jo.keys();
                do {
                    Log.e("Eli", token + "");
                    String key = it.next().toString();
                    String value = jo.get(key).toString();
                    if (firstValue) {
                        firstValue = false;
                    } else {
                        packedData.append("&");
                    }
                    packedData.append(URLEncoder.encode(key, "UTF-8"));
                    packedData.append("=");
                    packedData.append(URLEncoder.encode(value, "UTF-8"));
                } while (it.hasNext());
               String p =  packedData.toString();

                url.connect();
                Log.e("Eli", token + "");

                if (url == null) {
                    return null;
                }

                try {
                    OutputStream os = url.getOutputStream();
                    //WRITE
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    bw.write(p);
                    bw.flush();
                    //RELEASE RES
                    bw.close();
                    os.close();

                    int responseCode = url.getResponseCode();
                    if (responseCode == url.HTTP_OK) {
                        //GET EXACT RESPONSE
                        BufferedReader br = new BufferedReader(new InputStreamReader(url.getInputStream()));
                        StringBuffer response = new StringBuffer();
                        String line;
                        //READ LINE BY LINE
                        Log.e("Eli","OK");
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }
                        //RELEASE RES
                        br.close();
                        return response.toString();
                    } else {
                        Log.e("Eli","NOT OK");
                    }


                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

    }*/
}
