package com.example.rishab.complaintsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Complaint_List extends ActionBarActivity {

    JSONArray complaints = new JSONArray();
    int if_concerned = 0;
    int admin;
    String designation = "";
    int us_id;
    String location="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__list);
        ListView listView = (ListView) findViewById(R.id.lv1);

        Intent intent_r = getIntent();

        JSONObject data_received = new JSONObject();

        //Receiving data in form of string

        String js = intent_r.getStringExtra("data");

        //final JSONArray complaints = new JSONArray();

        try {
            data_received = new JSONObject(js);      //convert into JSON Object
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Displaying in List View
        ArrayList<HashMap<String, String>> complaint_list = new ArrayList<HashMap<String, String>>();


        //status 1 implies that user exsist
        try{if (data_received.getInt("status")==1) {

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE); //Shared Prefernce containing variables to be used
            complaints = data_received.getJSONArray("data");      //array of complaints
            if(data_received.getInt("add")==1) {


                //admin = data_received.getInt("admin");

                //retrieving user data
                us_id = data_received.getInt("user_id");
                //System.out.print(us_id);
                designation = data_received.getString("designation");

                location = data_received.getString("location");

                //Shared Preferences Code


                //putting data into Shared Preferences

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt("user_id", us_id);

                editor.putInt("admin", admin);

                editor.putString("location", location);

                editor.putString("designation", designation);

                editor.commit();


            }

            //if no complaints relevant to the user are present
            if(complaints.length()==0){Toast.makeText(getApplicationContext(), "Currently there are no complaints in your feed",
                    Toast.LENGTH_SHORT).show();}


            //retreiving if the user is normal,special or concerned authority
            if_concerned = sharedPref.getInt("user_type", 0);

            us_id = sharedPref.getInt("user_id",0);

            //System.out.print("fddsfsdfsfdssfs"+if_concerned);

            //Looping through the complaints array
            for (int i = 0; i < complaints.length(); i++) {

                JSONObject c = complaints.getJSONObject(i);

                String initial_letter = c.getString("name");

                String title = c.getString("title");

                int upv = c.getInt("upvotes");

                int downv = c.getInt("downvotes");

                HashMap<String, String> item = new HashMap<String, String>();


                //putting data into a list item

                item.put("key1",Character.toString(initial_letter.charAt(0)));
                item.put("key2",title);
                item.put("key3","+"+Integer.toString(upv));
                item.put("key4","-"+Integer.toString(downv));
                item.put("key5","Added By " + initial_letter);

                complaint_list.add(item);

                //creating a adapter

                ListAdapter adapter = new SimpleAdapter
                        (
                                Complaint_List.this,
                                complaint_list,
                                R.layout.complaint_list_item,
                                new String[]{"key1", "key2", "","","key5"},
                                new int[]{R.id.initials, R.id.title, R.id.upvotes, R.id.downvotes, R.id.added_by}
                        );

                listView.setAdapter(adapter);     //setting it to the listview


            }
        }

        //if the user doesn't exist
        else {
            //Log.e("ServiceHandler", "Couldn't get any data from the url");
            Toast.makeText(getApplicationContext(), "Invalid usename or password or usert type",
                    Toast.LENGTH_SHORT).show();
            this.finish();
        }}


        catch (JSONException E) {
            E.printStackTrace();
        }



        //setting events on list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long d) {
                Intent in = new Intent(getApplicationContext(), Complaint_Details.class);    //for normal user
                Intent in2 = new Intent(getApplicationContext(),Complaint_Details2.class);   //for concerned authority
                try

                {

                    JSONObject o = complaints.getJSONObject(position);

                    //JSONObject o = complaints.getJSONObject(position);
                    //updated version
                    String complaint_id = Integer.toString(o.getInt("complaint_id"));

                    if (if_concerned == 0) {
                        RequestManager req = new RequestManager(in, Complaint_List.this, "complaintcontroller/showcomplaint/" + complaint_id+"/"+Integer.toString(us_id), 0);//course assignment api
                        req.request();
                    } else {
                        RequestManager req = new RequestManager(in2, Complaint_List.this, "/complaintcontroller/showcomplaint/" + complaint_id+"/"+Integer.toString(us_id), 0);//course assignment api
                        req.request();
                    }



                }

                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        //Button for add complaints

        Button b = (Button)findViewById(R.id.comp);

        if(if_concerned==1){b.setClickable(false);}

        b.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent_s = new Intent(getApplicationContext(), AddComplaint.class);
                        startActivity(intent_s);
                    }
                });
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
        if (id == R.id.action_exit) {
            Intent intent = getIntent();
            RequestManager req = new RequestManager(intent, Complaint_List.this, "complaintcontroller/showcomplaint//"+Integer.toString(us_id), 1);//course assignment api
            req.request();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}