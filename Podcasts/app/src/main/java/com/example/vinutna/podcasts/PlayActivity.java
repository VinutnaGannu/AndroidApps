package com.example.vinutna.podcasts;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity {
    MediaPlayer media;
    Handler myHandler=new Handler();
    int sbPosition=0;
    SeekBar sb;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.app_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_play);
        final Podcasts podcast;
        podcast=(Podcasts) getIntent().getExtras().getParcelable("PODCAST");
        TextView title= (TextView) findViewById(R.id.playActivityTitle);
        title.setText(podcast.getTitle());

        ImageView img=(ImageView)findViewById(R.id.playActivityIcon);
        Picasso.with(this).load(podcast.getImageURL()).into(img);

        TextView des=(TextView)findViewById(R.id.desc);
        des.setText("Description: "+podcast.getDescription());
        des.setMovementMethod(new ScrollingMovementMethod());

        TextView pub=(TextView)findViewById(R.id.published);
        DateFormat formatter=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

        Log.d("demo",podcast.getPubDate());
        try {
            Date date= formatter.parse(podcast.getPubDate());
            formatter=new SimpleDateFormat("dd/MM/yyyy");
            pub.setText((formatter.format(date)).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView dur=(TextView)findViewById(R.id.duration);
        dur.setText(podcast.getDuration());

        sb = (SeekBar) findViewById(R.id.playActivitySeek);
        media = new MediaPlayer();
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                media.stop();
                sbPosition = 0;
            }
        });
        try {
            media.setDataSource(podcast.getMp3Url());
            media.prepare();
            if (sbPosition == 0) {
                sb.setMax(media.getDuration());
                Log.d("demo", "duration" + media.getDuration());
                sbPosition = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.playActivityButton).setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                if (!media.isPlaying()) {
                    v.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    if(media==null) {
                        media.stop();
                        sbPosition = 0;
                    }
                    media.start();
                    sb.setProgress(media.getCurrentPosition());
                    myHandler.postDelayed(run, 1000);
                }
                else {
                    v.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    media.pause();
                }
            }
        });
    }

    Runnable run = new Runnable() {
        @Override public void run()
        {
            sb.setProgress(media.getCurrentPosition());
            myHandler.postDelayed(run, 1000);
        }
    };
}
