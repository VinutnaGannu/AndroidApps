package com.example.vinutna.notekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lata on 27-02-2017.
 */

public class NoteDAO {

    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db){
        this.db=db;
    }

    public long save(Notes notes){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values=new ContentValues();
        values.put(NotesTable.COLUMN_NOTE,notes.getNote());
        values.put(NotesTable.COLUMN_PRIORITY,notes.getPriority());
        values.put(NotesTable.COLUMN_STATUS,notes.getStatus());
        values.put(NotesTable.COLUMN_UPDATETIME,formatter.format(notes.getUpdateTime()));
        long id = db.insert(NotesTable.TABLE_NAME,null,values);
        Log.d("DB","Id - "+id);
        return id;
    }
    public boolean update(Notes notes)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values=new ContentValues();
        values.put(NotesTable.COLUMN_NOTE,notes.getNote());
        values.put(NotesTable.COLUMN_PRIORITY,notes.getPriority());
        values.put(NotesTable.COLUMN_STATUS,notes.getStatus());
        values.put(NotesTable.COLUMN_UPDATETIME,formatter.format(notes.getUpdateTime()));
        return db.update(NotesTable.TABLE_NAME, values, NotesTable.COLUMN_ID + "=?",new String[]{notes.getId()+""})>0;
    }
    public boolean delete(Notes notes)
    {
        return db.delete(NotesTable.TABLE_NAME,NotesTable.COLUMN_ID + "=?",new String[]{notes.getId()+""})>0;
    }
    public List<Notes> get(String status){
        List<Notes> notesList=new ArrayList<>();
        Cursor c = db.query(true, NotesTable.TABLE_NAME, new String[]{
                NotesTable.COLUMN_ID, NotesTable.COLUMN_NOTE,
                NotesTable.COLUMN_PRIORITY, NotesTable.COLUMN_STATUS,
                NotesTable.COLUMN_UPDATETIME}, NotesTable.COLUMN_STATUS + "=?", new String[]{status},null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            do{
                Notes notes=buildNoteFromCursor(c);
                if(notes!=null){
                    notesList.add(notes);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
                c.close();
        }
        return notesList;
    }
    public List<Notes> getAll(String order){
        List<Notes> notesList=new ArrayList<>();
        Cursor c=null;
        if(order.equals(NotesTable.COLUMN_UPDATETIME)) {
            c = db.query(NotesTable.TABLE_NAME, new String[]{
                    NotesTable.COLUMN_ID, NotesTable.COLUMN_NOTE,
                    NotesTable.COLUMN_PRIORITY, NotesTable.COLUMN_STATUS,
                    NotesTable.COLUMN_UPDATETIME},null,null,null, null, NotesTable.COLUMN_UPDATETIME);
        }
        else if(order.equals(NotesTable.COLUMN_PRIORITY))
        {
            c=db.query(NotesTable.TABLE_NAME, new String[]{
                    NotesTable.COLUMN_ID, NotesTable.COLUMN_NOTE,
                    NotesTable.COLUMN_PRIORITY, NotesTable.COLUMN_STATUS,
                    NotesTable.COLUMN_UPDATETIME},null,null,null, null,NotesTable.COLUMN_PRIORITY);
        }
        if(c!=null && c.moveToFirst())
            {
            do{
                Notes notes=buildNoteFromCursor(c);
                if(notes!=null){
                    notesList.add(notes);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
                c.close();
        }
        return notesList;
    }
    public Notes buildNoteFromCursor(Cursor c)
    {
        Notes notes=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(c!=null)
        {
            notes=new Notes();
            notes.setId(c.getLong(0));
            notes.setNote(c.getString(1));
            notes.setPriority(c.getString(2));
            notes.setStatus(c.getString(3));
            try {
                notes.setUpdateTime(formatter.parse(c.getString(4)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return notes;
    }
}