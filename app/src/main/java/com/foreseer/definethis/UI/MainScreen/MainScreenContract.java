package com.foreseer.definethis.UI.MainScreen;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.MainScreen.RecyclerView.DefinitionRecyclerViewContract;

public interface MainScreenContract {

    interface MainView {

        void setAdapter(DefinitionRecyclerViewContract.DefinitionPresenter presenter);

        void showError(String error);
        void resetError();

        void showProgressBar();
        void hideProgressBar();
        void makeProgressBarGreen();
        void makeProgressBarGrey();

        void setWordTextView(String text);
        void setPhoneticsTextView(String text);

        void hideWordPhoneticsCard();
        void showWordPhoneticsCard();

        void hideWordTextView();

        void hideWordLayout();
        void showWordLayout();
    }

    interface MainPresenter {
        void onWordEntered(String word);
        void onEditTextChanged(String text);

        void onDestroy();
    }

    interface MainInteractor {
        void onWordDefinitionRequested(String word);
        void onTextChanged(String text);

        void finish();

        interface MainInteractorListener {
            void onWordDefinitionsReceived(Word word);

            void onEmptyRequestReceived();

            void onRequestStarted();
            void onWordNotFound(String word);
            void onIncorrectWord();
            void onError(Exception error, boolean custom);
        }
    }
}
