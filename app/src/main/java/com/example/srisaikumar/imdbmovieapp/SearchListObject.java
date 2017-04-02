package com.example.srisaikumar.imdbmovieapp;

import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/10/2016.
 */
public class SearchListObject {
    public ArrayList<SearchList> searchList;
    private static SearchListObject searchListObject;

    SearchListObject(){
        this.searchList=new ArrayList<>();
    }


    public static SearchListObject getInstance(){
        if(searchListObject==null){
            searchListObject=new SearchListObject();
        }
        return searchListObject;
    }
}
