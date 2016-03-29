package com.example.rishab.complaintsystem;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UserDetails extends ActionBarActivity {

    String username, name, residence, designation, phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        name = intent.getStringExtra("name");
        designation = intent.getStringExtra("designation");
        residence = intent.getStringExtra("residence");
        phone = intent.getStringExtra("phone");


       // username = "parasgupta";
       // name = "Paras Gupta";
       // designation = "Student";
       // residence = "Jwalamukhi";
       // phone = "8979333000";

        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        TextView nameText = (TextView)  findViewById(R.id.nameText);
        TextView residenceText = (TextView) findViewById(R.id.residenceText);
        TextView designationText = (TextView)   findViewById(R.id.designationText);
        TextView phoneText = (TextView) findViewById(R.id.textPhone);

        usernameText.setText("Username:    "+username);
        nameText.setText("Name:        "+name);
        residenceText.setText("Residence:   "+residence);
        designationText.setText("Designation: " + designation);
        phoneText.setText("Phone no: " + phone);


        TextView addUserButton = (TextView) findViewById(R.id.addUserButton);
        TextView addSuperUserButton = (TextView) findViewById(R.id.addSuperUserButton);

        addUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                   System.out.println("added as a user");
                    String url = "authentication/approve/"+username+"/0";
                Intent target = new Intent(UserDetails.this, PendingRequests.class);
                RequestManager manager = new RequestManager(target,UserDetails.this,url,0);
                manager.request();
            }

        });

        addSuperUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                System.out.println("added as a super-user");
                String url = "authentication/approve/"+username+"/1";
                Intent target = new Intent(UserDetails.this, PendingRequests.class);
                RequestManager manager = new RequestManager(target,UserDetails.this,url,0);
                manager.request();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
