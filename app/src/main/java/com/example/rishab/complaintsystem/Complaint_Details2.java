package com.example.rishab.complaintsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Complaint_Details2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__details2);

        TextView t1 = (TextView)findViewById(R.id.title1);
        TextView t2 = (TextView)findViewById(R.id.desc);
        TextView t3 = (TextView)findViewById(R.id.name);
        TextView t4 = (TextView)findViewById(R.id.date);
        TextView t5 = (TextView)findViewById(R.id.auth);

        Intent intent_r = getIntent();

        //Converting into JSON Object

        String js = intent_r.getStringExtra("data");

        JSONObject data_received = new JSONObject();

        try {
            data_received = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{

            JSONArray complaint_detail = data_received.getJSONArray("data");

            JSONObject c = complaint_detail.getJSONObject(0);

            String title = c.getString("title");
            t1.setText(title);

            String description = c.getString("description");
            t2.setText(description);

            String name = c.getString("name");
            t3.append(name);

            String dateStr = c.getString("created");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = new Date();
            try{birthDate = sdf.parse(dateStr);}
            catch(Exception e){e.printStackTrace();}
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = formatter.format(birthDate);

            t4.append(s);

            String auth = c.getString("designation");  //needs to be taken care of in previous activity
            t5.append(auth);


            //Checkbox for resolved
            CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
            String r = Integer.toString(c.getInt("is_ressolved"));

            if(r.equals("0"))
            {
                cb.setText("Not Resolved");
                cb.setChecked(false);
            }

            else
            {
                cb.setText("Resolved");
                cb.setChecked(true);
            }

            cb.setClickable(false);




        }

        catch(JSONException e)
        {
            e.printStackTrace();
        }
          /*
        String title = intent_r.getStringExtra("Title");
        t1.setText(title);

        String description = intent_r.getStringExtra("Description");
        t2.setText(description);

        String name = intent_r.getStringExtra("Name");
        t3.append(name);

        String date = intent_r.getStringExtra("Date");  //needs to be taken care of in previous activity
        t4.append(date);

        String auth = intent_r.getStringExtra("Authority");  //needs to be taken care of in previous activity
        t5.append(auth);

        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        String r = intent_r.getStringExtra("isResolved");

        if(r.equals("0"))
        {
            cb.setText("Not Resolved");
            cb.setChecked(false);
        }

        else
        {
            cb.setText("Resolved");
            cb.setChecked(true);
        }

        cb.setClickable(false);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint__details2, menu);
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
