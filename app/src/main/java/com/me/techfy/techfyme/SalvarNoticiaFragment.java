package com.me.techfy.techfyme;



import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.me.techfy.techfyme.DAO.NewsDAO;
import com.me.techfy.techfyme.adapter.RecyclerViewNewsAdapter;
import com.me.techfy.techfyme.adapter.RecyclerViewNoticiasSalvasAdapter;
import com.me.techfy.techfyme.database.AppDatabase;
import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.modelo.NoticiaDb;
import com.me.techfy.techfyme.service.ServiceListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalvarNoticiaFragment extends Fragment implements RecyclerViewNewsAdapter.CardPostClicado, ServiceListener {

    public static final String NOTICIA_TITULO = "noticia_titulo";
    public static final String NOTICIA_FONTE = "noticia_fonte";
    public static final String NOTICIA_DESCRICAO = "noticia_descricao";
    public static final String NOTICIA_DATA = "Noticia_data";
    public static final String NOTICIA_TEXTO = "noticia_texto";
    public static final String NOTICIA_URL = "noticia_url";
    private AppDatabase db;
    private RecyclerView recyclerView;
    private RecyclerViewNoticiasSalvasAdapter adapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private List<Noticia> noticiaList = new ArrayList<>();
    private DatabaseReference mref;
    private ProgressBar progressBar;

    public SalvarNoticiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salvar_noticia, container, false);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "database-techfyme").build();

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressbar_id);
        progressBar.setVisibility(View.VISIBLE);

        setupRecyclerView(view);

        return view;


    }


    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.save_recyclerview_news_id);

        adapter = new RecyclerViewNoticiasSalvasAdapter(noticiaList, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mostrarFavoritos();

    }

    @Override
    public void onCardClicado(Noticia noticia) {
            String dataNoticia = noticia.getDataCriacao();
            progressBar.setVisibility(View.VISIBLE);

            Bundle bundle = new Bundle();
            bundle.putString(NOTICIA_TITULO, noticia.getTitulo());
            bundle.putString(NOTICIA_FONTE, noticia.getFonte());
            bundle.putString(NOTICIA_DATA, dataNoticia);
            bundle.putString(NOTICIA_TEXTO, noticia.getTextoCompleto());
            bundle.putString(NOTICIA_URL,noticia.getLinkDaMateria());


            Intent intent = new Intent(getContext(), NoticiaDetalheActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            progressBar.setVisibility(View.INVISIBLE);

        }


    @Override
    public void onExcluirClicado(Noticia noticia) {

    }

    @Override
    public void onShareClicado(Noticia noticia) {

    }

    @Override
    public void onArmazenar(Noticia noticia) {

    }

    private void mostrarFavoritos() {


        database = FirebaseDatabase.getInstance();

        mref = database.getReference("users/" + FirebaseAuth.getInstance().getUid());

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Noticia>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Noticia>>() {
                };
                if (dataSnapshot.getValue(genericTypeIndicator) != null) {
                    Collection<Noticia> noticiaCollection = dataSnapshot.getValue(genericTypeIndicator).values();

                    List<Noticia> noticiaFavoritaList = new ArrayList<>(noticiaCollection);

                    adapter.setMateriasFavoritasList(noticiaFavoritaList);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    @Override
    public void onSuccess(Object object) {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onError(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
    }
}
