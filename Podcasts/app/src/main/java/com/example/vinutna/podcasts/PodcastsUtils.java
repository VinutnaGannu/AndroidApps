package com.example.vinutna.podcasts;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by vinutna on 02-03-2017.
 */

public class PodcastsUtils {
    static public class PodcastPullParser{
        static ArrayList<Podcasts> parsePodcasts(InputStream in) throws XmlPullParserException, IOException, ParseException {
            XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in,"UTF-8");
            Podcasts podcast=null;
            boolean itemFlag=false;
            ArrayList<Podcasts> podcastList=new ArrayList<Podcasts>();
            int event=parser.getEventType();
            while (event!=XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("item")){
                            podcast=new Podcasts();
                            itemFlag=true;
                        }
                        else if(parser.getName().equals("title")&&itemFlag){
                            podcast.setTitle(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("description")&&itemFlag){
                            podcast.setDescription(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("pubDate")&&itemFlag){
                            podcast.setPubDate(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("itunes:duration")&&itemFlag){
                            podcast.setDuration(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("itunes:image")&&itemFlag){
                            podcast.setImageURL(parser.getAttributeValue(null,"href"));
                        }
                        else if(parser.getName().equals("enclosure")&&itemFlag){
                            podcast.setMp3Url(parser.getAttributeValue(null,"url"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            podcastList.add(podcast);
                            podcast=null;
                            itemFlag=false;
                        }
                        break;
                    default:
                        break;
                }
                event=parser.next();
            }
            return podcastList;
        }
    }
}
