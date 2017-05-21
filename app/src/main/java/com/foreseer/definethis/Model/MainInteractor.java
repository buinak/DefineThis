package com.foreseer.definethis.Model;

import com.foreseer.definethis.Model.API.JSONSchema.Definition;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public interface MainInteractor {
    void onWordDefinitionRequested(String word);
    void onTextChanged(String text);

    interface MainInteractorListener {
        void onWordDefinitionReceived(Definition definition);
        void onWordDefinitionsReceived(List<Definition> definitions);
        void onRequestStarted();
        void onWordNotFound(String word);
        void onError(String error, boolean custom);
    }
}
