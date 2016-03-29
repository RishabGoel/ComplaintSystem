package com.example.rishab.complaintsystem;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Complaint System: Login");

        //creating a spinner for login type
        Spinner spinnerLevel = (Spinner) findViewById(R.id.loginType);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(this,
                R.array.login_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerLevel.setAdapter(adapterLevel);


        //level = spinnerLevel.getSelectedItemPosition();

        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                level = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        TextView signUpButton = (TextView) findViewById(R.id.signUpButton);
        //Log.d("fdsfds", addDescriptionButton.getText().toString());
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intencion = new Intent(MainActivity.this,SignUp.class );
                startActivity(intencion);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendLoginRequest(View view) {

        String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        String requestUrl = "authentication/login/" + username.toString() + "/" + password.toString()+"/"+level ;
        System.out.println(requestUrl);

       Intent target;
       if(level<2) {
         target =new Intent(MainActivity.this, Complaint_List.class);
       }
        else{
           target =new Intent(MainActivity.this, PendingRequests.class);
       }

        SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_type", level);
        editor.commit();

        RequestManager manager = new RequestManager(target,this,requestUrl,0);
        manager.request();

        //Context context = MainActivity.this;

        //Toast.makeText(context, "Invalid Username or Password",

        //Toast.LENGTH_SHORT).show();

        //Intent intencion = new Intent(MainActivity.this,AddComplaint.class );
        //startActivity(intencion);

        //Intent target = new Intent(MainActivity.this, ComplaintList.class);
        //Re q = new Re(target, MainActivity.this, requestUrl, 0);
        //q.request();
    }
}