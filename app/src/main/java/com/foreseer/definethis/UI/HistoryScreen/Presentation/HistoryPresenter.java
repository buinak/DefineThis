package com.foreseer.definethis.UI.HistoryScreen.Presentation;

import com.foreseer.definethis.UI.HistoryScreen.SortType;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public interface HistoryPresenter {
    void onResetClicked();
    void onResetConfirmed();

    void onSortClicked(SortType sortType);

    void onSearchQueried(String query);
}
