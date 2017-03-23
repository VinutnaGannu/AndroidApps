package com.myapp.lata.gamesdb;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SimilarActivity extends AppCompatActivity implements GetSimilarGameInfoAsyncTask.Idata{
    ArrayList<GamesInfo> selectedGame;
    ArrayList<String> similarGames;
    ArrayList<GamesInfo> gamesInfo;
    Integer counter;
    ProgressDialog pd;
    static final String key="id";
    static final String baseURL="http://thegamesdb.net/api/GetGame.php";
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);
        selectedGame= (ArrayList<GamesInfo>) this.getIntent().getSerializableExtra(GameDetails.INTENT_SIMILAR);

        TextView title= (TextView) findViewById(R.id.titledisplay);
        title.setTextColor(Color.BLACK);
        title.setText("Similar games to "+selectedGame.get(0).getGameTitle());
        ll= (LinearLayout) findViewById(R.id.ll);
        similarGames=selectedGame.get(0).getSimilarGameId();
        pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();
        findViewById(R.id.finishbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(similarGames.size()>0) {
            counter=0;
            for (int i = 0; i < similarGames.size(); i++) {
                StringBuilder sb = new StringBuilder();
                String value = null;
                gamesInfo=new ArrayList<>();
                try {
                    value = URLEncoder.encode(similarGames.get(i), "UTF-8");
                    sb.append(key + "=" + value);
                    Log.d("demo",similarGames.get(i));
                    new GetSimilarGameInfoAsyncTask(this).execute(baseURL +"?"+ sb.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }
        else Toast.makeText(this, "No similar games", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupData(ArrayList<GamesInfo> gamesInfos) {
        gamesInfo=gamesInfos;
        counter=counter+1;
        if(counter==similarGames.size()){
            pd.dismiss();
        }
        Log.d("demo",gamesInfo.get(0).getGameTitle());
        TextView textView = new TextView(this);
        textView.setTextColor(Color.BLACK);
        if (gamesInfo.get(0).getReleaseDate()!=null)
            textView.setText(gamesInfo.get(0).getGameTitle()+". Released in: "+gamesInfo.get(0).getReleaseDate()+". Platform: "+gamesInfo.get(0).getPlatform());
        else
            textView.setText(gamesInfo.get(0).getGameTitle()+". Platform: "+gamesInfo.get(0).getPlatform());

        ll.addView(textView);
        TextView textView1=new TextView(this);
        textView1.setText("");
        ll.addView(textView1);
        }
    }

