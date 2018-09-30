package com.foreseer.definethis.HistoryScreen.Model;

import com.foreseer.definethis.HistoryScreen.SortType;
import com.foreseer.definethis.HistoryScreen.View.RecyclerView.ExpandableWord;

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
        void onDefinitionsReceived(List<ExpandableWord> words);
        SortType returnDefaultSortType();
    }
}
