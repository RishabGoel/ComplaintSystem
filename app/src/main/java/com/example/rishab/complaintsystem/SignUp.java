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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class SignUp extends ActionBarActivity {

    int residence,designation,type;
    String firstName,lastName,password,phone,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner residenceSpinner = (Spinner) findViewById(R.id.residence);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(this,
                R.array.residence_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        residenceSpinner.setAdapter(adapterLevel);


        //level = spinnerLevel.getSelectedItemPosition();

        residenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                residence = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Spinner designationSpinner = (Spinner) findViewById(R.id.designation);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLevel2 = ArrayAdapter.createFromResource(this,
                R.array.designation_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterLevel2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        designationSpinner.setAdapter(adapterLevel2);


        //level = spinnerLevel.getSelectedItemPosition();

        designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                designation = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLevel3 = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterLevel3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typeSpinner.setAdapter(adapterLevel3);


        //level = spinnerLevel.getSelectedItemPosition();

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                type = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        //Action on done button

        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditText first_name = (EditText) findViewById(R.id.first_name);
                EditText last_name = (EditText) findViewById(R.id.last_name);
                EditText password_text = (EditText) findViewById(R.id.password);
                EditText username_text = (EditText) findViewById(R.id.username);
                EditText phone_text = (EditText)    findViewById(R.id.phone);

                String residenceString = "";
                String designationString="";
                username = username_text.getText().toString();
                password = password_text.getText().toString();
                firstName = first_name.getText().toString();
                lastName = last_name.getText().toString();
                phone = phone_text.getText().toString();

                switch(residence){

                    case 0:residenceString="Outside_Campus";break;
                    case 1:residenceString="Jwalamukhi";break;
                    case 2:residenceString="Kumaun";break;
                    case 3:residenceString="Satpura";break;
                    case 4:residenceString="Shivalik";break;
                    case 5:residenceString="Aravali";break;
                    case 6:residenceString="Karakoram";break;
                    case 7:residenceString="Vindhyachal";break;
                    case 8:residenceString="Zanskar";break;
                    case 9:residenceString="Nilgiri";break;
                    case 10:residenceString="Udaigiri";break;
                    case 11:residenceString="Girnar";break;
                    case 12:residenceString="Kailash";break;
                    case 13:residenceString="Himadri";break;
                    default: break;

                }

                switch (designation){
                    case 0:designationString="Student";break;
                    case 1:designationString="Warden";break;
                    case 2:designationString="Electrician";break;
                    case 3:designationString="Plumber";break;
                    case 4:designationString="Carpenter";break;
                    case 5:designationString="Dean";break;
                    case 6:designationString="Maintenance_Secy";break;
                    case 7:designationString="House_Secy";break;
                    case 8:designationString="Cultural_Secy";break;
                    case 9:designationString="Sports_Secy";break;
                }


                System.out.println(username+" "+password+" "+firstName+" "+lastName+" "+phone+" "+residenceString+" "+designationString+" "+type);

                String url = "authentication/signUp/"+username+"/"+firstName+"-"+lastName+"/"+phone+"/"+residenceString+"/"+designationString+"/"+type+"/"+password;
                System.out.println(url);




                Intent target = new Intent(SignUp.this, MainActivity.class);

                //SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPref.edit();
                //editor.putInt("user_type", level);
                //editor.commit();

               // SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

                //des = sharedPref.getString("designation","");

                //String user_id = Integer.toString(sharedPref.getInt("user_id", 0));
                //String location = sharedPref.getString("location","");

                //String requestUrl = "complaintController/addComplaint/"+user_id+"/"+level+"/"+complaintTitle+"/"+concernedAuthString+"/"+complaintDesc+"/"+location+"/"+resolvingAdmins;

                RequestManager manager = new RequestManager(target,SignUp.this,url,0);
                manager.request();





            }

        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
