package com.example.vinutna.notekeeper;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lata on 27-02-2017.
 */

public class NotesTable {

    static final String TABLE_NAME="notes";
    static final String COLUMN_ID="id";
    static final String COLUMN_NOTE="note";
    static final String COLUMN_PRIORITY="priority";
    static final String COLUMN_STATUS="status";
    static final String COLUMN_UPDATETIME="updateTime";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLE_NAME+" (");
        sb.append(COLUMN_ID+ " integer primary key autoincrement, ");
        sb.append(COLUMN_NOTE+ " text not null, ");
        sb.append(COLUMN_PRIORITY+ " text not null, ");
        sb.append(COLUMN_STATUS+" text not null, ");
        sb.append(COLUMN_UPDATETIME+ " text not null);");
        try {
            db.execSQL(sb.toString());
            Log.d("demo","table created");
        }
        catch (SQLException ex){
            Log.d("demo",ex.toString());
            ex.printStackTrace();
        }
    }
    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        NotesTable.onCreate(db);
    }
}
