package com.foreseer.definethis.UI.HistoryScreen.Model;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.HistoryScreen.SortType;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public interface HistoryInteractor {

    void requestDefinitions(SortType sortType);
    void requestDefinitions();
    void querySearch(String searchString);

    void resetHistory();

    public interface HistoryInteractorListener {
        void onDefinitionsReceived(List<Word> words);
        SortType returnDefaultSortType();
    }
}
