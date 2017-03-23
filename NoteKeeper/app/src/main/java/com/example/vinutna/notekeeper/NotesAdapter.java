package com.example.vinutna.notekeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vinutna on 27-02-2017.
 */

public class NotesAdapter extends ArrayAdapter<Notes>{
    Context mContext;
    int mResource;
    List<Notes> mData;
    DataBaseManager dm;
    AlertDialog.Builder edit;
    Notes notes=new Notes();

    public NotesAdapter(Context context, int resource, List<Notes> objects) {
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

        notes=mData.get(position);
        dm=new DataBaseManager(mContext);

        TextView textTitle= (TextView) convertView.findViewById(R.id.noteTitle);
        textTitle.setText(notes.getNote());

        final CheckBox statusBox= (CheckBox) convertView.findViewById(R.id.status);
        if(notes.getStatus().equals("pending"))
            statusBox.setChecked(false);
        else if (notes.getStatus().equals("completed"))
            statusBox.setChecked(true);
        statusBox.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(notes.getStatus().equals("pending")){
                         edit=new AlertDialog.Builder(v.getContext());
                         edit.setMessage("Do you really want to mark it as completed?")
                                 .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         notes.setStatus("completed");
                                         if(dm.updateNote(notes))
                                            statusBox.setChecked(true);
                                     }
                                 }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 statusBox.setChecked(false);
                                 dialog.cancel();
                             }
                         });
                         edit.show();
                     }
                     else {
                         edit=new AlertDialog.Builder(v.getContext());
                         edit.setMessage("Do you really want to mark it as pending?")
                                 .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         notes.setStatus("pending");
                                         if(dm.updateNote(notes))
                                            statusBox.setChecked(false);
                                     }
                                 }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 statusBox.setChecked(true);
                                 dialog.cancel();
                             }
                         });
                         edit.show();
                     }
                 }
             });

        TextView priority = (TextView) convertView.findViewById(R.id.notePriority);
        priority.setText(notes.getPriority());

        TextView time= (TextView) convertView.findViewById(R.id.timeStamp);
        PrettyTime p=new PrettyTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setText(p.format(notes.getUpdateTime()));


        return convertView;
    }
}
