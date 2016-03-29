package com.example.rishab.complaintsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Complaint_Details extends ActionBarActivity {

    String complaint_id="";
    int upvotes;
    int downvotes;
    String comment="";
    String des="";
    String user_id_link="";
    int if_action;
    CheckBox cb;
    TextView t6;
    int able;

    @Override

    //for normal user

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complaint__details);

        TextView t1 = (TextView)findViewById(R.id.title1);
        TextView t2 = (TextView)findViewById(R.id.desc);
        TextView t3 = (TextView)findViewById(R.id.name);
        TextView t4 = (TextView)findViewById(R.id.date);
        TextView t5 = (TextView)findViewById(R.id.auth);
        t6 = (TextView)findViewById(R.id.updo);
        TextView t7 = (TextView)findViewById(R.id.noofcomm);
        final Button b1 = (Button)findViewById(R.id.ub);
        final Button b2 = (Button)findViewById(R.id.db);


        Intent intent_r = getIntent();


        //Receiving data in form of string and converting it to JSON Object

        String js = intent_r.getStringExtra("data");

        JSONObject data_received = new JSONObject();

        try {
            data_received = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray complaint_detail = data_received.getJSONArray("data");   //Receving the complaint object

            JSONObject c = complaint_detail.getJSONObject(0);


            
            able = c.getInt("able");  //if the user can perform an action on complaint


            String title = c.getString("title");
            t1.setText(title);

            String description = c.getString("description");
            t2.setText(description);

            String name = c.getString("name");
            t3.append(name);


            //Converting TimestampSql to valid date and time
            String dateStr = c.getString("created");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date birthDate = new Date();
            try{birthDate = sdf.parse(dateStr);}
            catch(Exception e){e.printStackTrace();}
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = formatter.format(birthDate);

            t4.append(s);




            String auth = c.getString("designation");
            t5.append(auth);


            //Upvotes and Downvotes
            String updo = "+" + Integer.toString(c.getInt("upvotes")) + "    " + "-" + Integer.toString(c.getInt("downvotes"));
            t6.setText(updo);



            //String noc = Integer.toString(c.getInt("total_comments"))+" comments";  //needs to be taken care of in previous activity
            //t7.append(noc);

            //Checkbox for relving the complaint

            cb = (CheckBox)findViewById(R.id.checkBox);
            String r = Integer.toString(c.getInt("is_ressolved"));



            //if not already resolved
            if(r.equals("0"))
            {
                cb.setText("Not Resolved");
                cb.setChecked(false);
            }



            //if already resolved
            else
            {
                cb.setText("Resolved");
                cb.setChecked(true);
                cb.setClickable(false);
            }

            //Parsing a string containing all resolving admins
            List<String> stringList= Arrays.asList(c.getString("resolving_admin").toLowerCase().split("\\s*,\\s*"));

            upvotes = (c.getInt("upvotes"));

            downvotes = (c.getInt("downvotes"));

            complaint_id = Integer.toString(c.getInt("complaint_id"));


            //Retrieve the shared vaiables

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

            des = sharedPref.getString("designation", "");
            user_id_link = Integer.toString(sharedPref.getInt("user_id", 0));
            //if_action = sharedPref.getInt("flag",1);

            //if(if_action==0){b1.setClickable(false);b2.setClickable(false);}


            //to check if the user is one of the resolving admins

            int i;

            for(i=0;i<stringList.size();i++)
            {
                if(des.toLowerCase().equals(stringList.get(i)))
                {
                    break;
                }

            }

            System.out.println(i<stringList.size());


            //if a resolving admin and complaint not resolved already
            if(i<stringList.size() && !cb.isChecked())
            {
                cb.setClickable(true);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //cb.setChecked(true);

                        //cb.setClickable(false);

                        //cb.setText("Resolved");

                        RequestManager req = new RequestManager(getIntent(), Complaint_Details.this, "/complaintController/ressolveComplaint/" + complaint_id, 2);//course assignment api
                        req.request();    //api for marking it resolve

                        cb.setChecked(true);

                        cb.setClickable(false);

                        cb.setText("Resolved");

                    }
                });
            }


            //if not a resolving admin
            else if(i>=stringList.size())
            {
                //cb.setClickable(false);

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        //cb.setChecked(true);

                        //cb.setClickable(false);

                        //cb.setText("Resolved");

                        //RequestManager req = new RequestManager(getIntent(), Complaint_Details.this, "/complaintController/ressolveComplaint/" + complaint_id, 2);//course assignment api
                        //req.request();

                        cb.setChecked(false);

                        cb.setClickable(false);

                        //cb.setText("Resolved");

                        Toast.makeText(Complaint_Details.this, "You are not authorised to mark it resolve",
                                Toast.LENGTH_SHORT).show();

                    }
                });

            }


            //if already resolved
            else
            {
                cb.setClickable(false);

            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        //final Button b1 = (Button)findViewById(R.id.ub);
        //final Button b2 = (Button)findViewById(R.id.db);
        Button b3 = (Button)findViewById(R.id.addcom);    //for adding comments
        Button b4 = (Button)findViewById(R.id.shco);      //for showing list of comments

        //String comment = "";

        //Dialog box on click of adding comments button
        b3.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View arg0) {    //Create custom dialog object
                        final Dialog dialog = new Dialog(Complaint_Details.this);
                        // Include dialog.xml file
                        dialog.setContentView(R.layout.add_complaint_description);
                        // Set dialog title
                        dialog.setTitle("Comment Description");
                        // set values for custom dialog components - text, image and button
                        dialog.show();
                        TextView doneButton = (TextView) dialog.findViewById(R.id.doneButton);
                        final EditText desc = (EditText) dialog.findViewById(R.id.description);
                        //desc.setText(complaintDesc);    //Button declineButton = (Button) dialog.findViewById(R.id.declineButton);    // if decline button is clicked, close the custom dialog
                        doneButton.setOnClickListener(new View.OnClickListener() {
                                                          //@Override
                                                          public void onClick(View v) {            // Close dialog

                                                              RequestManager req = new RequestManager(new Intent(), Complaint_Details.this, "comment/addComment/" +user_id_link+"/"+ complaint_id + "/" + desc.getText().toString(), 2);//course assignment api
                                                              req.request();//api for adding comments
                                                              dialog.dismiss();
                                                              Toast.makeText(Complaint_Details.this, "Comment Added",
                                                                      Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                        );

                    }
                }
        );



        //api for showing comments
        b4.setOnClickListener(

                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent_s = new Intent(getApplicationContext(), Comment_List.class);//
                        //intent_s.putExtra("coursecode1", message);//course code message
                        RequestManager req = new RequestManager(intent_s,Complaint_Details.this,"comment/getComments/"+complaint_id,0);//course assignment api
                        req.request();
                        //startActivity(intent_s);
                    }
                }
        );




        //api for upvotes
        b1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){


                        //if can't mark the upvote
                        if(able==1)
                        {
                            b1.setClickable(false);
                            Toast.makeText(Complaint_Details.this, "You have already performed action",
                                    Toast.LENGTH_SHORT).show();

                        }


                        //if can mark the upvote
                        else {

                            String s = Integer.toString(upvotes + 1);

                            RequestManager req = new RequestManager(new Intent(), Complaint_Details.this, "complaintcontroller/upvoteComplaint/" + complaint_id + "/" + s + "/" + user_id_link, 2);//course assignment api
                            req.request();  //api for upvotes

                            b1.setClickable(false);
                            b2.setClickable(false);
                            b1.setText("UPVOTED");
                            String k = "+" + s + " " + "-" + Integer.toString(downvotes);
                            t6.setText(k);

                        }

                        //api to account for upvote
                    }
                }
        );



        //for downvotes button
        b2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){


                        //if can't mark
                        if(able==1)
                        {
                            b1.setClickable(false);
                            Toast.makeText(Complaint_Details.this, "You have already performed action",
                                    Toast.LENGTH_SHORT).show();

                        }


                        //if can mark
                        else {
                            String s = Integer.toString(downvotes + 1);

                            RequestManager req = new RequestManager(new Intent(), Complaint_Details.this, "/ComplaintController/downvoteComplaint/" + complaint_id + "/" + s + "/" + user_id_link, 2);//course assignment api
                            req.request();  //api for downvote

                            b1.setClickable(false);
                            b2.setClickable(false);
                            b2.setText("DOWNVOTED");
                            String k = "+" + Integer.toString(upvotes) + " " + "-" + s;
                            t6.setText(k);
                            //api
                        }
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint__details, menu);
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