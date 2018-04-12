package com.example.tolulopeontop.news;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        TextView headingView = (TextView) findViewById(R.id.headingText);
        TextView authorView = (TextView) findViewById(R.id.authorText);
        TextView dateView = (TextView) findViewById(R.id.dateText);
        TextView contentView = (TextView) findViewById(R.id.contentText);


        Bundle extras = getIntent().getExtras();
        String headingText = null;
        String authorText = null;
        String dateText = null;
        String contentText = null;
        String imageUrl =null;

        if (extras != null) {
            headingText = extras.getString("keyHead");
            authorText = extras.getString("keyAuthor");
            dateText = extras.getString("keyDate");
            contentText = extras.getString("keyContent");
            imageUrl = extras.getString("keyImage");
        }

        headingView.setText(headingText);
        authorView.setText(authorText);
        dateView.setText(dateText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentView.setText(Html.fromHtml(contentText, Html.FROM_HTML_MODE_LEGACY));
        }
        Picasso.with(ContentActivity.this).load(imageUrl).into(imageView);


    }
}
