package com.baya.walmartmovie.data.api.model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @NonNull
    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("title")

    private String title;

    @Expose
    @SerializedName("overview")
    private String overview;

    @Expose
    @SerializedName("release_date")
    private String releaseDate;

    @Expose
    @SerializedName("popularity")
    private String popularity;

    @NonNull
    public String getId() {
        return mId;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

}
