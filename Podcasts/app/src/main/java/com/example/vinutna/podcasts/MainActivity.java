package com.example.vinutna.podcasts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PodcastAsync.IData,PodcastAdapter.IData,PodcastGridAdapter.IData {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    boolean displayMode=false;
    boolean pauseplay=true;
    ArrayList<Podcasts> podcastList;
    MediaPlayer media;
    SeekBar sb;
    ImageButton pp;
    Handler myHandler=new Handler();
    ProgressDialog pdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.app_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        sb=(SeekBar)findViewById(R.id.seekBar);
        pp= (ImageButton) findViewById(R.id.playPauseButton);
        new PodcastAsync(this).execute("https://www.npr.org/rss/podcast.php?id=510298");

        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!media.isPlaying()){
                    pp.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    media.start();
                    sb.setProgress(media.getCurrentPosition());
                }
                else {
                    pp.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    media.pause();
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {

                if (media != null && b) {
                    media.seekTo(progressValue * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /*MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (media != null) {
                    int currentposition = media.getCurrentPosition() / 1000;
                    sb.setProgress(currentposition);
                }
                myHandler.postDelayed(this, 1000);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.navigationMode){
            if(displayMode){
                if(media!=null)
                    media.stop();
                pp.setVisibility(View.GONE);
                sb.setVisibility(View.GONE);
                mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new PodcastAdapter(MainActivity.this,MainActivity.this,podcastList);
                mRecyclerView.setAdapter(mAdapter);
                displayMode=false;
            }
            else{
                if(media!=null)
                    media.stop();
                pp.setVisibility(View.GONE);
                sb.setVisibility(View.GONE);
                mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new GridLayoutManager(MainActivity.this,2);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new PodcastGridAdapter(MainActivity.this,MainActivity.this,podcastList);
                mRecyclerView.setAdapter(mAdapter);
                displayMode=true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void putData(ArrayList<Podcasts> result) {
        podcastList=new ArrayList<Podcasts>();
        podcastList=result;
        mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PodcastAdapter(MainActivity.this,MainActivity.this,result);
        mRecyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void playData(String url,View v){
        if(media!=null){
            media.stop();
        }
        pp.setVisibility(View.VISIBLE);
        sb.setVisibility(View.VISIBLE);
        media=new MediaPlayer();
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                media.stop();
                pp.setVisibility(View.GONE);
                sb.setVisibility(View.GONE);
            }
        });
        try {
            media.setDataSource(url);
            media.prepare();
            sb.setMax(media.getDuration());
            media.start();
            sb.setProgress(media.getCurrentPosition());
            myHandler.postDelayed(run, 1000);
            pp.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playAct(Podcasts pod) {
        if(media!=null)
            media.stop();
        pp.setVisibility(View.GONE);
        sb.setVisibility(View.GONE);
        Log.d("demo","hide2");
        Intent i=new Intent(this,PlayActivity.class);
        i.putExtra("PODCAST", pod);
        startActivity(i);
    }

    Runnable run = new Runnable() {
        @Override public void run()
        {
            sb.setProgress(media.getCurrentPosition());
            myHandler.postDelayed(run, 1000);
        }
    };
}
