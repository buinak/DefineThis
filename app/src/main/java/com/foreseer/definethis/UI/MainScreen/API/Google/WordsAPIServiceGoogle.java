package com.foreseer.definethis.UI.MainScreen.API.Google;


import com.foreseer.definethis.Data.Entities.DefineThis.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface WordsAPIServiceGoogle{

    @GET("/")
    @Headers("Accept: application/json")
    Observable<Word> getWordDefinition(@Query("define") String word);

}
