package com.example.vinutna.itunestoppaidapps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by vinutna on 22-02-2017.
 */

public class Apps {
    String title,icon,amount,currency;
    int favApp;

    static public Apps createApps(JSONObject js) throws JSONException {
        Apps apps=new Apps();
        apps.setTitle(js.getJSONObject("title").getString("label"));
        apps.setAmount(js.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
        apps.setCurrency(js.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
        apps.setIcon(js.getJSONArray("im:image").getJSONObject(0).getString("label"));
        return apps;
    }

    public int getFavApp() {
        return favApp;
    }

    public void setFavApp(int favApp) {
        this.favApp = favApp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Apps{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public static Comparator<Apps> sortByPriceInc=new Comparator<Apps>() {
        @Override
        public int compare(Apps a1, Apps a2) {
            Double diff=Double.parseDouble(a1.amount)-Double.parseDouble(a2.amount);
            if(diff>0)
                return 1;
            else if(diff<0)
                return  -1;
            else
                return 0;
        }
    };

    public static Comparator<Apps> sortByPriceDec=new Comparator<Apps>() {
        @Override
        public int compare(Apps a1, Apps a2) {
            Double diff=Double.parseDouble(a2.amount)-Double.parseDouble(a1.amount);
            if(diff>0)
                return 1;
            else if(diff<0)
                return  -1;
            else
                return 0;
        }
    };
}
