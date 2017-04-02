package com.example.srisaikumar.imdbmovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.imdb_logo);
        }catch (Exception e){}


        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout1.setOrientation(LinearLayout.VERTICAL);


        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.imdb_image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setPadding(0,0,0,200);
        linearLayout1.addView(imageView);

        EditText editText=new EditText(this);
        editText.setHint(R.string.enter_text);
        editText.setId(R.id.search_tag);
        editText.setSingleLine();
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(editText);

        Button button=new Button(this);
        button.setId(R.id.search_button);
        button.setText(R.string.search_imdb);
        button.setAllCaps(false);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(button);

        setContentView(linearLayout1);


        View.OnClickListener searchl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search_tag=(EditText)findViewById(R.id.search_tag);
                Intent intent=new Intent(getApplicationContext(),SearchMovie.class);
                if (!isEmpty(search_tag.getText().toString())) {
                    intent.putExtra("SEARCH_TAG",search_tag.getText().toString());
                    startActivity(intent);
                }
            }
        };
        button.setOnClickListener(searchl);


    }


}
