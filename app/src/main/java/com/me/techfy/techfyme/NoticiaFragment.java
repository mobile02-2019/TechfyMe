package com.me.techfy.techfyme;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.me.techfy.techfyme.DAO.NewsDAO;
import com.me.techfy.techfyme.adapter.RecyclerViewNewsAdapter;
import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.modelo.ResultadoAPI;
import com.me.techfy.techfyme.service.ServiceListener;

import java.util.List;

import static com.me.techfy.techfyme.MenuHomeActivity.CHAVE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaFragment extends Fragment implements RecyclerViewNewsAdapter.CardPostClicado,ServiceListener {


    public static final String NOTICIA_TITULO = "noticia_titulo";
    public static final String NOTICIA_FONTE = "noticia_fonte";
    public static final String NOTICIA_DESCRICAO = "noticia_descricao";
    public static final String NOTICIA_DATA = "Noticia_data";
    public static final String NOTICIA_TEXTO = "noticia_texto";
    public static final String NOTICIA_URL = "noticia_url";
    private RecyclerView recyclerView;
    private RecyclerViewNewsAdapter adapter;
    private String query;
    private ProgressBar progressBar;

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
        View view = inflater.inflate(R.layout.fragment_noticia, container, false);

        progressBar = view.findViewById(R.id.progressbar_id);
        progressBar.setVisibility(View.VISIBLE);


        Bundle bundle = getArguments();
            query = bundle.getString(CHAVE_KEY);

            setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_news_id);

        NewsDAO newsDAO = new NewsDAO();

        adapter = new RecyclerViewNewsAdapter(newsDAO.getNewsList(getContext(),this,query), this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }


    @Override
    public void onCardClicado(Noticia noticia) {
        String dataNoticia = noticia.getDataCriacao().toString();

        Bundle bundle = new Bundle();
        bundle.putString(NOTICIA_TITULO, noticia.getTitulo());
        bundle.putString(NOTICIA_FONTE, noticia.getFonte());
        bundle.putString(NOTICIA_DATA, dataNoticia);
        bundle.putString(NOTICIA_TEXTO, noticia.getTextoCompleto());
        bundle.putString(NOTICIA_URL,noticia.getLinkDaMateria());


        Intent intent = new Intent(getContext(), NoticiaDetalheActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @Override
    public void onExcluirClicado(final Noticia noticia) {
        new MaterialDialog.Builder(getContext())
                .title("Atenção")
                .content("Deseja realmente excluir a notícia?")
                .positiveColor(R.style.AppThemeDialog)
                .positiveText("ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        ((RecyclerViewNewsAdapter) recyclerView.getAdapter()).deletarNoticia(noticia);
                    }
                })
                .negativeColor(R.style.AppThemeDialog)
                .negativeText("Cancel")
                .show();

    }


    @Override
    public void onShareClicado(Noticia noticia) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, noticia.getTitulo());
        share.putExtra(Intent.EXTRA_TEXT, noticia.getLinkDaMateria());
        startActivity(Intent.createChooser(share, noticia.getDataCriacao()));

    }

    @Override
    public void onArmazenar(Noticia noticia) {

    }

    @Override
    public void onSuccess(Object object) {
        ResultadoAPI resultadoAPI = (ResultadoAPI) object;
        adapter.setNewsList(resultadoAPI.getNoticiaList());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable throwable) {
        Snackbar.make(recyclerView,throwable.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
        progressBar.setVisibility(View.GONE);
    }
}


