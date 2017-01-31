package com.example.codevs.pintxoway;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Borja on 27/01/2017.
 */

public class PintxoService{

    private static PintxoService mInstance;
    private RequestQueue requestQueue;
    private static Context context;
    private String serverPath="10.110.4.144:3000";

    private PintxoService(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized PintxoService getInstance(Context context){

        if(mInstance == null){
            mInstance  = new PintxoService(context);
        }
        return mInstance;
    }

    public void addToRequestQue(Request request){
        requestQueue.add(request);
    }

    public String getDistancePath(String lat, String lon, String rad){
        return serverPath+"/local/list?lat="+lat+"&long="+lon+"&rad="+rad;
    }
}
