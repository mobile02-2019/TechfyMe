package com.me.techfy.techfyme.DAO;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.modelo.ResultadoAPI;
import com.me.techfy.techfyme.service.NewsAPI;
import com.me.techfy.techfyme.service.RetrofitService;
import com.me.techfy.techfyme.service.ServiceListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDAO {

    public List<Noticia> getNewsList(Context context, final ServiceListener listener,String query) {

        List<Noticia> newsList = new ArrayList<>();

        if (isConected(context)){
            if(query=="home"){
                getRemoteNews(listener);
            } else {
                getRemoteNewsWithKey(listener,query);

            }
        } else {
            Toast.makeText(context, "Falha na conexão!", Toast.LENGTH_LONG).show();
        }

        return newsList;
    }

    public List<Noticia> getNewsListResultado(Context context, final ServiceListener listener) {

        List<Noticia> newsList = new ArrayList<>();

        if (isConected(context)){
            getRemoteNews(listener);
        } else {
            Toast.makeText(context, "Falha na conexão!", Toast.LENGTH_LONG).show();
        }

        return newsList;
    }

    private void getRemoteNewsWithKey(final ServiceListener listener, String query) {
        Call<ResultadoAPI> call = RetrofitService.getNewsApi().getWithKey(query);

        call.enqueue(new Callback<ResultadoAPI>() {
            @Override
            public void onResponse(Call<ResultadoAPI> call, Response<ResultadoAPI> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResultadoAPI> call, Throwable t) {
                listener.onError(t);
            }
        });
    }



    private void getRemoteNews(final ServiceListener listener) {
        Call<ResultadoAPI> call = RetrofitService.getNewsApi().getResultado();

        call.enqueue(new Callback<ResultadoAPI>() {
            @Override
            public void onResponse(Call<ResultadoAPI> call, Response<ResultadoAPI> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResultadoAPI> call, Throwable t) {
                listener.onError(t);
            }
        });
    }

    public static boolean isConected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }





}
