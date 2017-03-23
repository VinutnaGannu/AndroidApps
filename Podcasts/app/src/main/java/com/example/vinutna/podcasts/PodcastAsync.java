package com.example.vinutna.podcasts;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by vinutna on 02-03-2017.
 */

public class PodcastAsync extends AsyncTask<String,Void,ArrayList<Podcasts>>{
    IData activity;

    public PodcastAsync(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcasts> podcastses) {
        super.onPostExecute(podcastses);
        activity.putData(podcastses);
    }

    @Override
    protected ArrayList<Podcasts> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                InputStream in=con.getInputStream();
                return PodcastsUtils.PodcastPullParser.parsePodcasts(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public interface IData{
        public void putData(ArrayList<Podcasts> result);
    }
}
