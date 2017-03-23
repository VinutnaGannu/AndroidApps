package com.example.vinutna.getnewsxml;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vinutna on 13-02-2017.
 */

public class GetNewsAsync extends AsyncTask<String, Void, ArrayList<News>>{
    IData activity;

    public GetNewsAsync(IData activity){
        this.activity=activity;
    }

    static public interface IData{
        public void putData(ArrayList<News> result);
    }
    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        super.onPostExecute(newses);
        activity.putData(newses);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            Log.d("demo",params[0]);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                InputStream in=con.getInputStream();
                return NewsUtil.NewsSAXParser.parseNews(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }
}
