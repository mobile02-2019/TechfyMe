package com.me.techfy.techfyme.service;

import com.me.techfy.techfyme.modelo.Noticia;
import com.me.techfy.techfyme.modelo.ResultadoAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET("/v2/top-headlines?country=br&category=technology" + RetrofitService.API_KEY)
    Call<ResultadoAPI> getResultado();


}
