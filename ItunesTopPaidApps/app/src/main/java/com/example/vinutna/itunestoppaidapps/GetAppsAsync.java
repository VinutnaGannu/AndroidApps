package com.example.vinutna.itunestoppaidapps;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vinutna on 22-02-2017.
 */

public class GetAppsAsync extends AsyncTask<String,Void,ArrayList<Apps>>{
    IData activity;

    public GetAppsAsync(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Apps> appses) {
        super.onPostExecute(appses);
        activity.putData(appses);
        //Log.d("demo",appses.get(0).toString());
    }

    @Override
    protected ArrayList<Apps> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line=br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
                return AppsUtil.AppJSONParser.parseApps(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public interface IData{
        public void putData(ArrayList<Apps> result);
    }
}
