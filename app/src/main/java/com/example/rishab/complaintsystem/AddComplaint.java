package com.example.rishab.complaintsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AddComplaint extends Activity {
    private int PICK_IMAGE_REQUEST = 1;
    private String img;

    //function to validate strings

    public boolean validate(String s){
        boolean res = false;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '&&s.charAt(i)!='\n') return true;
        }
        return  res;
    }

    String complaintDesc = "";
    String complaintTitle = "";
    String resolvingAdmins = "";
    int concernedAuth;
    int level;

    private TextView addDescriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);


        //Spinner (Complaint level)

        Spinner spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(this,
                R.array.level_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerLevel.setAdapter(adapterLevel);


        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                level = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        //Spinner (Authority concerned)

        Spinner spinnerAuthority = (Spinner) findViewById(R.id.spinnerAuthority);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterAuthority = ArrayAdapter.createFromResource(this,
                R.array.auth_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterAuthority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerAuthority.setAdapter(adapterAuthority);

        spinnerAuthority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                concernedAuth = i;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        addDescriptionButton = (TextView) findViewById(R.id.addDescriptionButton);
        //Log.d("fdsfds", addDescriptionButton.getText().toString());
        addDescriptionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(AddComplaint.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.add_complaint_description);
                // Set dialog title
                dialog.setTitle("Complaint Description");

                // set values for custom dialog components - text, image and button

                dialog.show();

                TextView doneButton = (TextView) dialog.findViewById(R.id.doneButton);

                final EditText desc = (EditText) dialog.findViewById(R.id.description);
                desc.setText(complaintDesc);



               ;

                //Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        complaintDesc = desc.getText().toString();

                        dialog.dismiss();
                    }
                });
            }

        });



        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText title = (EditText)findViewById(R.id.editTextSubject);
                complaintTitle = title.getText().toString();

                EditText resolvingAdminsText = (EditText) findViewById(R.id.resolvingAdminsText);
                resolvingAdmins = resolvingAdminsText.getText().toString();

                String levelString="";
                String concernedAuthString="";

                switch(level){
                    case 0: levelString = "Personal";
                        break;
                    case 1: levelString = "Hostel";
                        break;
                    case 2: levelString = "Institute";
                        break;
                    default:break;
                }

                switch (concernedAuth){
                    case 0:concernedAuthString="Warden";break;
                    case 1:concernedAuthString="Electrician";break;
                    case 2:concernedAuthString="Plumber";break;
                    case 3:concernedAuthString="Carpenter";break;
                    case 4:concernedAuthString="Dean";break;
                    case 5:concernedAuthString="Maintenance_Secy";break;
                    case 6:concernedAuthString="House_Secy";break;
                    case 7:concernedAuthString="Cultural_Secy";break;
                    case 8:concernedAuthString="Sports_Secy";break;
                }

                if(validate(complaintDesc)&&validate(complaintTitle)){
                    System.out.println(complaintTitle+"/"+complaintDesc);
                    System.out.println("level="+levelString);
                    System.out.println("auth="+concernedAuthString);



                    Intent target = new Intent(AddComplaint.this, Complaint_List.class);

                    //SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                    //SharedPreferences.Editor editor = sharedPref.edit();
                    //editor.putInt("user_type", level);
                    //editor.commit();

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

                    //des = sharedPref.getString("designation","");

                    String user_id = Integer.toString(sharedPref.getInt("user_id", 0));
                    String location = sharedPref.getString("location","");

                    String requestUrl = "complaintController/addComplaint/"+user_id+"/"+level+"/"+complaintTitle+"/"+concernedAuthString+"/"+complaintDesc+"/"+location+"/"+resolvingAdmins;

                    RequestManager manager = new RequestManager(target,AddComplaint.this,requestUrl,0);
                    manager.request();





                }

                else{
                    Context context = AddComplaint.this;
                    Toast.makeText(context, "Title and Description could not be Empty!",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.menu_add_complaint, menu);
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

    public  void as(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                img = getStringImage(MediaStore.Images.Media.getBitmap(getContentResolver(), filePath));
                //Setting the Bitmap to ImageView

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
