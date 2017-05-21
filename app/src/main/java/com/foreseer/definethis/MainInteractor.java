package com.foreseer.definethis;

import com.foreseer.definethis.API.JSONSchema.Result;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainInteractor {
    void onWordDefinitionRequested(String word);

    interface MainInteractorListener {
        void onWordDefinitionReceived(String definition);
        void onWordDefinitionsReceived(List<Result> results);
        void onWordNotFound(String word);
        void onError(String error);
    }
}
