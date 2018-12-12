package com.me.techfy.techfyme.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.me.techfy.techfyme.R;
import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.service.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewNoticiasSalvasAdapter extends RecyclerView.Adapter<RecyclerViewNoticiasSalvasAdapter.ViewHolder> {

    private RecyclerViewNewsAdapter.CardPostClicado listener;
    private TextView titulo;
    private TextView fonte;
    private  ImageView iconeCompartilhar;
    private  TextView data;
    private  TextView descricao;
    private  ImageView iconeLixeira;
    private  ImageView imagemDaNoticia;
    private ImageView botaoSalvar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private List<Noticia> noticiaList = new ArrayList<>();
    private FirebaseAuth mAuth;

    public RecyclerViewNoticiasSalvasAdapter(List<Noticia> noticiaList, RecyclerViewNewsAdapter.CardPostClicado listener) {
        this.noticiaList = noticiaList;
        this.listener = listener;
    }

    public void setMateriasFavoritasList(List<Noticia> noticiaList) {
        this.noticiaList = noticiaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewNoticiasSalvasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_item_news_save, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewNoticiasSalvasAdapter.ViewHolder viewHolder, int i) {
        Noticia noticia = noticiaList.get(i);
        viewHolder.bind(noticia);
    }


    @Override
    public int getItemCount() {
        return noticiaList.size();
    }

    public void deletarNoticiaFavorita (Noticia noticia){
        int position = -1;

        for (int i=0; i<noticiaList.size(); i++){
            if (noticia.getTitulo().equals(noticiaList.get(i).getTitulo())){
                position = i;
            }
        }
        noticiaList.remove(position);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getUid());
        myRef.child(noticia.getDataBaseKey()).removeValue();
        notifyItemRemoved(position);

    }

    public void onArmazenar(Noticia noticia) {
        noticiaList.add(noticia);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users/" + firebaseAuth.getUid());
        mFirebaseDatabase.push().setValue(noticia);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             titulo = itemView.findViewById(R.id.text_titulo_id);
             fonte = itemView.findViewById(R.id.text_fonte_noticia_id);
             descricao = itemView.findViewById(R.id.text_descricao_id);
             data = itemView.findViewById(R.id.text_fonte_noticia_id);
             imagemDaNoticia = itemView.findViewById(R.id.imagem_celulares_id);
             iconeLixeira = itemView.findViewById(R.id.icone_lixeira);
             iconeCompartilhar = itemView.findViewById(R.id.iconeCompartilhar);

         }

         public void bind(Noticia noticia) {

             titulo.setText(noticia.getTitulo());
             descricao.setText(noticia.getDescricao());
             data.setText(noticia.getDataCriacao());
             Picasso.get().load(noticia.getImagemUrl()).into(imagemDaNoticia);
             imagemDaNoticia.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onCardClicado(noticia);

                 }
             });

             iconeLixeira.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onExcluirClicado(noticia);
                 }
             });

             iconeCompartilhar.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onShareClicado(noticia);

                 }
             });
         }
     }
}


