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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by אלי on 04/10/2017.
 */

public class DepositAdapter extends ArrayAdapter<Deposit> {
    private Context mContext;
    int mResource;
    public DepositAdapter(Context context, int resource, ArrayList<Deposit> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String username = getItem(position).getUsername();
        final     String time = getItem(position).getMonth();
        final     String day = getItem(position).getDayDelivered();
        String description = getItem(position).getDepositDescrption();
        String image = getItem(position).getImageUri();
        String whofor = getItem(position).getWhoDepositFor();
        String whofrom = getItem(position).getWhoDeliverDeposit();
        final    String depositnum = getItem(position).getDepositnumber();
        final    String return1 = getItem(position).getIsReturn();



        Deposit user = new Deposit(description, time, username, whofor, whofrom, image,depositnum,return1,day);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView timeTxt = (TextView)convertView.findViewById(R.id.whoFoundText2);
        TextView usernameTxt = (TextView)convertView.findViewById(R.id.timeText);
        TextView DesTxt = (TextView)convertView.findViewById(R.id.lostDescrptionText);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageUpload);
        TextView whoforTxt = (TextView)convertView.findViewById(R.id.whereLostFoundText);
        TextView whofromTxt = (TextView)convertView.findViewById(R.id.secHandele);
        TextView depositnumTxt = (TextView)convertView.findViewById(R.id.numberofLost);
        //TextView isReturnTxt = (TextView)convertView.findViewById(R.id.isReturend);
        final Button isreturn = (Button)convertView.findViewById(R.id.button2);


        isreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase.returnItemDeposit(time,depositnum,return1);
                if(isreturn.getText().equals("No")) {
                    isreturn.setText("Yes");
                    DataBase.returnItemDeposit(time,depositnum,"Yes");
                }
                else {
                    isreturn.setText("No");
                    DataBase.returnItemDeposit(time,depositnum,"No");
                }
            }
        });

        timeTxt.setText(day);
        usernameTxt.setText(username);
        DesTxt.setText(description);
        whoforTxt.setText(whofor);
        whofromTxt.setText(whofrom);
        depositnumTxt.setText(depositnum);
        isreturn.setText(return1);

        if(!image.equals("")) {
            Uri uri;
            String stringUri;
            uri = Uri.parse(image);

            Log.e("URI:" , uri.toString());
            imageView.getLayoutParams().height = 800;
           // imageView.getLayoutParams().width = 1200;
            Picasso.with(getContext()).load(uri).rotate(90).fit().centerInside().into(imageView);
        }

        //imageView.setImageResource(  getContext().getResources().getIdentifier("drawable/" + image, null,  getContext().getPackageName()));

        return convertView;


    }


}
