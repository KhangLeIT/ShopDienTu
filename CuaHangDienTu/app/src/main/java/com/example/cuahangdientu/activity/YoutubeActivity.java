package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.model.GioHang;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Timer;
import java.util.TimerTask;

public class YoutubeActivity extends AppCompatActivity {

    private WebView mWebView;
    private Toolbar toolbar;
    private TextView title;
    private AppCompatButton btnAddCart, btnBack;
    private SanPhamMoi ctsp = DbQuery.select_sanpham_chitiet;
    //Embeded Youtube Video Address
    String URL = "https://www.youtube.com/embed/" + DbQuery.link_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        AnhXaView();
        if(DbQuery.select_sanpham_chitiet !=null && DbQuery.link_video != null)
        {
            setVideoURL();

        }
        AcctionToobar();

        ActionYoutubeView();


    }

    private void ActionYoutubeView() {
        btnBack.setOnClickListener(view ->
        {
            finish();
        });
        btnAddCart.setOnClickListener(view ->
        {
            themVaoGioHang();
            Toast.makeText(this, "Đã Thêm sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void themVaoGioHang() {
        if(DbQuery.mangGioHang.size() >0)
        {
            boolean flag = false;
            int soluong = 1;

            for(int i = 0; i<DbQuery.mangGioHang.size();i++)
            {
                if (DbQuery.mangGioHang.get(i).getIdsp() == ctsp.getIdsp())
                {
                    int tongSL = soluong + DbQuery.mangGioHang.get(i).getSoluong();
                    if(tongSL >= 10)
                    {
                        DbQuery.mangGioHang.get(i).setSoluong(10);
                    }
                    else
                    {
                        DbQuery.mangGioHang.get(i).setSoluong(tongSL);
                    }

                    float gia = ctsp.getGiabansp() * DbQuery.mangGioHang.get(i).getSoluong();
                    DbQuery.mangGioHang.get(i).setGiabansp(gia);
                    flag = true;
                }
            }
            if(flag == false)
            {
                float gia = ctsp.getGiabansp() * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGiabansp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setGiahientai(ctsp.getGiabansp());
                gioHang.setIdsp(ctsp.getIdsp());
                gioHang.setHinhanhsp(ctsp.getHinhanhsp());
                gioHang.setTensp(ctsp.getTensp());
                DbQuery.mangGioHang.add(gioHang);


            }

        }else
        {
            int soluong = 1;
            float gia = ctsp.getGiabansp() * soluong;

            GioHang gioHang = new GioHang();
            gioHang.setGiabansp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(ctsp.getIdsp());
            gioHang.setHinhanhsp(ctsp.getHinhanhsp());
            gioHang.setGiahientai(ctsp.getGiabansp());
            gioHang.setTensp(ctsp.getTensp());
            DbQuery.mangGioHang.add(gioHang);


        }
    }


    private void AnhXaView() {
        mWebView = (WebView) findViewById(R.id.youtubeView);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.ytb_title);
        btnBack = findViewById(R.id.ytb_backB);
        btnAddCart = findViewById(R.id.ytb_addCart);

    }

    private void AcctionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(DbQuery.select_sanpham_chitiet.getTensp());
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
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
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