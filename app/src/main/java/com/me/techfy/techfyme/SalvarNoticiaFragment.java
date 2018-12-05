package com.me.techfy.techfyme;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.techfy.techfyme.adapter.RecyclerViewNewsAdapter;
import com.me.techfy.techfyme.database.AppDatabase;
import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.service.ServiceListener;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalvarNoticiaFragment extends Fragment implements RecyclerViewNewsAdapter.CardPostClicado, ServiceListener {


    private AppDatabase db;

    public SalvarNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_salvar_noticia, container, false);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "database-techfyme").build();


        return view;
    }

    @Override
    public void onCardClicado(Noticia noticia) {

    }

    @Override
    public void onExcluirClicado(Noticia noticia) {

    }

    @Override
    public void onShareClicado(Noticia noticia) {

    }

    @Override
    public void onArmazenar(final Noticia noticia) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.noticiaDao().inserirNoticia(noticia);
            }
        });

    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
