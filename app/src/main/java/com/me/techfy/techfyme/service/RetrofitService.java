package com.me.techfy.techfyme.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "&apiKey=a5627144665f41f7ab0fc979eea130f4";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.writeTimeout(30, TimeUnit.SECONDS);




//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                    .create();
//
//            Retrofit retrofitAdapter = new Retrofit.Builder()
//                    .baseUrl(API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    public static NewsAPI getNewsApi(){
        return getRetrofit().create(NewsAPI.class);


    }
}
