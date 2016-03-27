package com.example.rishab.complaintsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddComplaint extends Activity {

    String complaintDesc = "";
    String complaintTitle = "";

    private TextView addDescriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

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
                System.out.println(complaintTitle+"/"+complaintDesc);
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
}
