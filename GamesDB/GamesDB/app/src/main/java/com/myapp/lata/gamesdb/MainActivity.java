package com.myapp.lata.gamesdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetGamesAsync.Idata {

    Button gobtn, searchbtn;
    EditText input;
    String inputkeyword;
    ArrayList<Games> gamesList;
    static final String baseUrl = "http://thegamesdb.net/api/GetGamesList.php";
    static final String key = "name";
    LinearLayout ll;
    ProgressBar pb;
    RadioGroup rg;
    String id;
    static final String ID_KEY = "selection id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gobtn = (Button) findViewById(R.id.gobtn);
        gobtn.setEnabled(false);
        searchbtn = (Button) findViewById(R.id.searchbtn);
        ll = (LinearLayout) findViewById(R.id.ll);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = (EditText) findViewById(R.id.inputkeyword);
                inputkeyword = input.getText().toString();
                ll.removeAllViews();
                if (!inputkeyword.equals("")) {
                    try {
                        new GetGamesAsync(MainActivity.this).execute(getEncodedUrl());
                        pb = new ProgressBar(MainActivity.this, null, android.R.attr.progressBarStyleLarge);
                        pb.setIndeterminate(true);
                        pb.setVisibility(View.VISIBLE);
                        ll.addView(pb);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }

            }
        });
        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = String.valueOf(rg.getCheckedRadioButtonId());
                Log.d("demo", id + "");
                Intent intent = new Intent(MainActivity.this, GameDetails.class);
                intent.putExtra(ID_KEY, id);
                startActivity(intent);
            }
        });
    }

    public String getEncodedParams() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String value = URLEncoder.encode(inputkeyword.trim(), "UTF-8");
        sb.append(key + "=" + value);
        return sb.toString();
    }

    public String getEncodedUrl() throws UnsupportedEncodingException {
        return baseUrl + "?" + getEncodedParams();
    }

    @Override
    public void setupData(ArrayList<Games> games) {
        gamesList = games;
       // Log.d("demo", gamesList.toString());
        pb.setVisibility(View.GONE);
        if (gamesList == null)
            Toast.makeText(this, "Match not found", Toast.LENGTH_SHORT).show();
        else {
            rg = new RadioGroup(this);
            rg.setOrientation(RadioGroup.VERTICAL);
            for (int i = 0; i < gamesList.size(); i++) {
                RadioButton rb = new RadioButton(this);
                rb.setId(Integer.parseInt(gamesList.get(i).getId()));
                if (!gamesList.get(i).getReleaseDate().equals("")) {
                    rb.setText(gamesList.get(i).getTitle() + ". Released in " + gamesList.get(i).getReleaseDate() + ". Platform: " + gamesList.get(i).getPlatform());
                } else {
                    rb.setText(gamesList.get(i).getTitle() + ". Platform: " + gamesList.get(i).getPlatform());
                }
                rg.addView(rb);
            }
            ll.addView(rg);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    gobtn.setEnabled(true);
                }
            });
        }
    }
}
