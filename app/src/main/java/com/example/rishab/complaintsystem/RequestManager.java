package com.example.rishab.complaintsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rishab on 27-03-2016.
 */
public class RequestManager {
    Context context;
    Intent intent;
    //    String url="http://10.192.57.72:8000";
//    String url="http://192.168.0.100:8000";
    String url="http://10.0.2.2/BackendComplaintSystem/index.php/";
    String URL="http://10.192.57.72:8000/default/login.json?userid=vinay&password=vinay";
    int reqtype;



    public RequestManager(Intent i, Context c, String u, int t){
        context=c;
        url+=u;
        intent=i;
        reqtype=t;


    }
    public void request() {
        Log.d("url", url);
        Log.d("dffgdf","entered");
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        final int user=sharedPref.getInt("user_id", -1);
        final int admin=sharedPref.getInt("admin", -1);
        final String location=sharedPref.getString("location", "");
        final String designation=sharedPref.getString("designation", "");

        if (reqtype == 1) {
            Log.d("check","ent");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("user_id", -1);
            editor.apply();

            context.startActivity(new Intent(context, MainActivity.class));
            return;
        }
        else {
            Log.d("check","entere");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    //add the logic after recieving the data
                    Log.d("fd", string);
                    try {
                        if(reqtype==0) {
                            intent.putExtra("data", string);
                            context.startActivity(intent);
                        }
                        JSONObject data = new JSONObject("{s:'s'}");
                    } catch (JSONException e) {
                        Log.d("check", "error in getting the threads");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError string) {
                    Log.d("check", string.toString());
                    Toast.makeText(context, "Network Error : Something went wrong",
                            Toast.LENGTH_SHORT).show();

                }

                ;
            }) {
                @Override
                protected Map<String, String> getParams() {
                    //this function converts the the entered data into the format to be sent to
                    // the server

                    Map<String, String> req = new HashMap<String, String>();
                    req.put("user_id", Integer.toString(user));
                    req.put("admin", Integer.toString(admin));
                    req.put("location", location);
                    req.put("designation", designation);
                    return req;
                }
            };
            //the following is the global request queue to prevent construction of
            // request queue again and again
            SingletonNetworkClass.getInstance(context).addToRequestQueue(stringRequest);

            //        queue.add(stringRequest);
        }
    }
}
