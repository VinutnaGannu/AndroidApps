package com.example.vinutna.itunestoppaidapps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vinutna on 23-02-2017.
 */

public class AppsAdapter extends ArrayAdapter<Apps>{
    Context mContext;
    int mResource;
    List<Apps> mData;
    final boolean flag=false;
    View fav;

    public AppsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mData=objects;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }

        final Apps apps=mData.get(position);

        ImageView imgIcon= (ImageView) convertView.findViewById(R.id.appIcon);
        Picasso.with(convertView.getContext()).load(apps.getIcon()).into(imgIcon);

        TextView title= (TextView) convertView.findViewById(R.id.appTitle);
        title.setText(apps.getTitle()+"\nPrice: "+apps.getCurrency()+" "+apps.getAmount());

        final ImageButton star=(ImageButton)convertView.findViewById(R.id.star);
        star.setBackgroundResource(R.drawable.whitestar);
        apps.setFavApp(0);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav=v;
                AlertDialog.Builder edit = new AlertDialog.Builder(v.getContext());
                edit.setMessage("Are you sure that you want to add this app to favorites?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fav.setBackgroundResource(R.drawable.blackstar);

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                });
                edit.show();
            }
        });
        return convertView;
    }
}
