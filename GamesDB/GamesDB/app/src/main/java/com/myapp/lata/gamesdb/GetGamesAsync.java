package com.myapp.lata.gamesdb;

import android.os.AsyncTask;
import android.util.Xml;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lata on 17-02-2017.
 */

public class GetGamesAsync extends AsyncTask<String,Void,ArrayList<Games>> {
    MainActivity activity;
    public GetGamesAsync(MainActivity activity)
    {
        this.activity=activity;
    }

    @Override
    protected ArrayList<Games> doInBackground(String... params) {

        try {
            URL url=new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK)
            {
                InputStream in=con.getInputStream();
                return GamesXMLParser.GamesSaxParser.parseGames(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Games> games) {
        activity.setupData(games);
        super.onPostExecute(games);


    }
    public static interface Idata
    {
        public void setupData(ArrayList<Games> games);
    }
}
