package com.example.vinutna.trivia;

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
 * Created by vinutna on 09-02-2017.
 */

public class TriviaAsyncTask extends AsyncTask<String, Void, ArrayList<Questions>>{

    @Override
    protected void onPostExecute(ArrayList<Questions> questionses) {
        for(int i=0;i<questionses.size();i++){
            Log.d("demo",questionses.get(i).toString());
        }
        super.onPostExecute(questionses);
    }

    @Override
    protected ArrayList<Questions> doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                return QuestionsUtil.QuestionsJSONParser.parseQuestions(sb.toString());
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
}
