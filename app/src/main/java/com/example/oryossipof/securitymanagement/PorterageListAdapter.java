package com.example.oryossipof.securitymanagement;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by or yossipof on 01/10/2017.
 */

class PorterageListAdapter extends ArrayAdapter<PorterageIntity> {
    private Context mContext;
    int mResource;
    public PorterageListAdapter(Context context,  int resource, ArrayList<PorterageIntity> objects) {
        super(context,resource,objects);

        mContext = context;
        mResource = resource;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String handon = getItem(position).getNumhands();
        String stuffnum = getItem(position).getNumstuff();
        String peoplenum = getItem(position).getNumpeople();
        String status = getItem(position).getStatus();
        String date = getItem(position).getDate();
        String totalMoney = getItem(position).getTotalMoney();
        String totalPeople = getItem(position).getTotalPeople();
        PorterageIntity port = new PorterageIntity(handon,peoplenum,stuffnum,status,date,totalMoney, totalPeople);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView dateTextView = (TextView)convertView.findViewById(R.id.dateTextView);
        TextView peopleNumberText = (TextView)convertView.findViewById(R.id.peopleNumberText);
        TextView handsOnNumText = (TextView)convertView.findViewById(R.id.handsOnNumText);

        TextView statusText = (TextView)convertView.findViewById(R.id.statusText);
        TextView totalPayText = (TextView)convertView.findViewById(R.id.totalPayText);

        dateTextView.setText(date);
        peopleNumberText.setText(peopleNumberText.getText() + " " + totalPeople);
        handsOnNumText.setText(handsOnNumText.getText()+" " + handon);
        statusText.setText(statusText.getText() + " " + status);
        totalPayText.setText(totalPayText.getText() + " " + totalMoney + "$");



        return convertView;


    }
}
