package com.baya.walmartmovie.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @Expose
    @SerializedName("page")
    private String page;

    @Expose
    @SerializedName("results")
    private List<Movie> movieList;

    public String getPage() {
        return page;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

}
