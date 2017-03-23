package com.example.vinutna.getnewsxml;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.zip.Deflater;

/**
 * Created by vinutna on 13-02-2017.
 */

public class NewsUtil {

    static public class NewsSAXParser extends DefaultHandler{
        ArrayList<News> newsList;
        News news=null;
        StringBuilder xmlInnerText;
        boolean correct;
        String imgHeight,imgWidth;

        public ArrayList<News> getNewsList() {
            return newsList;
        }

        public void setNewsList(ArrayList<News> newsList) {
            this.newsList = newsList;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            newsList=new ArrayList<News>();
            xmlInnerText=new StringBuilder();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("item")){
                news=new News();
                correct=true;
            }
            else if(qName.equals("media:content")&&correct){
                imgHeight=attributes.getValue("height");
                imgWidth=attributes.getValue("width");
                if(imgWidth.equals(imgHeight)){
                    Log.d("demo","height:"+imgHeight+"width:"+imgWidth);
                    news.setImageUrl(attributes.getValue("url"));
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch,start,length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("item")){
                newsList.add(news);
                correct=false;
            }
            else if(localName.equals("title")&&correct)
            {
                news.setTitle(xmlInnerText.toString());
            }
            else if(localName.equals("description")&&correct)
            {
                news.setDescription(xmlInnerText.toString());
            }
            else if(localName.equals("pubDate")&&correct)
            {
                news.setPubDate(xmlInnerText.toString());
            }
            xmlInnerText.setLength(0);
        }

        static public ArrayList<News> parseNews(InputStream in) throws IOException, SAXException {
            NewsSAXParser parser = new NewsSAXParser();
            Xml.parse(in,Xml.Encoding.UTF_8,parser);
            return parser.getNewsList();
        }


    }
}
