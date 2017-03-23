package com.myapp.lata.gamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GameDetails extends AppCompatActivity implements GetGameInfoAsyncTask.Idata{

    ArrayList<GamesInfo> gamesInfo;
    String id;
    static final String baseUrl="http://thegamesdb.net/api/GetGame.php";
    String key="id";
    ProgressDialog pd;
    TextView title,overview,genre,publisher;
    Button trailerbtn,finishbtn,similarbtn;
    ProgressBar pb;
    public static final String BASEIMGURL="http://thegamesdb.net/banners/";
    ImageView imageView;
    static final String INTENT_YOUTUBE="youtube";
    static final String INTENT_SIMILAR="similar";
    static final String INTENT_TITLE="title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        this.setTitle("Game details");
        id=this.getIntent().getStringExtra(MainActivity.ID_KEY);
        gamesInfo=new ArrayList<>();

        title= (TextView) findViewById(R.id.titledisplay);
        overview= (TextView) findViewById(R.id.overviewdisplay);
        genre= (TextView) findViewById(R.id.genredisplay);
        publisher= (TextView) findViewById(R.id.publisherdisplay);
        trailerbtn= (Button) findViewById(R.id.trailerbtn);
        similarbtn= (Button) findViewById(R.id.similarbtn);
        finishbtn= (Button) findViewById(R.id.finishbtn);
        imageView= (ImageView) findViewById(R.id.imageView);

        pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();
        try {
            new GetGameInfoAsyncTask(GameDetails.this).execute(getEncodedUrl());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        trailerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamesInfo.get(0).getYoutubeLink()!=null) {
                    Intent intent = new Intent(GameDetails.this, WebViewActivity.class);
                    intent.putExtra(INTENT_YOUTUBE, gamesInfo.get(0).getYoutubeLink());
                    intent.putExtra(INTENT_TITLE,gamesInfo.get(0).getGameTitle());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(GameDetails.this, "Link not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        similarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamesInfo.get(0).getSimilarGameId()!=null){
                Intent intent=new Intent(GameDetails.this,SimilarActivity.class);
                intent.putExtra(INTENT_SIMILAR,gamesInfo);
                startActivity(intent);
                }
                else
                {
                    Toast.makeText(GameDetails.this, "No similar games", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void setupData(ArrayList<GamesInfo> gamesInfos) {
        gamesInfo=gamesInfos;
       // Log.d("demo",gamesInfo.toString());
        pd.dismiss();
        title.setText(gamesInfo.get(0).gameTitle);
        title.setTextColor(Color.BLACK);
        overview.setText(gamesInfo.get(0).getOverview());
        overview.setTextColor(Color.BLACK);
        overview.setMovementMethod(new ScrollingMovementMethod());
        genre.setText(gamesInfo.get(0).getGenre());
        genre.setTextColor(Color.BLACK);
        publisher.setText(gamesInfo.get(0).getPublisher());
        publisher.setTextColor(Color.BLACK);
        pb= (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(BASEIMGURL+gamesInfo.get(0).getImageSource())
                .into(imageView,new ImageLoadedCallback(pb){
        public void onSuccess(){
            if (pb != null)
                pb.setVisibility(View.GONE);
            }
        public void onError()
        {
            pb.setVisibility(View.GONE);
        }
        });
    }
    public String getEncodedParams() throws UnsupportedEncodingException {
        StringBuilder sb=new StringBuilder();
        String value= URLEncoder.encode(id,"UTF-8");
        sb.append(key+"="+value);
        return sb.toString();
    }
    public String getEncodedUrl() throws UnsupportedEncodingException {
        return baseUrl+"?"+getEncodedParams();
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar pb) {
            progressBar = pb;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
