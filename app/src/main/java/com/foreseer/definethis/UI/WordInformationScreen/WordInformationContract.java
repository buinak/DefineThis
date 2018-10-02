package com.foreseer.definethis.UI.WordInformationScreen;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.WordInformationScreen.RecyclerView.DefinitionAdapter;

public interface WordInformationContract {
    interface WordInformationView {
        void setWordViewText(String text);
        void setPhoneticsViewText(String text);

        void setUpRecyclerView();
        void setRecyclerViewAdapter(DefinitionAdapter adapter);
    }

    interface WordInformationPresenter {
        void onDestroy();
    }

    interface WordInformationModel {

        void requestWord(String word);
        void onDestroy();

        interface Listener {
            void onWordReceived(Word word);
        }
    }
}
