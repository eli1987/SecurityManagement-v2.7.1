package com.example.oryossipof.securitymanagement;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by אלי on 05/10/2017.
 */

public class MySinglton {
    private static MySinglton mInstance;
    private static  Context mCtx;
    private RequestQueue requestQueue;

    private MySinglton(Context context) {

        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue ==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;



    }

    public static  synchronized MySinglton getmInstance (Context context)
    {

        if(mInstance == null)
        {
            mInstance = new MySinglton(context);
        }
            return  mInstance ;

    }

    public<T> void addToRequestque(Request<T> request){

        getRequestQueue().add(request);

    }

}

