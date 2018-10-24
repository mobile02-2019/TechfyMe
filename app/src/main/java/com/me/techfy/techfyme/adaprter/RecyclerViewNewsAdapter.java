package com.me.techfy.techfyme.adaprter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.techfy.techfyme.R;
import com.me.techfy.techfyme.modelo.Noticia;

import java.util.Date;
import java.util.List;

public class RecyclerViewNewsAdapter extends RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> {
    private List<Noticia> noticiaList;
    private CardPostClicado listener;


    public interface CardPostClicado {
        void onCardClicado(Noticia noticia);
    }

    public RecyclerViewNewsAdapter(List<Noticia> noticiaListt, CardPostClicado listener){
        this.noticiaList = noticiaListt;
        this.listener = listener;
        }


    @NonNull
    @Override
    public RecyclerViewNewsAdapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_file_news, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewNewsAdapter .ViewHolder viewHolder, int position) {
        Noticia noticia = noticiaList.get(position);
        viewHolder.bind(noticia);

    }

    @Override
    public int getItemCount() {
        return noticiaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo;
        private  TextView fonte;
        private TextView descricao;
        private  TextView data;
        private ImageView imagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.text_titulo_id);
            fonte = itemView.findViewById(R.id.text_fonte_noticia_id);
            descricao = itemView.findViewById(R.id.text_descricao_id);
            data = itemView.findViewById(R.id.text_fonte_noticia_id);
            imagem = itemView.findViewById(R.id.imagem_celulares_id);

        }
        public void bind(final Noticia noticia){
            titulo.setText(noticia.getTitulo());
            fonte.setText(noticia.getFonte());
            descricao.setText(noticia.getDescricao());
            data.setText(noticia.getDataCriacao().toString());
            imagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCardClicado(noticia);

                }
            });


        }
    }


}

