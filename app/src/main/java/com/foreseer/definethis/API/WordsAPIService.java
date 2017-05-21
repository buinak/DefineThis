package com.foreseer.definethis.API;


import com.foreseer.definethis.API.JSONSchema.WordJSON;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface WordsAPIService {

    String key = "zE3eij0pGxmshZPpWDkpbUHYFifOp1erDKujsnUS5tShUfwC24";
    String accept = "application/json";

    @GET("/words/{word}")
    Observable<WordJSON> getWordDefinition(@Header("X-Mashape-Key") String header,
                                           @Header("Accept") String accept,
                                           @Path("word") String word);
}
