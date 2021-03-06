package com.kelldavis.movieguide.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

    @SerializedName("key")
    private String key;
    @SerializedName("site")
    private String site;
    @SerializedName("name")
    private String name;

    public Video(String key, String site, String name) {
        this.key = key;
        this.site = site;
        this.name = name;
    }

    public Video(Parcel parcel) {
        key = parcel.readString();
        site = parcel.readString();
        name = parcel.readString();
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getTitle() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(name);
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        @Override
        public Video[] newArray(int i) {
            return new Video[i];
        }
    };
}

