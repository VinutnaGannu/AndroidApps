package com.example.vinutna.itunestoppaidapps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vinutna on 22-02-2017.
 */

public class AppsUtil {
    static public class AppJSONParser{
        static ArrayList<Apps> parseApps(String input) throws JSONException {
            ArrayList<Apps> appsList=new ArrayList<Apps>();
            JSONObject root=new JSONObject(input);
            JSONArray entryArray=root.getJSONObject("feed").getJSONArray("entry");
            for (int i=0;i<entryArray.length();i++){
                JSONObject appJSONObject=entryArray.getJSONObject(i);
                Apps apps=Apps.createApps(appJSONObject);
                appsList.add(apps);
            }
            return appsList;
        }
    }
}
