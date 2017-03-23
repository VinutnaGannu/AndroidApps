package com.example.vinutna.podcasts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinutna on 02-03-2017.
 */

public class Podcasts implements Parcelable {
    private String title,description,pubDate,duration,imageURL,mp3Url;

    public Podcasts(){

    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Podcasts{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", duration='" + duration + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeString(pubDate);
        dest.writeString(duration);
        dest.writeString(mp3Url);
    }

    public static final Parcelable.Creator<Podcasts> CREATOR
            = new Parcelable.Creator<Podcasts>() {
        public Podcasts createFromParcel(Parcel in) {
            return new Podcasts(in);
        }

        public Podcasts[] newArray(int size) {
            return new Podcasts[size];
        }
    };

    private Podcasts(Parcel in){
        this.title=in.readString();
        this.imageURL=in.readString();
        this.description=in.readString();
        this.pubDate=in.readString();
        this.duration=in.readString();
        this.mp3Url=in.readString();
    }

}
