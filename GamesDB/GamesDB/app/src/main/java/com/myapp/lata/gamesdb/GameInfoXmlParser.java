package com.myapp.lata.gamesdb;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lata on 18-02-2017.
 */

public class GameInfoXmlParser {
    static public class GameInfoSaxParser extends DefaultHandler{
        ArrayList<GamesInfo> gamesInfoList;
        GamesInfo gamesInfo;
        StringBuilder xmlInnerText;
        Integer flag,imgFlag;
        ArrayList<String> similarList;

        static public ArrayList<GamesInfo> parseGamesInfo(InputStream in) throws IOException, SAXException {
            GameInfoSaxParser parser=new GameInfoSaxParser();
            Xml.parse(in,Xml.Encoding.UTF_8,parser);
            return parser.getGamesList();
        }
        public ArrayList<GamesInfo> getGamesList(){
            return gamesInfoList;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            gamesInfoList=new ArrayList<>();
            xmlInnerText=new StringBuilder();
            flag=0;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("Game") &&flag==0) {
                gamesInfo = new GamesInfo();
                imgFlag=1;
            }
            else if(localName.equals("Similar"))
            {
                similarList=new ArrayList<>();
                flag=1;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if(localName.equals("id") && flag==0)
                    gamesInfo.setId(xmlInnerText.toString().trim());
            else if(localName.equals("ReleaseDate")){
                if(xmlInnerText.toString().trim().length()>=4)
                    gamesInfo.setReleaseDate(xmlInnerText.toString().trim().substring(xmlInnerText.toString().trim().length()-4));
                else
                    gamesInfo.setReleaseDate("");
            }
            else if(localName.equals("Platform"))
            {
                if(xmlInnerText.toString().trim().length()>0)
                    gamesInfo.setPlatform(xmlInnerText.toString().trim());
                else
                    gamesInfo.setPlatform("");
            }
            else if(localName.equals("id") && flag==1) {
                similarList.add(xmlInnerText.toString().trim());
            }
            else if(localName.equals("Similar")) {
                flag = 0;
            }
            else if(localName.equals("GameTitle"))
                gamesInfo.setGameTitle(xmlInnerText.toString().trim());
            else if(localName.equals("original") && imgFlag==1){
                gamesInfo.setImageSource(xmlInnerText.toString().trim());
                imgFlag=0;
            }
            else if(localName.equals("genre"))
                gamesInfo.setGenre(xmlInnerText.toString().trim());
            else if(localName.equals("Publisher")) {
                    gamesInfo.setPublisher(xmlInnerText.toString().trim());
            }
            else if(localName.equals("Overview")) {
                    gamesInfo.setOverview(xmlInnerText.toString().trim());
            }
            else if(localName.equals("Youtube")) {
                    gamesInfo.setYoutubeLink(xmlInnerText.toString().trim());
            }
            else if(localName.equals("Game") && flag==0){
                gamesInfoList.add(gamesInfo);
                gamesInfo.setSimilarGameId(similarList);
            }
          xmlInnerText.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch,start,length);
        }
    }
}
