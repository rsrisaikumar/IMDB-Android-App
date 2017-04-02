package com.example.srisaikumar.imdbmovieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.PublicKey;

/**
 * Created by srisaikumar on 6/12/2016.
 */
public class MovieWebView extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.imdb_logo);
        }catch (Exception e){}

        SearchListObject searchListObject = SearchListObject.getInstance();
        Intent intent = getIntent();
        TextView tv = new TextView(this);
        String imdb_id = intent.getStringExtra("IMDB_ID");

        String url = "http://m.imdb.com/title/" + imdb_id;
        final WebView webview = new WebView(this);
        webview.setId(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webview.loadUrl(url);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(webview);
        setContentView(linearLayout);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        WebView webview=(WebView)findViewById(R.id.webview);
        if((keyCode==KeyEvent.KEYCODE_BACK&&webview.canGoBack())){
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


