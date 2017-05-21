package com.foreseer.definethis.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class WordAPIClient {
    private static final String API_URL = "https://wordsapiv1.p.mashape.com/";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static WordsAPIService getApiClient(){
        return getRetrofitInstance().create(WordsAPIService.class);
    }
}
