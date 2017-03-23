package com.example.vinutna.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by vinutna on 27-02-2017.
 */

public class DataBaseManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DataBaseManager(Context mContext){
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
        noteDAO=new NoteDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public NoteDAO getNoteDAO(){
        return this.noteDAO;
    }

    public long saveNote(Notes notes){
        return this.noteDAO.save(notes);
    }

    public boolean updateNote(Notes notes){
        return this.noteDAO.update(notes);
    }

    public List<Notes> getNotes(String status){
        return this.noteDAO.get(status);
    }

    public List<Notes> getAllNotes(String order){
        return this.noteDAO.getAll(order);
    }

    public boolean deleteNote(Notes notes){
        return this.noteDAO.delete(notes);
    }
}
