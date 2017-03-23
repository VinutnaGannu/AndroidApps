package com.example.vinutna.getnewsxml;

import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.vinutna.getnewsxml.R.id.image;

public class MainActivity extends AppCompatActivity implements GetNewsAsync.IData{

    ArrayList<News> newsList=new ArrayList<News>();
    int i;
    LinearLayout ll;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll=(LinearLayout)findViewById(R.id.linearLayoutNews);

        findViewById(R.id.getNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
                pb =new ProgressBar(MainActivity.this);
                ll.addView(pb);
                pb.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.firstButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList !=null) {
                    i = 0;
                    try {
                        setData(i, newsList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.prevButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList !=null) {
                    if (i > 0) {
                        i = i - 1;
                        try {
                            setData(i, newsList);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList !=null) {
                    if (i < newsList.size() - 1) {
                        i = i + 1;
                        try {
                            setData(i, newsList);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        findViewById(R.id.lastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList !=null) {
                    i = newsList.size() - 1;
                    try {
                        setData(i, newsList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void putData(ArrayList<News> result) {
        newsList=result;
        pb.setVisibility(View.GONE);
        for(int i=0;i<result.size();i++)
            Log.d("demo",result.get(i).toString());
        try {
            setData(0,newsList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setData(Integer index,ArrayList<News> result) throws ParseException {
        LinearLayout ll=(LinearLayout) findViewById(R.id.linearLayoutNews);
        ll.removeAllViews();
        //Log.d("demo","result"+result.get(0).getTitle()+result.get(0).getPubDate());
        ImageView img=(ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load(result.get(index).getImageUrl()).into(img);
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        Date date=format.parse(result.get(index).getPubDate());
        format = new SimpleDateFormat("yyyy-MM-dd");
        String resultDate=format.format(date);

        TextView t=new TextView(MainActivity.this);
        t.setText(result.get(index).getTitle());
        t.setTypeface(null, Typeface.BOLD);
        t.setTextColor(Color.BLACK);
        ll.addView(t);

        TextView t1=new TextView(MainActivity.this);
        t1.setText("Published On: "+resultDate);
        t1.setTextColor(Color.BLACK);
        ll.addView(t1);

        TextView t2=new TextView(MainActivity.this);
        t2.setText("Description:"+result.get(index).getDescription().toString());
        t2.setTextColor(Color.BLACK);
        ll.addView(t2);
    }

}
