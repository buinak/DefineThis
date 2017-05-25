package com.foreseer.definethis.Main.Model.API;


import com.foreseer.definethis.Main.Model.API.JSONSchema.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface WordsAPIService {

    @GET("/v2/dictionaries/laad3/entries")
    @Headers("Accept: application/json")
    Observable<Word> getWordDefinition(@Query("headword") String word,
                                       @Query("limit") int limit);

}
