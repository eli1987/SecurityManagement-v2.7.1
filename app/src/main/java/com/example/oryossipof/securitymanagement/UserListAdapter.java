package com.example.oryossipof.securitymanagement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
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
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by or yossipof on 05/09/2017.
 */

public class UserListAdapter extends ArrayAdapter<User> {
    private Context mContext;
    int mResource;
    public UserListAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        String time = getItem(position).getTime();
        String description = getItem(position).getDescription();
        String image = getItem(position).getUrlImage();
        User user = new User(username,time,description,image);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView timeTxt = (TextView)convertView.findViewById(R.id.timeTextView);
        TextView usernameTxt = (TextView)convertView.findViewById(R.id.nameTextView);
        TextView DesTxt = (TextView)convertView.findViewById(R.id.DesTextView);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageUpload);


        timeTxt.setText(time);
        usernameTxt.setText(username);
        DesTxt.setText(description);


        if(!image.equals("")) {
                Uri uri;
            String stringUri;
            uri = Uri.parse(image);

            Log.e("URI:" , uri.toString());
            imageView.getLayoutParams().height = 800;
            Picasso.with(getContext()).load(uri).rotate(90).fit().centerInside().into(imageView);
        }

        //imageView.setImageResource(  getContext().getResources().getIdentifier("drawable/" + image, null,  getContext().getPackageName()));

        return convertView;


    }


}
