package com.example.admincuahangdientu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.admincuahangdientu.query.DbQuery;

public class YoutubeActivity extends AppCompatActivity {
    private WebView mWebView;
    private Toolbar toolbar;
    private Button backFail, backOK;

    private String URL = "https://www.youtube.com/embed/" + DbQuery.linkVideo_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        AnhXaView();
        ActionToobar();

        if(DbQuery.linkVideo_check != null)
        {
            setVideoURL();
        }

        actionBack();
    }

    private void actionBack() {
        backOK.setOnClickListener(view -> {
            finish();
        });

    }

    private void AnhXaView() {
        toolbar = findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.youtubeView);
        toolbar = findViewById(R.id.toolbar);
        backOK = findViewById(R.id.ytb_btnBackOK);


    }

    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Check Video");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setVideoURL() {

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset='utf-8'>\n" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n" +
                "    <title>Page Title</title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
                "   <style>\n" +
                "        .video-container {\n" +
                "        overflow: hidden;\n" +
                "        position: relative;\n" +
                "        width:100%;\n" +
                "        }\n" +
                "\n" +
                "        .video-container::after {\n" +
                "            padding-top: 56.25%;\n" +
                "            display: block;\n" +
                "            content: '';\n" +
                "        }\n" +
                "\n" +
                "        .video-container iframe {\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            left: 0;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "   </style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"video-container\">\n" +
                "    <iframe width=\"1102\" height=\"752\" src=\""+URL+"?&autoplay=1\" title=\"video san pham\" frameborder=\"0\" allow=\"autoplay; web-share\" allowfullscreen></iframe>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        //String VideoEmbededAdress = "<div><iframe width=\"100%\" height=\"225\" src=\""+URL+"?&autoplay=1\" title=\""+DbQuery.select_sanpham_chitiet.getTensp()+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe></div>";
        final String mimeType = "text/html";
        final String encoding = "UTF-8";//"base64";
        String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.getSettings().setUserAgentString(USERAGENT);//Important to auto play video
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(html);
        mWebView.loadDataWithBaseURL("", html, mimeType, encoding, "");


    }
    @Override
    public void onResume()
    {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

}