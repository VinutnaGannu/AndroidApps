package com.myapp.lata.gamesdb;

import java.io.Serializable;

/**
 * Created by Lata on 17-02-2017.
 */

public class Games implements Serializable {
    String id,title,releaseDate,platform;

    @Override
    public String toString() {
        return "Games{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
