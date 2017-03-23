package com.myapp.lata.gamesdb;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lata on 18-02-2017.
 */

public class GetGameInfoAsyncTask extends AsyncTask<String,Void,ArrayList<GamesInfo>> {

    GameDetails activity;

    public GetGameInfoAsyncTask(GameDetails activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<GamesInfo> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return GameInfoXmlParser.GameInfoSaxParser.parseGamesInfo(in);
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<GamesInfo> gamesInfos) {
        activity.setupData(gamesInfos);
        super.onPostExecute(gamesInfos);
    }

    public static interface Idata
    {
        public void setupData(ArrayList<GamesInfo> gamesInfos);
    }
}

