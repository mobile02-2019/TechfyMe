package com.me.techfy.techfyme;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.me.techfy.techfyme.modelo.Noticia;

import java.util.Date;

public class NoticiaDetalheActivity extends AppCompatActivity {

    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalhe);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.techfyme_logo_action_bar);

        Bundle bundle = getIntent().getExtras();

        url = bundle.getString(NoticiaFragment.NOTICIA_URL);

        ImageView imagem = findViewById(R.id.imagem_celulares_id);

                webView = findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();

        } else {
            super.onBackPressed();
        }
    }
}