package com.foreseer.definethis.Model.API;


import com.foreseer.definethis.Model.API.JSONSchema.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface WordsAPIService {

    @GET("/words/{word}/definitions")
    @Headers({"X-Mashape-Key: zE3eij0pGxmshZPpWDkpbUHYFifOp1erDKujsnUS5tShUfwC24",
              "Accept: application/json"})
    Observable<Word> getWordDefinition(@Path("word") String word);

}
