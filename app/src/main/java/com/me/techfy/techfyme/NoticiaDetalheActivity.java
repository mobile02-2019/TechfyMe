package com.me.techfy.techfyme;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView titulo = findViewById(R.id.text_titulo_noticiadetail_id);
        TextView fonte = findViewById(R.id.text_fonte_noticiadetail_id);
        TextView dataNoticia = findViewById(R.id.text_data_noticiadetail_id);
        TextView textoCompleto = findViewById(R.id.text_textocompleto_noticiadetail_id);
        FloatingActionButton fab = findViewById(R.id.fab_detalhe_noticia_id);


        titulo.setText(bundle.getString(NoticiaFragment.NOTICIA_TITULO));
        fonte.setText(bundle.getString(NoticiaFragment.NOTICIA_FONTE));
        dataNoticia.setText(bundle.getString(NoticiaFragment.NOTICIA_DATA));
        textoCompleto.setText(bundle.getString(NoticiaFragment.NOTICIA_TEXTO));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Noticia adicionado aos favoritos", Toast.LENGTH_LONG).show();

                webView = findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

            }
        });


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