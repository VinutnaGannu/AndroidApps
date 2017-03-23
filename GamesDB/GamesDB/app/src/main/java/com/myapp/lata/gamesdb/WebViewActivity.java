package com.myapp.lata.gamesdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    private String url;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        this.setTitle("Web View");

        url=this.getIntent().getStringExtra(GameDetails.INTENT_YOUTUBE);
        title=this.getIntent().getStringExtra(GameDetails.INTENT_TITLE);

        TextView titledisplay= (TextView) findViewById(R.id.titledisplay);
        titledisplay.setText(title+"© Trailer");

        webView= (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }
}
