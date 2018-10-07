package com.foreseer.definethis.UI.HistoryScreen;

import com.foreseer.definethis.Data.Entities.DefineThis.Word;
import com.foreseer.definethis.UI.HistoryScreen.RecyclerView.SwipeToDeleteCallback;

import java.util.List;

public interface HistoryScreenContract {

    interface HistoryView {
        void displayWords(List<Word> wordList);
        void displayPromptDialog();
        void displayError(String errorMessage);

        void saveLastSortedType(SortType sortType);
        SortType getLastSortedType();

        void initializeRecyclerView(int direction, SwipeToDeleteCallback.SwipeToDeleteCallbackListener listener);
    }

    interface HistoryPresenter {
        void onResetClicked();
        void onResetConfirmed();

        void onSortClicked(SortType sortType);
        void onUndoClicked();

        void onSearchQueried(String query);

        void onDestroy();
    }

    interface HistoryModel {

        void requestDefinitions(SortType sortType);
        void requestDefinitions();
        void querySearch(String searchString);

        void requestUndo();
        void resetHistory();

        void finish();

        boolean hasWords();

        interface HistoryModelListener {
            void onDefinitionsReceived(List<Word> words);
            void onWordAlreadyExists(String wordString);
            void onWordAlreadyExists();

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
