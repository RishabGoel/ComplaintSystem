package com.example.rishab.complaintsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Paras Gupta on 3/29/2016.
 */
public class PendingRequestAdapter {
    String[] Name, Phone, Address, Designation, Username;
    Context context;
    Context intent;
    public PendingRequestAdapter(Context context,String[] Name,String[] Phone,String[] Address,String[] Designation,String[] Username){
        this.Name=Name;
        this.Phone=Phone;
        this.Address=Address;
        this.Designation=Designation;
        this.Username=Username;
        this.context=context;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return Name.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.pending_request_listview, null, true);
//        TextView data = (TextView) row.findViewById(R.id.commodity);
//        data.setText(items[position]);
//        data=(TextView)row.findViewById(R.id.discount);
//        data.setText(discounts[position]);
        return row;
    }
}
