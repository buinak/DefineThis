package com.foreseer.definethis.UI.MainScreen.API;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.API.Google.WordDeserializer;
import com.foreseer.definethis.UI.MainScreen.API.Google.WordsAPIServiceGoogle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 * e-mail (preferred): fforeseer@gmail.com
 */

public class WordAPIClient {
    private static final String API_URL_GOOGLE = "https://googledictionaryapi.eu-gb.mybluemix.net/";

    private static Retrofit getRetrofitInstance(String API_URL) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);


        Gson gson = new GsonBuilder().registerTypeAdapter(Word.class, new WordDeserializer()).create();
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static WordsAPIServiceGoogle getGoogleApiClient() {
        return getRetrofitInstance(API_URL_GOOGLE).create(WordsAPIServiceGoogle.class);
    }
}
