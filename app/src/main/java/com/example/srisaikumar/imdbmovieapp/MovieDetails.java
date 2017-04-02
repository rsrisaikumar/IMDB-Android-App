package com.example.srisaikumar.imdbmovieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by srisaikumar on 6/10/2016.
 */

//are you right creating an array list of ids. Since you already have all the details, gather everything now in searchlistobject.

public class MovieDetails extends AppCompatActivity {
    String imdb_id="";
    int position=0;
    Bitmap bitmap=null;
    ImageView imageView=null;
    int height,width;
    SearchListObject searchListObject=SearchListObject.getInstance();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.imdb_logo);
        }catch (Exception e){}


        Intent intent=getIntent();
        imdb_id=intent.getStringExtra("IMDB_ID");
        ScrollView scrollView=new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT));
        scrollView.setId(R.id.sm_sc);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;

        final LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setId(R.id.md_lin);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
        scrollView.addView(linearLayout);
        setContentView(scrollView);


        final LinearLayout linearLayout1=new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        layoutParams.setMargins(0,height/3,0,height/4);
        linearLayout1.setLayoutParams(layoutParams);




        final ProgressBar progressBar=new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        linearLayout1.addView(progressBar);
        TextView tv=new TextView(this,null,android.R.attr.textAppearanceSmall);
        tv.setText(R.string.loading_movie);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
        linearLayout1.addView(tv);

        linearLayout.addView(linearLayout1);


        startRetrieve();

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                linearLayout1.setVisibility(View.GONE);
                createView(position,width,height);
            }
        };

        Handler handler=new Handler();
        handler.postDelayed(runnable,3000);


    }


    public void createView(final int pos, final int width, final int height){
        position=pos;
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.md_lin);
        if(linearLayout!=null)
            linearLayout.removeAllViews();

        ///title wing///-------------------------------------------------//
        TextView title=new TextView(this,null,android.R.attr.textAppearanceMedium);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTypeface(null,Typeface.BOLD);
        title.setPadding(0,0,0,height/25);
        String title_string=searchListObject.searchList.get(pos).getTitle()+"("+searchListObject.searchList.get(pos).getYear()+")";
        title.setText(title_string);
        linearLayout.addView(title);
        //--//--------------------------------------------------------//

        ///2 wing///-------------------------------------------------//
        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout1_1=new LinearLayout(this);
        linearLayout1_1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout linearLayout1_1_1=new LinearLayout(this);
        TextView release_label=new TextView(this);
        release_label.setText(R.string.released_label);
        TextView release=new TextView(this);
        release.setText(searchListObject.searchList.get(pos).getReleased());
        release.setTypeface(null,Typeface.BOLD);
        linearLayout1_1_1.addView(release_label);
        linearLayout1_1_1.addView(release);
        linearLayout1_1.addView(linearLayout1_1_1);
        LinearLayout linearLayout1_1_2=new LinearLayout(this);
        TextView genre_label=new TextView(this);
        genre_label.setText(R.string.genre_label);
        TextView genre=new TextView(this);
        genre.setText(breakString(searchListObject.searchList.get(pos).getGenre(),width));
        genre.setTypeface(null,Typeface.BOLD);
        linearLayout1_1_2.addView(genre_label);
        linearLayout1_1_2.addView(genre);
        linearLayout1_1.addView(linearLayout1_1_2);
        LinearLayout linearLayout1_1_3=new LinearLayout(this);
        TextView director_label=new TextView(this);
        director_label.setText(R.string.director_label);
        TextView director=new TextView(this);
        director.setText(breakString(searchListObject.searchList.get(pos).getDirector(),width));
        director.setTypeface(null,Typeface.BOLD);
        linearLayout1_1_3.addView(director_label);
        linearLayout1_1_3.addView(director);
        linearLayout1_1.addView(linearLayout1_1_3);
        LinearLayout linearLayout1_1_4=new LinearLayout(this);
        TextView actors_label=new TextView(this);
        actors_label.setText(R.string.actors_label);
        TextView actors=new TextView(this);
        actors.setText(breakString(searchListObject.searchList.get(pos).getActors(),width));
        actors.setTypeface(null,Typeface.BOLD);
        linearLayout1_1_4.addView(actors_label);
        linearLayout1_1_4.addView(actors);
        linearLayout1_1.addView(linearLayout1_1_4);
        linearLayout1.addView(linearLayout1_1);


        LinearLayout linearLayout1_2=new LinearLayout(this);
        linearLayout1_2.setGravity(Gravity.END);
        linearLayout1_2.setId(R.id.md_lin_im);
        ProgressBar progressBar=new ProgressBar(this);
        progressBar.setId(R.id.progress);
        linearLayout1_2.addView(progressBar);
        new LoadImage().execute(searchListObject.searchList.get(pos).getPoster());
        linearLayout1.addView(linearLayout1_2);


        linearLayout.addView(linearLayout1);
        //--//--------------------------------------------------------//


        ///rating bar wing///-------------------------------------------------//
        LinearLayout linearLayout2=new LinearLayout(this);
        linearLayout2.setGravity(Gravity.CENTER_HORIZONTAL);
        RatingBar ratingBar=new RatingBar(this);
        LinearLayout.LayoutParams ratingbarlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ratingBar.setEnabled(false);
        ratingBar.setNumStars(5);
        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorFocus),PorterDuff.Mode.SRC_ATOP);
        Float f=Float.parseFloat(searchListObject.searchList.get(pos).getImdbRating())/2;
        ratingBar.setStepSize(0.1f);
        ratingBar.setRating(f);
        linearLayout2.addView(ratingBar);
        linearLayout.addView(linearLayout2);
        //--//--------------------------------------------------------//


        ///plot wing///-------------------------------------------------//
        TextView plot_label=new TextView(this);
        plot_label.setText(R.string.plot_label);
        TextView plot=new TextView(this);
        plot.setText(searchListObject.searchList.get(pos).getPlot());
        linearLayout.addView(plot_label);
        linearLayout.addView(plot);
        //--//--------------------------------------------------------//





        ///3 wing///-------------------------------------------------//
        LinearLayout linearLayout3=new LinearLayout(this);
        ImageButton previous=new ImageButton(this);
        previous.setImageResource(R.drawable.previous);
        LinearLayout.LayoutParams previouslp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        previouslp.setMargins(0,0,width/4,0);
        previous.setLayoutParams(previouslp);
        previous.setBackgroundColor(Color.TRANSPARENT);
        linearLayout3.addView(previous);
        View.OnClickListener previousl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos-1<0)
                    createView(searchListObject.searchList.size()-1,width,height);
                else
                    createView(pos-1,width,height);
            }
        };
        previous.setOnClickListener(previousl);

        Button finish=new Button(this);
        finish.setText(R.string.finish);
        linearLayout3.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        View.OnClickListener finishl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
                System.exit(0);
            }
        };
        finish.setOnClickListener(finishl);
        linearLayout3.addView(finish);

        ImageButton next=new ImageButton(this);
        next.setImageResource(R.drawable.next);
        LinearLayout.LayoutParams nextlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nextlp.setMargins(width/4,0,0,0);
        next.setLayoutParams(nextlp);
        next.setBackgroundColor(Color.TRANSPARENT);
        linearLayout3.addView(next);
        View.OnClickListener nextl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createView((pos+1)%searchListObject.searchList.size(),width,height);
            }
        };
        next.setOnClickListener(nextl);


        linearLayout.addView(linearLayout3);
        //--//--------------------------------------------------------//



    }


    public String breakString(String data, int width){
        StringBuilder sb=new StringBuilder();;
        int i=0;
        while(i<data.length()) {

            for (int j = 0; j < width/50&&i<data.length(); j++) {
                sb.append(data.charAt(i));
                i++;
            }
            if(i<data.length())
                sb.append("\n");
        }
        return sb.toString();
    }
    public void startRetrieve(){
        int k=findIndex(imdb_id);
        position=findIndex(imdb_id);
        new LoadImage().execute(searchListObject.searchList.get(position).getPoster());
        int i=0;
        while(i<searchListObject.searchList.size()){
            String url = "http://www.omdbapi.com/?i=" + imdb_id;
            new GetDataTask().execute(url);
            k=(k+1)%searchListObject.searchList.size();
            imdb_id=searchListObject.searchList.get(k).getImdbID();
            i++;
        }

    }


    public int findIndex(String imdb_id){
        for(int i=0;i<searchListObject.searchList.size();i++){
            if(searchListObject.searchList.get(i).getImdbID().equals(imdb_id)){
                return i;
            }
        }
        return 0;
    }

    private class GetDataTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb=new StringBuilder();
            try {
                URL url = new URL(params[0]);
                URLConnection con = (URLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
            }catch (Exception e){}
            return sb.toString();
        }


        @Override
        protected void onPostExecute(String result){
            retrieve(result);
        }

    }


    void retrieve(String result){

        try {
            JSONObject jsonObject = new JSONObject(result);
            SearchList tempSearchList=new SearchList();
            tempSearchList.setImdbID(jsonObject.getString("imdbID"));
            tempSearchList.setReleased(jsonObject.getString("Released"));
            tempSearchList.setDirector(jsonObject.getString("Director"));
            tempSearchList.setActors(jsonObject.getString("Actors"));
            tempSearchList.setPlot(jsonObject.getString("Plot"));
            tempSearchList.setGenre(jsonObject.getString("Genre"));
            tempSearchList.setImdbRating(jsonObject.getString("imdbRating"));
            for(int i=0;i<searchListObject.searchList.size();i++){
                if(searchListObject.searchList.get(i).getImdbID().equals(tempSearchList.getImdbID())){
                    searchListObject.searchList.get(i).setReleased(tempSearchList.getReleased());
                    searchListObject.searchList.get(i).setDirector(tempSearchList.getDirector());
                    searchListObject.searchList.get(i).setActors(tempSearchList.getActors());
                    searchListObject.searchList.get(i).setPlot(tempSearchList.getPlot());
                    searchListObject.searchList.get(i).setGenre(tempSearchList.getGenre());
                    searchListObject.searchList.get(i).setImdbRating(tempSearchList.getImdbRating());
                    return;
                }
            }
        }catch (Exception e){}


    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            bitmap=null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            imageButtonTask(image,position);
    }
    }

    void imageButtonTask(Bitmap image, final int position){
        ImageButton imageButton;
        if(image != null){
            imageButton=new ImageButton(MovieDetails.this);
            imageButton.setImageBitmap(image);
            imageButton.setBackgroundColor(Color.TRANSPARENT);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
            imageButton.setPadding(width/7,0,0,0);
            View.OnClickListener imageButtonl=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MovieDetails.this, MovieWebView.class);
                    intent.putExtra("IMDB_ID",searchListObject.searchList.get(position).getImdbID());
                    startActivity(intent);
                }
            };
            imageButton.setOnClickListener(imageButtonl);

            ProgressBar progressBar=(ProgressBar)findViewById(R.id.progress);
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.md_lin_im);
            if(linearLayout!=null)
                linearLayout.removeView(progressBar);
            if (linearLayout != null) {
                linearLayout.addView(imageButton);
            }
        }
    }


}
