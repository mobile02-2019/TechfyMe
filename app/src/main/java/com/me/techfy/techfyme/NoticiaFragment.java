package com.me.techfy.techfyme;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.techfy.techfyme.adaprter.RecyclerViewNewsAdapter;
import com.me.techfy.techfyme.modelo.Noticia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaFragment extends Fragment {


    public NoticiaFragment() {
        // Required empty public constructor
    }

    @Override // O que devo alterar aqui?
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticia, container, false);

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_news_id);


        RecyclerViewNewsAdapter adapter = new RecyclerViewNewsAdapter(createNoticiaList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private List<Noticia> createNoticiaList() {
        List<Noticia> noticiaList = new ArrayList<>();

        Noticia noticia1 = new Noticia();
        noticia1.setTitulo("Os 10 celulares Android que mais marcaram época (até agora");
        noticia1.setDescricao("O Android completou 10 anos em setembro de 2018 e nada mais justo do que relembrar alguns aparelhos que rodam o sistema operacional da Google.");
        noticia1.setDataCriacao(new Date());
        noticiaList.add(noticia1);

        Noticia noticia2 = new Noticia();
        noticia2.setTitulo("Google cobrará de fabricantes até US$40 por dispositivo para uso de apps no Android");
        noticia2.setDescricao("Depois de receber multa de US$ 5 bilhões da União Europeia por Android, Google irá cobrar fabricantes em novo modelo de licenciamento do sistema operacional.");
        noticia2.setDataCriacao(new Date());
        noticiaList.add(noticia2);

        return noticiaList;


    }
}


