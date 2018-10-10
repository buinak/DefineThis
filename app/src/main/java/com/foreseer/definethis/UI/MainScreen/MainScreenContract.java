package com.foreseer.definethis.UI.MainScreen;

import com.foreseer.definethis.Data.Entities.DefineThis.Word;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionRecyclerViewContract;

public interface MainScreenContract {

    interface MainView {

        void setAdapter(DefinitionRecyclerViewContract.DefinitionPresenter presenter);

        void showError(String error);
        void resetError();

        void startProgressBar();
        void stopProgressBar();
        void makeProgressBarGreen();
        void makeProgressBarGrey();
        void hideProgressBar();
        void showProgressBar();

        void showPhonetics();
        void hidePhonetics();

        void setPhoneticsTextView(String text);

        void hideWordLayout();
        void showWordLayout();
    }

    interface MainPresenter {
        void onWordEntered(String word);
        void onEditTextChanged(String text);

        void onDestroy();
    }

    interface MainModel {
        void onWordDefinitionRequested(String word);
        void onTextChanged(String text);

        void finish();

        interface MainModelListener {
            void onWordDefinitionsReceived(Word word);

            void onEmptyRequestReceived();

            void onRequestStarted();
            void onWordNotFound(String word);
            void onCorrectWord();
            void onIncorrectWord();
            void onError(Exception error, boolean custom);
        }
    }
}
