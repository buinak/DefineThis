package com.foreseer.definethis.MainScreen.Model;

import com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Word;

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
        void onWordDefinitionsReceived(Word word);

        void onEmptyRequestReceived();

        void onRequestStarted();
        void onWordNotFound(String word);
        void onIncorrectWord();
        void onError(String error, boolean custom);
    }
}
