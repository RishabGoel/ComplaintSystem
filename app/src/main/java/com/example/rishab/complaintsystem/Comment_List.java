package com.example.rishab.complaintsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Comment_List extends ActionBarActivity {

    JSONArray coms = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__list);
        ListView listView = (ListView) findViewById(R.id.lv2);

        Intent intent_r = getIntent();

        JSONObject data_received = new JSONObject();//JSON Object which will be parsed throughout this assignment

        String js = intent_r.getStringExtra("data");

        System.out.println(intent_r.getStringExtra("data"));

        try {
            data_received = new JSONObject(js);//type casting to JSON object
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<HashMap<String, String>> comment_list = new ArrayList<HashMap<String, String>>();

        try{if(data_received.getInt("status")==1){

            coms = data_received.getJSONArray("data");

            for(int i=0;i<coms.length();i++)
            {
                JSONObject c = coms.getJSONObject(i);

                String name = c.getString("name");

                String description = c.getString("description");



                String dateStr = c.getString("time_created");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date birthDate = new Date();
                try{birthDate = sdf.parse(dateStr);}
                catch(Exception e){e.printStackTrace();}
                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s = formatter.format(birthDate);



                String total = name + " : " + description;

                HashMap<String,String> item = new HashMap<String,String>();

                item.put("total",total);
                item.put("time",s);

                comment_list.add(item);

                ListAdapter adapter = new SimpleAdapter
                        (
                                Comment_List.this,
                                comment_list,
                                R.layout.comment_list_item,
                                new String[]{"total", "time"},
                                new int[]{R.id.comment, R.id.postedat}
                        );

                listView.setAdapter(adapter);
            }


        }
        else
        {
            Toast.makeText(getApplicationContext(), "No data received. Please exit and login again",
                    Toast.LENGTH_SHORT).show();
        }
        }
        catch (JSONException E) {
            E.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment__list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}