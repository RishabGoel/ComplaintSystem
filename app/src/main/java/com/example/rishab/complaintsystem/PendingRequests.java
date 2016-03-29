package com.example.rishab.complaintsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class PendingRequests extends ActionBarActivity {
    JSONArray users = new JSONArray();
    String[] Name, Phone, Address, Designation,Username;
    String designation = "";
    int us_id;
    String location="";
    int admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);
        ListView listView=(ListView) findViewById(R.id.pend_requests);

        Intent intent_r = getIntent();

        JSONObject data_received = new JSONObject();

        String js = intent_r.getStringExtra("data");

        //final JSONArray complaints = new JSONArray();

        try {
            data_received = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            ArrayList<HashMap<String, String>> complaint_list = new ArrayList<HashMap<String, String>>();
            try{if (data_received.getInt("status")==1) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                users = data_received.getJSONArray("data");
                if(data_received.getInt("add")==1) {


                    //admin = data_received.getInt("admin");
                    us_id = data_received.getInt("user_id");
                    designation = data_received.getString("designation");
                    location = data_received.getString("location");


                    //Shared Preferences Code


                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("user_id", us_id);
                    editor.putInt("admin", admin);
                    editor.putString("location", location);
                    editor.putString("designation", designation);
                    editor.commit();


                }
                Name=new String[users.length()];
                Phone=new String[users.length()];
                Designation=new String[users.length()];
                Address=new String[users.length()];
                Username=new String[users.length()];

                for (int i = 0; i < users.length(); i++) {

                    JSONObject c = users.getJSONObject(i);

                    Name[i] = c.getString("name");

                    Phone[i] = c.getString("phone");

                    Address[i] = c.getString("address");

                    Designation[i] = c.getString("designation");

                    Username[i] = c.getString("username");






                }
            }
            else {
                //Log.e("ServiceHandler", "Couldn't get any data from the url");
                Toast.makeText(getApplicationContext(), "Invalid username or password or user_type",
                        Toast.LENGTH_SHORT).show();
                this.finish();
            }}
            catch (JSONException E) {
                E.printStackTrace();
            }







        String[] a={"sfdsf","sfdsf","sfdsf","sfdsf"};
        try {
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.pending_request_listview,R.id.pend_req_users,Name);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(PendingRequests.this,UserDetails.class);
                    intent.putExtra("name",Name[position]);
                    intent.putExtra("phone",Phone[position]);
                    intent.putExtra("address",Address[position]);
                    intent.putExtra("designation",Designation[position]);
                    intent.putExtra("username",Username[position]);
                    startActivity(intent);

                }
            });
        }
        catch (Exception e){
            int rt=1;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending_requests, menu);
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
            RequestManager req = new RequestManager(intent, PendingRequests.this, "complaintcontroller/showcomplaint//"+Integer.toString(us_id), 1);//course assignment api
            req.request();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
