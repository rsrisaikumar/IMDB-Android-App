package com.example.srisaikumar.imdbmovieapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/10/2016.
 */
public class SearchList implements Serializable {
    private String title;
    private int year;
    private String imdbID;
    private String type;
    private String poster;
    private String released;
    private String director;
    private String actors;
    private String plot;
    private String genre;
    private String imdbRating;


    public SearchList(){
        this.title=null;
        this.year=0;
        this.imdbID=null;
        this.type=null;
        this.poster=null;
        this.released=null;
        this.director=null;
        this.actors=null;
        this.plot=null;
        this.genre=null;
        this.imdbRating=null;
    }

    public SearchList(String title,int year,String imdbID, String type, String poster, String released, String director, String actors, String plot, String genre, String imdbRating){
        this.title=title;
        this.year=year;
        this.imdbID=imdbID;
        this.type=type;
        this.poster=poster;
        this.released=released;
        this.director=director;
        this.actors=actors;
        this.plot=plot;
        this.genre=genre;
        this.imdbRating=imdbRating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleased() {
        return released;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getGenre() {
        return genre;
    }

    public String getImdbRating() {
        return imdbRating;
    }
}
