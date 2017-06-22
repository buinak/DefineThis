package com.foreseer.definethis.MainScreen.Model;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 *  e-mail (preferred): fforeseer@gmail.com
 */

public interface MainInteractor {
    void onWordDefinitionRequested(String word);
    void onTextChanged(String text);

    interface MainInteractorListener {
        void onWordDefinitionReceived(Definition definition);
        void onWordDefinitionsReceived(List<Definition> definitions);

        void onEmptyRequestReceived();

        void onRequestStarted();
        void onWordNotFound(String word);
        void onIncorrectWord();
        void onError(String error, boolean custom);
    }
}
