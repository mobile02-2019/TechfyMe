package com.me.techfy.techfyme;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.me.techfy.techfyme.modelo.Noticia;

import java.util.Date;

public class NoticiaDetalheActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalhe);

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
                Toast.makeText(v.getContext(),"Noticia adicionado aos favoritos", Toast.LENGTH_LONG).show();

            }
        });






    }
}
