package com.epickbikes.ebike_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;


public class Terms_Conditions extends AppCompatActivity {

    Button nextbtn;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms__conditions);
         myWebView = (WebView) findViewById(R.id.webview);
         nextbtn=(Button)findViewById(R.id.nextbtn);

        myWebView.loadUrl("https://epickbikes.com/terms-and-conditions.html");

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terms_Conditions.this, Splash_Screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            myWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int tek = (int) Math.floor(myWebView.getContentHeight() * myWebView.getScale());
                    if (tek - myWebView.getScrollY() == myWebView.getHeight()){
                        nextbtn.setVisibility(View.VISIBLE);
                    }
                     //   Toast.makeText(getActivity(), "End", Toast.LENGTH_SHORT).show();
                }
                });*/

            myWebView.getViewTreeObserver()
                    .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            if (!myWebView.canScrollVertically(1)) {
                                // bottom of scroll view
                                nextbtn.setVisibility(View.VISIBLE);

                            }
                            if (!myWebView.canScrollVertically(-1)) {
                                // top of scroll view
                            }
                        }
                    });

    }
}
