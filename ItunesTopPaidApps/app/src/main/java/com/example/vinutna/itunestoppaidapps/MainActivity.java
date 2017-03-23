package com.example.vinutna.itunestoppaidapps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetAppsAsync.IData {

    ProgressDialog pd;
    ArrayList<Apps> appsList;
    AppsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setTitle("Loading...");
        pd.show();
        new GetAppsAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.Refresh){
            new GetAppsAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        }
        else if(item.getItemId()==R.id.favorites){

        }
        else if(item.getItemId()==R.id.sortInc){
            adapter.sort(Apps.sortByPriceInc);
        }
        else if(item.getItemId()==R.id.sortDec){
            adapter.sort(Apps.sortByPriceDec);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void putData(ArrayList<Apps> result) {
        appsList=new ArrayList<Apps>();
        appsList=result;
        ListView list= (ListView) findViewById(R.id.listVIew);
        adapter=new AppsAdapter(this,R.layout.row_item,appsList);
        list.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        pd.dismiss();
    }
}
