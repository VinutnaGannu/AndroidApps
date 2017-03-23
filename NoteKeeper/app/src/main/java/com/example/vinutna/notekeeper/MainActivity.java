package com.example.vinutna.notekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseManager dm;
    Notes notes;
    List<Notes> notesList;
    NotesAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView noteText= (TextView) findViewById(R.id.noteText);
        final Spinner prioritySpinner= (Spinner) findViewById(R.id.spinner);
        listView=(ListView)findViewById(R.id.listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dm.deleteNote(notesList.get(position));
                notesList=dm.getAllNotes(NotesTable.COLUMN_PRIORITY);
                adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
                listView.setAdapter(adapter);
                return false;
            }
        });

        dm=new DataBaseManager(this);

        notesList=new ArrayList<Notes>();
        notesList=dm.getAllNotes(NotesTable.COLUMN_PRIORITY);
        adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes=new Notes();
                notes.setNote(noteText.getText().toString());
                notes.setPriority(prioritySpinner.getSelectedItem().toString());
                notes.setStatus("pending");

                    notes.setUpdateTime(Calendar.getInstance().getTime());


                dm.saveNote(notes);
                notesList=dm.getAllNotes(NotesTable.COLUMN_PRIORITY);
                Log.d("Demo","Size"+notesList.size());
                adapter.clear();
                adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
                //listView.removeAllViews();
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.showAll){
            notesList=dm.getAllNotes(NotesTable.COLUMN_PRIORITY);
            adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
            listView.setAdapter(adapter);
        }
        else if(item.getItemId()==R.id.showCompleted){
            notesList=dm.getNotes("completed");
            adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
            listView.setAdapter(adapter);
        }
        else if(item.getItemId()==R.id.showPending){
            notesList=dm.getNotes("pending");
            adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
            listView.setAdapter(adapter);
        }
        else if(item.getItemId()==R.id.sortbyTime){
            notesList=dm.getAllNotes(NotesTable.COLUMN_UPDATETIME);
            adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
            listView.setAdapter(adapter);
        }
        else if(item.getItemId()==R.id.sortbyPriority){
            notesList=dm.getAllNotes(NotesTable.COLUMN_PRIORITY);
            adapter=new NotesAdapter(MainActivity.this,R.layout.note_row,notesList);
            listView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
}
