package com.foreseer.definethis.UI.HistoryScreen.View;

import com.foreseer.definethis.Data.Models.Word;
import com.foreseer.definethis.UI.HistoryScreen.SortType;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public interface HistoryView {
    void displayWords(List<Word> wordList);
    void displayPromptDialog();

    void saveLastSortedType(SortType sortType);
    SortType getLastSortedType();
}
