package com.example.vinutna.podcasts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vinutna on 02-03-2017.
 */

public class PodcastGridAdapter extends RecyclerView.Adapter<PodcastGridAdapter.ViewHolder>{
    IData mactivity;
    private ArrayList<Podcasts> mDataset;
    private Context mContext;
    int mPosition;
    ProgressDialog pd;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        ImageButton play;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title=(TextView)itemView.findViewById(R.id.viewTitle);
            this.icon=(ImageView)itemView.findViewById(R.id.imageIcon);
            this.play=(ImageButton)itemView.findViewById(R.id.playButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd=new ProgressDialog(mContext);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setCancelable(false);
                    pd.show();
                    mPosition=getAdapterPosition();
                    Podcasts pod=mDataset.get(getAdapterPosition());
                    mactivity.playAct(pod);
                    pd.dismiss();
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PodcastGridAdapter(IData activity,Context context, ArrayList<Podcasts> myDataset) {
        mDataset = myDataset;
        mContext=context;
        mactivity=activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PodcastGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_grid,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PodcastGridAdapter.ViewHolder holder,final int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        Picasso.with(mContext).load(mDataset.get(position).getImageURL()).into(holder.icon);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mactivity.playData(mDataset.get(position).getMp3Url(),v);
            }
        });
    }

    public interface IData{
        public void playData(String url,View v);
        public void playAct(Podcasts pod);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
