package com.example.srisaikumar.imdbmovieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by srisaikumar on 6/6/2016.
 */
public class SearchMovie extends AppCompatActivity {

    SearchListObject searchListObject=SearchListObject.getInstance();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.imdb_logo);
        }catch (Exception e){}


        Intent intent=getIntent();
        String search_tag=null;

        search_tag = intent.getStringExtra("SEARCH_TAG");

        ScrollView scrollView=new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT));
        scrollView.setId(R.id.sm_sc);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;

        final LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setId(R.id.sm_lin);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
        scrollView.addView(linearLayout);
        setContentView(scrollView);


        LinearLayout linearLayout1=new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        layoutParams.setMargins(0,height/3,0,height/4);
        linearLayout1.setLayoutParams(layoutParams);




        final ProgressBar progressBar=new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        linearLayout1.addView(progressBar);
        TextView tv=new TextView(this,null,android.R.attr.textAppearanceSmall);
        tv.setText(R.string.loading_movie_list);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        linearLayout1.addView(tv);

        linearLayout.addView(linearLayout1);

        String url="http://www.omdbapi.com/?type=movie&s="+search_tag;

        if(isConnectionOnline())
            new UrlExtract().execute(url);
        else{
            progressBar.setVisibility(View.GONE);
            tv.setText(R.string.check_connection);
        }

        if(isConnectionOnline()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    printResponse();
                }
            };

            Handler handler = new Handler();
            handler.postDelayed(runnable, 2000);
        }





    }


    public boolean isConnectionOnline(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected()){
            return true;
        }
        return false;
    }


    public String getImdbID(String input){
        SearchListObject sListObject=SearchListObject.getInstance();
        for(int i=0;i<sListObject.searchList.size();i++){
            String title=sListObject.searchList.get(i).getTitle();
            int year=sListObject.searchList.get(i).getYear();
            if(input.equals(title+"("+year+")")){
                return searchListObject.searchList.get(i).getImdbID();
            }
        }
        return null;
    }

    public void printResponse(){
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView)findViewById(v.getId());
                Intent intent=new Intent(SearchMovie.this,MovieDetails.class);
                String imdb_id=getImdbID(tv.getText().toString());
                intent.putExtra("IMDB_ID",imdb_id);
                startActivity(intent);
            }
        };


        ScrollView scrollView=(ScrollView)findViewById(R.id.sm_sc);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.sm_lin);
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        for(int i=0;i<searchListObject.searchList.size();i++) {
            TextView tv = new TextView(SearchMovie.this,null,android.R.attr.textAppearanceSmall);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String re = searchListObject.searchList.get(i).getTitle()+"("+searchListObject.searchList.get(i).getYear()+")";
            tv.setText(re);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setPadding(0,50,0,0);
            tv.setId(i);
            tv.setClickable(true);
            tv.setBackground(getResources().getDrawable(R.drawable.back));
            linearLayout.addView(tv);
            tv.setOnClickListener(onClickListener);
        }
    }

    private class UrlExtract extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuilder sb = new StringBuilder();
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                bufferedReader.close();
                connection.disconnect();
                return sb.toString();
            } catch (Exception e) {
                Log.d("mal", "malformed url exception!!!!!!!!!!!!!");
            }
            return "asasas";
        }


        @Override
        protected void onPostExecute(String result){
            parsonJson(result);
        }
    }


    void parsonJson(String result){

        SearchList tempSearchList;
        try {
            searchListObject.searchList.clear();
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("Search");
            for(int i=0;i<jsonArray.length();i++){
                tempSearchList=new SearchList();
                JSONObject tempJSONObject=jsonArray.getJSONObject(i);
                tempSearchList.setTitle(tempJSONObject.getString("Title"));
                tempSearchList.setYear(Integer.parseInt(tempJSONObject.getString("Year")));
                tempSearchList.setImdbID(tempJSONObject.getString("imdbID"));
                tempSearchList.setType(tempJSONObject.getString("Type"));
                tempSearchList.setPoster(tempJSONObject.getString("Poster"));
                searchListObject.searchList.add(tempSearchList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sortArrayList();

        //printResponse();

    }

    void sortArrayList(){
        for(int i=0;i<searchListObject.searchList.size();i++){
            for(int j=i+1;j<searchListObject.searchList.size();j++){
                if(searchListObject.searchList.get(i).getYear()<searchListObject.searchList.get(j).getYear()){
                    SearchList temp=searchListObject.searchList.get(i);
                    searchListObject.searchList.set(i,searchListObject.searchList.get(j));
                    searchListObject.searchList.set(j,temp);
                }
            }
        }
    }

}


