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
    String url="http://10.192.41.212:8000";
    String URL="http://10.192.57.72:8000/default/login.json?userid=vinay&password=vinay";
    int reqtype;
    int user,admin;

    public RequestManager(Intent i, Context c, String u, int t,int user, int admin){
        context=c;
        url+=u;
        intent=i;
        reqtype=t;
        this.user=user;
        this.admin=admin;
    }
    public void request() {
        Log.d("url", url);
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        final int user=sharedPref.getInt("user_id",-1);
        final int admin=sharedPref.getInt("admin",-1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                //add the logic after recieving the data
                try {
                    if (reqtype==0){
                        JSONObject temp = new JSONObject(string);
                        boolean success = temp.getBoolean("success");
                        if(success==true){
//                            Intent courseList = new Intent(context,CourseList.class);
//                            RequestManager login = new RequestManager(courseList,context,"/courses/list.json",1);
//                            login.request();
                        }
                        else {
                            Toast.makeText(context, "Invalid Username or Password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(reqtype==1){

                        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("user_id",user);
                        editor.putInt("admin",admin);
                        editor.commit();
                    }
                    else if(reqtype==3){
                        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("user_id",-1);
                        editor.putInt("admin",0);
                        editor.apply();
                        String course_code=intent.getStringExtra("coursecode3");
                        RequestManager req=new RequestManager(intent,context,"/courses/course.json/"+course_code+"/threads",1);
                        req.request();

                    }
                    else{
                        String a=intent.getStringExtra("id");
                        RequestManager req=new RequestManager(intent,context,"/threads/thread.json/"+a,1);
                        req.request();
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
        }){
            @Override
            protected Map<String, String> getParams() {
                //this function converts the the entered data into the format to be sent to
                // the server

                Map<String, String> req = new HashMap<String, String>();
                req.put("user_id", Integer.toString(user));
                req.put("admin", Integer.toString(admin));
                return req;
            }
        };
        //the following is the global request queue to prevent construction of
        // request queue again and again
        SingletonNetworkClass.getInstance(context).addToRequestQueue(stringRequest);

        //        queue.add(stringRequest);

    }
}
