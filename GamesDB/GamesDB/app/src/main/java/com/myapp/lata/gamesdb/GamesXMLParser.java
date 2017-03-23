package com.myapp.lata.gamesdb;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lata on 17-02-2017.
 */

public class GamesXMLParser {

    static public class GamesSaxParser extends DefaultHandler
    {
        ArrayList<Games> gamesList;
        Games games;
        StringBuilder xmlInnerText;

        static public ArrayList<Games> parseGames(InputStream in) throws IOException, SAXException {
            GamesSaxParser parser=new GamesSaxParser();
            Xml.parse(in,Xml.Encoding.UTF_8,parser);
            return parser.getGamesList();
        }
        public ArrayList<Games> getGamesList(){
            return gamesList;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
           // games=new Games();
            gamesList=new ArrayList<Games>();
            xmlInnerText=new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("Game"))
                games=new Games();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if(localName.equals("Game")) {
                gamesList.add(games);
                //Log.d("demo",gamesList.toString());
            }
            else if(localName.equals("id"))
                games.setId(xmlInnerText.toString().trim());
            else if(localName.equals("GameTitle"))
                games.setTitle(xmlInnerText.toString().trim());
            else if (localName.equals("ReleaseDate")) {
                if (xmlInnerText.toString().trim().length()>=4)
                    games.setReleaseDate(xmlInnerText.toString().trim().substring((xmlInnerText.toString().trim().length())-4));
                else
                    games.setReleaseDate("");
            }
            else if(localName.equals("Platform"))
                games.setPlatform(xmlInnerText.toString().trim());
            xmlInnerText.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch,start,length);
        }
    }

}
