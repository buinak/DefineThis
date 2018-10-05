package com.foreseer.definethis.UI.HistoryScreen;

import com.foreseer.definethis.Data.Models.Word;

import java.util.List;

public interface HistoryScreenContract {

    interface HistoryView {
        void displayWords(List<Word> wordList);
        void displayPromptDialog();

        void saveLastSortedType(SortType sortType);
        SortType getLastSortedType();
    }

    interface HistoryPresenter {
        void onResetClicked();
        void onResetConfirmed();

        void onSortClicked(SortType sortType);
        void onUndoClicked();

        void onSearchQueried(String query);

        void onDestroy();
    }

    interface HistoryInteractor {

        void requestDefinitions(SortType sortType);
        void requestDefinitions();
        void querySearch(String searchString);

        void requestUndo();
        void resetHistory();

        void finish();

        interface HistoryInteractorListener {
            void onDefinitionsReceived(List<Word> words);
            SortType returnDefaultSortType();
        }
    }

    enum SortType {
        NEWEST,
        OLDEST,
        A_TO_Z,
        Z_TO_A
    }
}
