package com.me.techfy.techfyme;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.me.techfy.techfyme.adaprter.RecyclerViewNewsAdapter;
import com.me.techfy.techfyme.modelo.Noticia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaFragment extends Fragment implements RecyclerViewNewsAdapter.CardPostClicado {


    public static final String NOTICIA_TITULO = "noticia_titulo";
    public static final String NOTICIA_FONTE = "noticia_fonte";
    public static final String NOTICIA_DESCRICAO = "noticia_descricao";
    public static final String NOTICIA_DATA = "Noticia_data";
    public static final String NOTICIA_TEXTO = "noticia_texto";
    private List<Noticia> noticiaList;
    RecyclerView recyclerView;

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

        noticiaList = createNoticiaList();

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_news_id);


        RecyclerViewNewsAdapter adapter = new RecyclerViewNewsAdapter(noticiaList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private List<Noticia> createNoticiaList() {
        List<Noticia> noticiaList = new ArrayList<>();

        Noticia noticia1 = new Noticia();
        noticia1.setTitulo("Os 10 celulares Android que mais marcaram época (até agora)");
        noticia1.setDescricao("O Android completou 10 anos em setembro de 2018 e nada mais justo do que relembrar alguns aparelhos que rodam o sistema operacional da Google.");
        noticia1.setTextoCompleto("O Android completou 10 anos em setembro de 2018 e nada mais justo do que relembrar alguns aparelhos que rodam o sistema operacional da Google. A gente selecionou aparelhos que marcaram época por vários motivos diferentes, e listou dez por ordem de lançamento.\n" +
                "\n" +
                "Antes de partir para o conteúdo, não se esqueça assinar o canal do TecMundo no YouTube para mais vídeos como esse. Será que o seu Android favorito está nessa lista? Vamos descobrir!\n" +
                "\n" +
                "\n" +
                "1) HTC Dream\n" +
                "Um celular.\n" +
                "\n" +
                "Impossível não citar o primeirão. O HTC Dream foi lançado em setembro de 2008 como estreia do Android. Ele seria completamente diferente e mais parecido com um Blackberry até 2007, quando a Apple lançou o iPhone e obrigou a equipe a recomeçar do zero. O Dream tinha uma tela sensível ao toque e teclado deslizável na horizontal, só 192 MB de RAM e integrações nativas com serviços da Google, como o Gmail. Não estourou em venda, mas começou uma bela história.\n" +
                "\n" +
                "2) Motorola Milestone\n" +
                "Um celular.\n" +
                "\n" +
                "O Motorola Droid, ou Milestone, foi lançado em 2009 e chegou a ser considerado o Android definitivo. Ele tinha teclado retrátil na horizontal e várias funções pro mercado corporativo, um visual conservador meio quadradão até pro período e tela com mais resolução que o iPhone da época. A versão 2 também foi bem elogiada, mas logo o formato caiu em desuso.\n" +
                "\n" +
                "3) Sony Xperia Play\n" +
                "Um celular.\n" +
                "\n" +
                "Em dez anos de Android, deu pra atirar pra todos os lados e fazer experimentos variados. É o caso do Sony Ericsson Xperia Play, de 2011, que em vez de teclado deslizável na horizontal tinha um esquema de controles pra ser usado em jogos. Ele tinha características relativamente altas, mas o catálogo da Play store não era tão atrativo e ele era bem caro. Mesmo assim, foi sonho de consumo de muita gente, e smartphones gamers voltaram à moda atualmente.\n" +
                "\n" +
                "4) Galaxy SII\n" +
                "Um celular.\n" +
                "\n" +
                "O Galaxy SII não foi o primeiro da linha mais famosa da Samsung, mas foi o que mostrou que a briga com os iPhones seria dura. O processador dual-core, a tela de 4,2 polegadas e os 16 Gb de memória interna eram mais que suficiente pra muita gente, e a durabilidade do aparelho manteve ele por anos no mercado e como favorito do público. Lançado em 2011, foi um sucesso de vendas e deu a direção pra sul-coreana seguir o caminho no setor.");
        noticia1.setDataCriacao(new Date());
        noticiaList.add(noticia1);

        Noticia noticia2 = new Noticia();
        noticia2.setTitulo("Google cobrará de fabricantes até US$40 por dispositivo para uso de apps no Android");
        noticia2.setDescricao("Depois de receber multa de US$ 5 bilhões da União Europeia por Android, Google irá cobrar fabricantes em novo modelo de licenciamento do sistema operacional.");
        noticia2.setTextoCompleto("O Google cobrará das fabricantes de smartphones até US$ 40 por dispositivo para que os aparelhos tenham aplicativos da companhia, segundo fontes contatadas pela Reuters.\n" +
                "\n" +
                "A cobrança seria parte de um novo sistema de licenciamento, já que o antigo foi considerado anticompetitivo pela Comissão Europeia, que multou o Goole em US$5 bilhões, em julho. A Comissão considerou que o Google abusou de sua dominância de mercado para forçar fabricantes a adotarem seus produtos.\n" +
                "\n" +
                "As companhias podem se livrar da cobrança ao concordarem em incluir a pesquisa do Google e o buscador Chrome nos aparelhos diretamente de fábrica. Pelo acordo, o Google daria à fabricante do dispositivo uma parcela da receita de publicidade gerada por meio de busca e do Chrome.\n" +
                "\n" +
                "A nova tarifa deve entrar em vigor em 29 de outubro para qualquer novo smartphone ou tablet lançado na Área Econômica Europeia que use o sistema operacional Android, disse o Google nesta semana.\n" +
                "\n" +
                "O presidente do Google, Sundar Pichai, havia insinuado em julho que esse tipo de medida poderia ser tomada. Em um texto publicado em um blog oficial do Google, ele afirmou que a gratuidade do Android estava relacionada com o conjunto de aplicativos e serviços que são entregues aos consumidores, como a loja Google Play, o Gmail e o Google Maps, quando compram um novo smartphone.\n" +
                "\n" +
                "\"Se os fabricantes de smartphones e operadoras de redes móveis não puderem incluir nossos aplicativos em seu portfólio de dispositivos, isso vai prejudicar a balança do ecossistema Android”, disse.\n" +
                "\n" +
                "A cobrança pode ser de pelo menos US$ 2,50 e subir dependendo do país e do tamanho do dispositivo, contou a fonte. É padrão para fabricantes, com a maioria devendo pagar cerca de US$ 20, acrescentou.\n" +
                "\n" +
                "O novo sistema deve dar a rivais do Google, incluindo a Microsoft, mais espaço para se associarem com fabricantes de hardware para tornarem-se aplicativos padrão em busca e navegação, segundo analistas.\n" +
                "\n");
        noticia2.setDataCriacao(new Date());
        noticiaList.add(noticia2);


        return noticiaList;


    }

    @Override
    public void onCardClicado(Noticia noticia) {
        String dataNoticia = noticia.getDataCriacao().toString();

        Bundle bundle = new Bundle();
        bundle.putString(NOTICIA_TITULO, noticia.getTitulo());
        bundle.putString(NOTICIA_FONTE, noticia.getFonte());
        bundle.putString(NOTICIA_DATA, dataNoticia);
        bundle.putString(NOTICIA_TEXTO, noticia.getTextoCompleto());


        Intent intent = new Intent(getContext(), NoticiaDetalheActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @Override
    public void onExcluirClicado(final Noticia noticia) {
        new MaterialDialog.Builder(getContext())
                .title("Atenção")
                .content("Deseja realmente excluir a notícia?")
                .positiveText("ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        ((RecyclerViewNewsAdapter) recyclerView.getAdapter()).deletarNoticia(noticia);
                    }
                })
                .negativeText("Cancel")
                .show();




    }

    @Override
    public void onShareClicado(Noticia noticia) {

    }
}


