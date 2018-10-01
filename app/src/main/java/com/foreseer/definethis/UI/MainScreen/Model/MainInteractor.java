package com.foreseer.definethis.UI.MainScreen.Model;

import com.foreseer.definethis.Data.Models.Word;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 * For any questions, feel free to reach me using any of my contacts.
 * Contacts:
 *  e-mail (preferred): fforeseer@gmail.com
 */

public interface MainInteractor {
    void onWordDefinitionRequested(String word);
    void onTextChanged(String text);

    void onDestroy();

    interface MainInteractorListener {
        void onWordDefinitionsReceived(Word word);

        void onEmptyRequestReceived();

        void onRequestStarted();
        void onWordNotFound(String word);
        void onIncorrectWord();
        void onError(Exception error, boolean custom);
    }
}
