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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oryossipof.securitymanagement.R;
import com.example.oryossipof.securitymanagement.Lost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LostAdapter extends ArrayAdapter<Lost> {
    private Context mContext;
    int mResource;
    public LostAdapter(Context context, int resource, ArrayList<Lost> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String username = getItem(position).getUsername();
    final     String time = getItem(position).getMonth();
        String description = getItem(position).getLostDescrption();
        String image = getItem(position).getImageUri();
        String where = getItem(position).getWhereLostFound();
        String who = getItem(position).getWhoFound();
     final    String lost = getItem(position).getLostnumber();
     final    String return1 = getItem(position).getIsReturn();



        Lost user = new Lost(description, time, username, where, who, image,lost,return1);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView timeTxt = (TextView)convertView.findViewById(R.id.whoFoundText2);
        TextView usernameTxt = (TextView)convertView.findViewById(R.id.timeText);
        TextView DesTxt = (TextView)convertView.findViewById(R.id.lostDescrptionText);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageUpload);
        TextView WhereTxt = (TextView)convertView.findViewById(R.id.whereLostFoundText);
        TextView whoTxt = (TextView)convertView.findViewById(R.id.secHandele);
        TextView numOfLostTxt = (TextView)convertView.findViewById(R.id.numberofLost);
       // TextView isReturnTxt = (TextView)convertView.findViewById(R.id.isReturend);
        final  Button  isreturn = (Button)convertView.findViewById(R.id.button2);


        isreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.returnItem(time,lost,return1);
                if(isreturn.getText().equals("No")) {
                    isreturn.setText("Yes");
                    DataBase.returnItem(time,lost,"Yes");
                }
                else {
                    isreturn.setText("No");
                    DataBase.returnItem(time,lost,"No");
                }
            }
        });

        timeTxt.setText(time);
        usernameTxt.setText(username);
        DesTxt.setText(description);
        WhereTxt.setText(where);
        whoTxt.setText(who);
        numOfLostTxt.setText(lost);
        isreturn.setText(return1);

        if(!image.equals("")) {
            Uri uri;
            String stringUri;
            uri = Uri.parse(image);

            Log.e("URI:" , uri.toString());
            imageView.getLayoutParams().height = 800;
            Picasso.with(getContext()).load(uri).into(imageView);
        }

        //imageView.setImageResource(  getContext().getResources().getIdentifier("drawable/" + image, null,  getContext().getPackageName()));

        return convertView;


    }


}
