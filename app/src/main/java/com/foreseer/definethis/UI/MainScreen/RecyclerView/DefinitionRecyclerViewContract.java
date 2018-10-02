package com.foreseer.definethis.UI.MainScreen.RecyclerView;

import com.foreseer.definethis.Data.Models.Definition;
import com.foreseer.definethis.UI.MainScreen.View.RecyclerView.DefinitionHolder;

public interface DefinitionRecyclerViewContract {

    interface DefinitionModel {
        int getNumberOfElements();
        Definition getElement(int position);
    }

    interface DefinitionView {
        void setDefinitionText(String text);
        void setExamples(String text);
        void setPartOfSpeech(String text);

        void hideExamples();
    }

    interface DefinitionPresenter {
        int getRowCount();
        void onBindDefinitionHolder(DefinitionHolder holder, int position);
    }
}
