package com.myapp.lata.gamesdb;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lata on 18-02-2017.
 */

public class GamesInfo implements Serializable {
    String id,gameTitle,overview,genre,youtubeLink,publisher,imageSource,releaseDate,platform;
    ArrayList<String> similarGameId;

    @Override
    public String toString() {
        return "GamesInfo{" +
                "id='" + id + '\'' +
                ", gameTitle='" + gameTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", genre='" + genre + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", publisher='" + publisher + '\'' +
                ", imageSource='" + imageSource + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                ", similarGameId=" + similarGameId +
                '}';
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getSimilarGameId() {
        return similarGameId;
    }

    public void setSimilarGameId(ArrayList<String> similarGameId) {
        this.similarGameId = similarGameId;
    }
}
