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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


public class Complaint_List extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__list);
        ListView listView = (ListView) findViewById(R.id.lv1);

        Intent intent_r = getIntent();

        JSONObject data_received = new JSONObject();

        String js = intent_r.getStringExtra("data");

        try {
            data_received = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<HashMap<String, String>> complaint_list = new ArrayList<HashMap<String, String>>();

        if (data_received != null) {
            try {
                JSONArray complaints = data_received.getJSONArray("complaints");

                for (int i = 0; i < complaints.length(); i++) {

                    JSONObject c = complaints.getJSONObject(i);

                    String initial_letter = c.getString("name");

                    String title = c.getString("title");

                    String upv = c.getInt("upvotes");

                    String downv = c.getInt("downvotes");

                    HashMap<String, String> item = new HashMap<String, String>();

                    item.put("key1",initial_letter[0]);
                    item.put("key2",title);
                    item.put("key3","+"+(Integer.toString)upv);
                    item.put("key4","+"+(Integer.toString)downv);
                    item.put("key5","Added By " + initial_letter);

                    complaint_list.add(item);

                    ListAdapter adapter = new SimpleAdapter
                            (
                                    Complaint_List.this,
                                    complaint_list,
                                    R.layout.complaint_list_item,
                                    new String[]{"key1", "key2", "key3","key4","key5"},
                                    new int[]{R.id.initials, R.id.title, R.id.upvotes, R.id.downvotes, R.id.added_by};
                            );

                    lv1.setAdapter(adapter);


                }
            }catch (JSONException E) {
                E.printStackTrace();
            }
        }
        else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint__list, menu);
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
